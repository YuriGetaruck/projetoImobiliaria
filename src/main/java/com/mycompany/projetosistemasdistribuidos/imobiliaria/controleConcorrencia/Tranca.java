package com.mycompany.projetosistemasdistribuidos.imobiliaria.controleConcorrencia;

public class Tranca {

    public String tipo = "vazio";
    public String transacao = "vazio";

    public Tranca(Operacao operacao) {
        this.tipo = operacao.tipo;
        this.transacao = operacao.transacao;
    }

    public String toString() {
        return tipo + " " + transacao;
    }
}
