package com.mycompany.projetosistemasdistribuidos.imobiliaria.models;

public class Imovel {
    private int id;
    private String codigo;

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Imovel(int id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }
    
    @Override
    public String toString(){
        return this.codigo;
    }
}
