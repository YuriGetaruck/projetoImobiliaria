package com.mycompany.projetosistemasdistribuidos.imobiliaria.models;

public class Visita {
    private Cliente cliente;
    private Imovel imovel;

    public Visita(Cliente cliente, Imovel imovel) {
        this.cliente = cliente;
        this.imovel = imovel;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
}
