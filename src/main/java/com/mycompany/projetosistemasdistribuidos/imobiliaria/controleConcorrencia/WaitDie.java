package com.mycompany.projetosistemasdistribuidos.imobiliaria.controleConcorrencia;

import java.util.ArrayList;

public class WaitDie implements Runnable {

    public Thread thrd;

    WaitDie(String name) {
        thrd = new Thread(this, name);
        thrd.start();
    }

    public void run() {

        while (true) {

            try {
                Thread.sleep(5000);
                //Cauda da fila (mais novo termina)
                System.out.print("WD queue: ");
                for (Operacao op : Principal.fila) {
                    System.out.print("[" + op.tipo + op.transacao + "] ");
                }
                System.out.println();
                if (Principal.fila.size() > 1) { //Ignore the first element from queue
                    Operacao operacao = Principal.fila.get(Principal.fila.size() - 1); //Wait-Die: most recent should be removed first
                    String victim = operacao.transacao;
                    kill(victim);
                } else {
                    System.out.println("WD: System healthy.");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }// fim run

    public void kill(String victim) {
        boolean found = false;
        for (int i = 0; i < Principal.t.length && !found; i++) {
            found = false;
            if (Principal.t[i].thrd.getName().equals(victim)) {
                found = true;
                System.err.println(Principal.t[i].thrd.getName() + ": killed");
                int size = Principal.trancas.size();
                for (int j = 0; j < size; j++) {
                    Tranca tranca = Principal.trancas.get(j);
                    if (tranca.transacao.equals(Principal.t[i].thrd.getName())) {
                        Principal.trancas.remove(tranca);
                        //When I remove a node, it is necessary to update the list size.
                        j = 0;
                        size = Principal.trancas.size();
                    }//end if
                }//end for
                Principal.t[i].thrd.interrupt();
            }
        }
    }

}
