package com.mycompany.projetosistemasdistribuidos.imobiliaria.controleConcorrencia;

import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Imovel;
import com.mycompany.projetosistemasdistribuidos.imobiliaria.services.ImobiliariaService;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Principal {

    public static ArrayList<Tranca> trancas;
    public static ArrayList<Operacao> fila;
    public static Imovel imv1;
    public static Imovel imv2;

    public static boolean liberouTrancas = false;//global

    public static Usuario[] t;

    public ImobiliariaService service = new ImobiliariaService();

    public void simulaDeadLock() {

        trancas = new ArrayList<>();
        trancas.add(new Tranca(new Operacao("", "0")));
        fila = new ArrayList<>();
        fila.add(new Operacao("", "0"));

        imv1 = new Imovel(1, "Imovel 1");
        imv2 = new Imovel(2, "Imovel 2");

        System.out.println("BEGIN");
        int i = 0;
        t = new Usuario[10];
        
        Compartilhado imovel1 = new Compartilhado(imv1);
        Compartilhado imovel2 = new Compartilhado(imv2);
        

        String transacao = String.valueOf(i); //"1";		
        ArrayList<Operacao> op1 = new ArrayList<>();
        op1.add(ImobiliariaService.visita(transacao));
        op1.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel1, op1);

        transacao = String.valueOf(i); //"2";
        ArrayList<Operacao> op2 = new ArrayList<>();
        op2.add(ImobiliariaService.visita(transacao));
        op2.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel1, op2);

        transacao = String.valueOf(i); //"3";
        ArrayList<Operacao> op3 = new ArrayList<>();
        op3.add(ImobiliariaService.aluga(transacao));
        op3.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel1, op3);

        transacao = String.valueOf(i); //"4";
        ArrayList<Operacao> op4 = new ArrayList<>();
        op4.add(ImobiliariaService.visita(transacao));
        op4.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel1, op4);

        transacao = String.valueOf(i); //"5";
        ArrayList<Operacao> op5 = new ArrayList<>();
        op5.add(ImobiliariaService.visita(transacao));
        op5.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel1, op5);

        transacao = String.valueOf(i); //"6";
        ArrayList<Operacao> op6 = new ArrayList<>();
        op6.add(ImobiliariaService.aluga(transacao));
        op6.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel2, op6);

        transacao = String.valueOf(i); //"7";
        ArrayList<Operacao> op7 = new ArrayList<>();
        op7.add(ImobiliariaService.aluga(transacao));
        op7.add(ImobiliariaService.aluga(transacao));
        op7.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel2, op7);

        transacao = String.valueOf(i); //"8";
        ArrayList<Operacao> op8 = new ArrayList<>();
        op8.add(ImobiliariaService.visita(transacao));
        op8.add(ImobiliariaService.visita(transacao));
        op8.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel2, op8);

        transacao = String.valueOf(i); //"9";
        ArrayList<Operacao> op9 = new ArrayList<>();
        op9.add(ImobiliariaService.aluga(transacao));
        op9.add(ImobiliariaService.visita(transacao));
        op9.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel2, op9);

        transacao = String.valueOf(i); //"10";
        ArrayList<Operacao> op10 = new ArrayList<>();
        op10.add(ImobiliariaService.visita(transacao));
        op10.add(ImobiliariaService.aluga(transacao));
        op10.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel2, op10);

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
    
    public void simulaFuncionamento() {
        
        
        trancas = new ArrayList<>();
        trancas.add(new Tranca(new Operacao("", "0")));
        fila = new ArrayList<>();
        fila.add(new Operacao("", "0"));

        imv1 = new Imovel(1, "Imovel 1");

        System.out.println("BEGIN");
        int i = 0;
        t = new Usuario[10];
        
        Compartilhado imovel1 = new Compartilhado(imv1);
        

        String transacao = String.valueOf(i); //"1";		
        ArrayList<Operacao> op1 = new ArrayList<>();
        op1.add(ImobiliariaService.visita(transacao));
        op1.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel1, op1);

        transacao = String.valueOf(i); //"2";
        ArrayList<Operacao> op2 = new ArrayList<>();
        op2.add(ImobiliariaService.visita(transacao));
        op2.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel1, op2);

        transacao = String.valueOf(i); //"3";
        ArrayList<Operacao> op3 = new ArrayList<>();
        op3.add(ImobiliariaService.aluga(transacao));
        op3.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel1, op3);

        transacao = String.valueOf(i); //"4";
        ArrayList<Operacao> op4 = new ArrayList<>();
        op4.add(ImobiliariaService.visita(transacao));
        op4.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel1, op4);

        transacao = String.valueOf(i); //"5";
        ArrayList<Operacao> op5 = new ArrayList<>();
        op5.add(ImobiliariaService.visita(transacao));
        op5.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel1, op5);

        transacao = String.valueOf(i); //"6";
        ArrayList<Operacao> op6 = new ArrayList<>();
        op6.add(ImobiliariaService.aluga(transacao));
        op6.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel1, op6);

        transacao = String.valueOf(i); //"7";
        ArrayList<Operacao> op7 = new ArrayList<>();
        op7.add(ImobiliariaService.aluga(transacao));
        op7.add(ImobiliariaService.aluga(transacao));
        op7.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel1, op7);

        transacao = String.valueOf(i); //"8";
        ArrayList<Operacao> op8 = new ArrayList<>();
        op8.add(ImobiliariaService.visita(transacao));
        op8.add(ImobiliariaService.visita(transacao));
        op8.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel1, op8);

        transacao = String.valueOf(i); //"9";
        ArrayList<Operacao> op9 = new ArrayList<>();
        op9.add(ImobiliariaService.aluga(transacao));
        op9.add(ImobiliariaService.visita(transacao));
        op9.add(ImobiliariaService.aluga(transacao));
        t[i++] = new Usuario(transacao, imovel1, op9);

        transacao = String.valueOf(i); //"10";
        ArrayList<Operacao> op10 = new ArrayList<>();
        op10.add(ImobiliariaService.visita(transacao));
        op10.add(ImobiliariaService.aluga(transacao));
        op10.add(ImobiliariaService.visita(transacao));
        t[i++] = new Usuario(transacao, imovel1, op10);

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
//        System.out.println("===========SIMULA FUNCIONAMENTO============");
//        new Principal().simulaFuncionamento();
        
        System.out.println("===========SIMULA DEADLOCK============");
        new Principal().simulaDeadLock();
    }
}
