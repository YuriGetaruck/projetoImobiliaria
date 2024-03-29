package com.mycompany.projetosistemasdistribuidos.imobiliaria.controleConcorrencia;

import java.util.ArrayList;

public class Usuario implements Runnable {

    public Thread thrd;
    private Compartilhado x;
    private ArrayList<Operacao> operacoes;

    Usuario(String name, Compartilhado x,
            ArrayList<Operacao> operacoes) {
        thrd = new Thread(this, name);
        thrd.start();
        this.x = x;
        this.operacoes = operacoes;
    }

    @Override
    public void run() {

        x._2PL(operacoes, thrd);

    }
}
