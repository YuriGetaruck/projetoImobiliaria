package com.mycompany.projetosistemasdistribuidos.imobiliaria.controleConcorrencia;

import java.util.ArrayList;

public class WaitDie implements Runnable {

    public Thread thrd;

    // Construtor da classe WaitDie
    WaitDie(String name) {
        thrd = new Thread(this, name); // Inicializa a thread com o nome fornecido e inicia a execução
        thrd.start();
    }

    public void run() {
        // Loop infinito para verificar e manipular a fila de operações
        while (true) {
            try {
                Thread.sleep(5000); // Aguarda 5 segundos antes de executar novamente
                System.out.print("WD queue: ");
                // Exibe os tipos de operações na fila
                for (Operacao op : Principal.fila) {
                    System.out.print("[" + op.tipo + op.transacao + "] ");
                }
                System.out.println();
                // Verifica se há mais de uma operação na fila
                if (Principal.fila.size() > 1) {
                    Operacao operacao = Principal.fila.get(Principal.fila.size() - 1);
                    String victim = operacao.transacao; // Define a transação mais recente como vítima
                    kill(victim); // Chama o método kill() para manipular a transação vítima
                } else {
                    System.out.println("WD: System healthy."); // Mensagem se a fila tem uma operação ou está vazia
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para encerrar uma transação (vítima)
    public void kill(String victim) {
        boolean found = false;
        // Percorre a lista de threads para encontrar a vítima
        for (int i = 0; i < Principal.t.length && !found; i++) {
            found = false;
            // Verifica se o nome da thread corresponde à vítima
            if (Principal.t[i].thrd.getName().equals(victim)) {
                found = true;
                System.err.println(Principal.t[i].thrd.getName() + ": killed"); // Exibe mensagem de término da thread vítima
                int size = Principal.trancas.size();
                // Remove todas as trancas associadas à transação vítima
                for (int j = 0; j < size; j++) {
                    Tranca tranca = Principal.trancas.get(j);
                    if (tranca.transacao.equals(Principal.t[i].thrd.getName())) {
                        Principal.trancas.remove(tranca);
                        j = 0;
                        size = Principal.trancas.size();
                    }
                }
                Principal.t[i].thrd.interrupt(); // Interrompe a execução da thread vítima
            }
        }
    }
}
