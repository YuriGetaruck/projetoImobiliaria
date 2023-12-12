package com.mycompany.projetosistemasdistribuidos.imobiliaria;

import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Imovel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Compartilhado {

    private boolean visita = true;
    private boolean aluga = true;

    private final Imovel x;

    public Compartilhado(Imovel imovel) {
        this.x = imovel;
    }

    public synchronized void liberarTrancas(List<Operacao> operacoes, Thread thrd) {
        Principal.liberouTrancas = false; // durante a liberacao das trancas
        
        for (Operacao operacao : operacoes) {
            System.out.println(thrd.getName() + ": " + operacao.tipo + "u"
                    + operacao.transacao + "[" + x + "]");
            delTranca(operacao);
        }

        System.out.println(thrd.getName() + ": liberou trancas.");
        notifyAll();
        Principal.liberouTrancas = true;
        visita = true; // Permite acesso à região crítica (conflito())
    }

    public synchronized void delTranca(Operacao operacao) {
        String tipo = operacao.tipo;
        String transacao = operacao.transacao;
        
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

    public synchronized boolean enfileirar(Operacao operacao, Thread thrd) {
        boolean abort = false;
        boolean timeout = false;

        System.out.println(thrd.getName() + ": " + operacao.tipo
                + operacao.transacao + "[" + x + "]: fila");

        Principal.fila.add(operacao); // insere na fila

        while (!Principal.liberouTrancas && !timeout) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println(thrd.getName() + ": timeout.");
                timeout = true;
            }
        }
        Principal.fila.remove(operacao);

        Principal.liberouTrancas = false; // bloqueia novamente
        System.out.println(thrd.getName() + ": " + operacao.tipo
                + operacao.transacao + "[" + x + "]: saiu da fila");
        
        if (timeout) {
            abort = true;
        }
        
        return abort;
    }

    public void _2PL(List<Operacao> operacoes, Thread thrd) {
        boolean abort = false;
        for (Operacao operacao : operacoes) {
            abort = false;
            while (conflito(operacao, thrd.getName()) && !abort) {
                abort = enfileirar(operacao, thrd);
            }

            if (!abort) {
                Principal.trancas.add(new Tranca(operacao));
                System.out.println(thrd.getName() + ": " + operacao.tipo + ""
                        + operacao.transacao + "[" + x + "]");
                System.out.println(thrd.getName() + ": " + operacao.tipo
                        + operacao.transacao + "[" + x + "]");
                visita = true;

                dorme();
            } else {
                System.err.println(thrd.getName() + ": " + operacao.tipo
                        + operacao.transacao + "[" + x + "]: abort");
            }
        }
        liberarTrancas(operacoes, thrd);
    }

    public synchronized boolean conflito(Operacao operacao, String thread) {
        boolean result = false;

        while (!visita && !Principal.liberouTrancas) {
            try {
                System.out.println(thread + ": WAIT");
                wait();

            } catch (InterruptedException e) {
                System.err.println(e.toString());
            }
        }
        visita = false;

        System.out.print(thread + ": Trancas: [" + Principal.trancas.size() + "] ");
        for (Tranca t : Principal.trancas) {
            String trancas = thread + "-[" + t.tipo + "l" + t.transacao + "] ";
            System.out.print(trancas);

            if (!t.transacao.equals(operacao.transacao) &&
                    ((t.tipo.equals("w") && operacao.tipo.equals("w")) ||
                            (t.tipo.equals("r") && operacao.tipo.equals("w")) ||
                            (t.tipo.equals("w") && operacao.tipo.equals("r")))) {
                result = true;
            }
        }

        System.out.println();
        visita = true;
        notifyAll();

        return result;
    }

    public void dorme() {
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public String toString() {
        return x.getCodigo();
    }
}
