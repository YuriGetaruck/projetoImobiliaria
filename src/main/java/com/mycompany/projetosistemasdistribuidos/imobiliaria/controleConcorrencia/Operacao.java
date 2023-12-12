package com.mycompany.projetosistemasdistribuidos.imobiliaria.controleConcorrencia;

public class Operacao {

    public String tipo = "vazio";
    public String transacao = "vazio";

    public Operacao(String tipo, String transacao) {
        this.tipo = tipo;
        this.transacao = transacao;
    }

    @Override
    public String toString() {
        return tipo + " " + transacao;
    }
}
