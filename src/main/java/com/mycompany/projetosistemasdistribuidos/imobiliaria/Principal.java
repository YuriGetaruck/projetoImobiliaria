package com.mycompany.projetosistemasdistribuidos.imobiliaria;

import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Imovel;
import com.mycompany.projetosistemasdistribuidos.imobiliaria.services.ImobiliariaService;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Principal {

    public static ArrayList<Tranca> trancas;
    public static ArrayList<Operacao> fila;
    public static Imovel imv;

    public static boolean liberouTrancas = false;//global

    public static Usuario[] t;

    public ImobiliariaService service = new ImobiliariaService();

    public Principal() {

        trancas = new ArrayList<>();
        trancas.add(new Tranca(new Operacao("", "0")));
        fila = new ArrayList<>();
        fila.add(new Operacao("", "0"));

        imv = new Imovel(1, "Imovel 1");

        System.out.println("BEGIN");
        int i = 0;
        t = new Usuario[10];

        String transacao = String.valueOf(i); //"1";		
        Compartilhado imovel = new Compartilhado(imv);
        ArrayList<Operacao> op1 = new ArrayList<>();
        op1.add(ImobiliariaService.visita(transacao));
        op1.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel, op1);

        transacao = String.valueOf(i); //"2";
        ArrayList<Operacao> op2 = new ArrayList<>();
        op2.add(ImobiliariaService.visita(transacao));
        op2.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel, op2);

        transacao = String.valueOf(i); //"3";
        ArrayList<Operacao> op3 = new ArrayList<>();
        op3.add(ImobiliariaService.aluga(transacao));
        op3.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel, op3);

        transacao = String.valueOf(i); //"4";
        ArrayList<Operacao> op4 = new ArrayList<>();
        op4.add(ImobiliariaService.visita(transacao));
        op4.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel, op4);

        transacao = String.valueOf(i); //"5";
        ArrayList<Operacao> op5 = new ArrayList<>();
        op5.add(ImobiliariaService.visita(transacao));
        op5.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel, op5);

        transacao = String.valueOf(i); //"6";
        ArrayList<Operacao> op6 = new ArrayList<>();
        op6.add(ImobiliariaService.aluga(transacao));
        op6.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel, op6);

        transacao = String.valueOf(i); //"7";
        ArrayList<Operacao> op7 = new ArrayList<>();
        op7.add(ImobiliariaService.aluga(transacao));
        op7.add(ImobiliariaService.aluga(transacao));
        op7.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel, op7);

        transacao = String.valueOf(i); //"8";
        ArrayList<Operacao> op8 = new ArrayList<>();
        op8.add(ImobiliariaService.visita(transacao));
        op8.add(ImobiliariaService.visita(transacao));
        op8.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel, op8);

        transacao = String.valueOf(i); //"9";
        ArrayList<Operacao> op9 = new ArrayList<>();
        op9.add(ImobiliariaService.aluga(transacao));
        op9.add(ImobiliariaService.visita(transacao));
        op9.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel, op9);

        transacao = String.valueOf(i); //"10";
        ArrayList<Operacao> op10 = new ArrayList<>();
        op10.add(ImobiliariaService.visita(transacao));
        op10.add(ImobiliariaService.aluga(transacao));
        op10.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel, op10);

        WaitDie wd = new WaitDie("WD");

        try {
            for (i = 0; i < t.length; i++) {
                t[i].thrd.join();
            }
            wd.thrd.join();
        } catch (InterruptedException exc) {
            System.out.println("Thread principal interrompida.");
        }
        System.out.println("Done.");

    }

    public static void main(String args[]) {

        new Principal();

    }
}
