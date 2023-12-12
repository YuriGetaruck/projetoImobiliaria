package com.mycompany.projetosistemasdistribuidos.imobiliaria.services;

import com.mycompany.projetosistemasdistribuidos.imobiliaria.Operacao;
import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Aluga;
import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Cliente;
import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Imovel;
import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Visita;
import java.util.ArrayList;
import java.util.List;

public class ImobiliariaService {

    public static int id_cliente = 1;
    public static int id_imovel = 1;
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Imovel> imoveis = new ArrayList<>();
    private static ArrayList<Visita> visitas = new ArrayList<>();
    private static ArrayList<Aluga> alugeis = new ArrayList<>();

    public static Operacao visita(String transacao) {
        var opc = new Operacao("visita", transacao);
        var cliente = new Cliente(id_cliente, "Cliente" + id_cliente);
        var imovel = new Imovel(id_imovel, "Imovel" + id_imovel);
        clientes.add(cliente);
        imoveis.add(imovel);
        visitas.add(new Visita(cliente, imovel));
        return opc;
    }

    public static Operacao aluga(String transacao) {
        var opc = new Operacao("aluga", transacao);
        var cliente = new Cliente(id_cliente, "Cliente" + id_cliente);
        var imovel = new Imovel(id_imovel, "Imovel" + id_imovel);
        clientes.add(cliente);
        imoveis.add(imovel);
        alugeis.add(new Aluga(cliente, imovel));
        return opc;
    }

    public ArrayList<Visita> buscarVisitas() {
        while (visitas.size() < 10) {
            var cliente = new Cliente(id_cliente, "Cliente" + id_cliente);
            var imovel = new Imovel(id_imovel, "Imovel" + id_imovel);
            clientes.add(cliente);
            imoveis.add(imovel);
            visitas.add(new Visita(cliente, imovel));
            id_cliente++;
            id_imovel++;
        }
        return visitas;
    }

    public ArrayList<Aluga> buscarAlugueis() {
        while (alugeis.size() < 10) {
            var cliente = new Cliente(id_cliente, "Cliente" + id_cliente);
            var imovel = new Imovel(id_imovel, "Imovel" + id_imovel);
            clientes.add(cliente);
            imoveis.add(imovel);
            alugeis.add(new Aluga(cliente, imovel));
            id_cliente++;
            id_imovel++;
        }
        return alugeis;
    }

    public Cliente cadastrarCliente(String nome) {
        var cliente = new Cliente(id_cliente, nome);
        id_cliente++;
        return cliente;
    }

    public Imovel cadastrarImovel(String codigo) {
        var imovel = new Imovel(id_imovel, codigo);
        id_imovel++;
        return imovel;
    }
}
