package com.mycompany.projetosistemasdistribuidos.imobiliaria.controleConcorrencia;

import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Imovel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Compartilhado {

    private boolean visita = true; // Variável de controle para o acesso à região crítica
    private boolean aluga = true; // Variável de controle para o estado de aluguel

    private final Imovel x; // Objeto Imovel compartilhado

    public Compartilhado(Imovel imovel) {
        this.x = imovel; // Inicializa o objeto Imovel compartilhado
    }

    // Método para liberar trancas após as operações
    public synchronized void liberarTrancas(List<Operacao> operacoes, Thread thrd) {
        Principal.liberouTrancas = false; // Durante a liberação das trancas

        // Itera sobre as operações e libera as trancas associadas a elas
        for (Operacao operacao : operacoes) {
            System.out.println(thrd.getName() + ": " + operacao.tipo + "u"
                    + operacao.transacao + "[" + x + "]");
            delTranca(operacao); // Remove a tranca correspondente à operação
        }

        System.out.println(thrd.getName() + ": liberou trancas.");
        notifyAll(); // Notifica todas as threads que aguardam
        Principal.liberouTrancas = true;
        visita = true; // Permite acesso à região crítica (conflito())
    }

    // Método para remover uma tranca associada a uma operação
    public synchronized void delTranca(Operacao operacao) {
        String tipo = operacao.tipo;
        String transacao = operacao.transacao;

        // Itera sobre as trancas para remover a correspondente à operação
        Iterator<Tranca> iterator = Principal.trancas.iterator();
        while (iterator.hasNext()) {
            Tranca t = iterator.next();
            if (t.tipo.equals(tipo) && t.transacao.equals(transacao)) {
                iterator.remove();
                break;
            }
        }

        visita = false; // Não permite acesso à região crítica (conflito())
    }

    // Método para enfileirar uma operação caso haja conflito
    public synchronized boolean enfileirar(Operacao operacao, Thread thrd) {
        boolean abort = false;
        boolean timeout = false;

        System.out.println(thrd.getName() + ": " + operacao.tipo
                + operacao.transacao + "[" + x + "]: fila");

        Principal.fila.add(operacao); // Insere na fila de operações

        // Aguarda até que as trancas sejam liberadas ou ocorra um timeout
        while (!Principal.liberouTrancas && !timeout) {
            try {
                wait(); // Aguarda notificação de liberação das trancas
            } catch (InterruptedException e) {
                System.err.println(thrd.getName() + ": timeout.");
                timeout = true;
            }
        }
        Principal.fila.remove(operacao); // Remove a operação da fila

        Principal.liberouTrancas = false; // Bloqueia novamente
        System.out.println(thrd.getName() + ": " + operacao.tipo
                + operacao.transacao + "[" + x + "]: saiu da fila");

        if (timeout) {
            abort = true; // Define abort como true em caso de timeout
        }

        return abort;
    }

    // Implementação do protocolo 2PL (Two-Phase Locking)
    public void _2PL(List<Operacao> operacoes, Thread thrd) {
        boolean abort = false;
        for (Operacao operacao : operacoes) {
            abort = false;
            // Verifica conflitos e enfileira a operação se houver conflito
            while (conflito(operacao, thrd.getName()) && !abort) {
                abort = enfileirar(operacao, thrd); // Enfileira a operação
            }

            if (!abort) {
                Principal.trancas.add(new Tranca(operacao)); // Adiciona uma nova tranca para a operação
                System.out.println(thrd.getName() + ": " + operacao.tipo + ""
                        + operacao.transacao + "[" + x + "]");
                System.out.println(thrd.getName() + ": " + operacao.tipo
                        + operacao.transacao + "[" + x + "]");
                visita = true; // Permite acesso à região crítica

                dorme(); // Simula uma operação com um tempo de espera
            } else {
                System.err.println(thrd.getName() + ": " + operacao.tipo
                        + operacao.transacao + "[" + x + "]: abort");
            }
        }
        liberarTrancas(operacoes, thrd); // Libera as trancas após todas as operações
    }

    // Método para verificar conflitos entre operações
    public synchronized boolean conflito(Operacao operacao, String thread) {
        boolean result = false;

        // Aguarda até que a região crítica esteja disponível e as trancas liberadas
        while (!visita && !Principal.liberouTrancas) {
            try {
                System.out.println(thread + ": WAIT");
                wait(); // Aguarda notificação de liberação da região crítica
            } catch (InterruptedException e) {
                System.err.println(e.toString());
            }
        }
        visita = false; // Marca a região crítica como indisponível

        System.out.print(thread + ": Trancas: [" + Principal.trancas.size() + "] ");
        // Itera sobre as trancas para verificar possíveis conflitos
        for (Tranca t : Principal.trancas) {
            String trancas = thread + "-[" + t.tipo + "l" + t.transacao + "] ";
            System.out.print(trancas);

            // Verifica se há conflitos entre as operações
            if (!t.transacao.equals(operacao.transacao)
                    && ((t.tipo.equals("w") && operacao.tipo.equals("w"))
                    || (t.tipo.equals("r") && operacao.tipo.equals("w"))
                    || (t.tipo.equals("w") && operacao.tipo.equals("r")))) {
                result = true; // Define result como true se houver conflito
            }
        }

        System.out.println();
        visita = true; // Marca a região crítica como disponível
        notifyAll(); // Notifica todas as threads que aguardam

        return result; // Retorna o resultado do conflito
    }

    // Método para simular um tempo de espera
    public void dorme() {
        try {
            Thread.sleep((int) (Math.random() * 1000)); // Dorme por um período aleatório
        } catch (InterruptedException e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public String toString() {
        return x.getCodigo();
    }
}
