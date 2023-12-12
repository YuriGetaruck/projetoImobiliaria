package com.mycompany.projetosistemasdistribuidos.imobiliaria.controller;

import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Aluga;
import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Cliente;
import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Imovel;
import com.mycompany.projetosistemasdistribuidos.imobiliaria.models.Visita;
import com.mycompany.projetosistemasdistribuidos.imobiliaria.services.ImobiliariaService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/imobiliaria")
public class ImobiliariaController {
    
    ImobiliariaService service = new ImobiliariaService();

    @GET
    @Path("/visitas")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Visita> buscarVisitas() {
        return service.buscarVisitas();
    }

    @GET
    @Path("/alugueis")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Aluga> buscarAlugueis() {
        return service.buscarAlugueis();
    }
    
    @GET
    @Path("/cadastrarCliente/{nome}")
    @Produces(MediaType.APPLICATION_JSON)
    public Cliente cadastrarCliente(@PathParam("nome") String nome) {
        return service.cadastrarCliente(nome);
    }
    
        
    @GET
    @Path("/cadastrarImovel/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Imovel cadastrarImovel(@PathParam("codigo") String codigo) {
        return service.cadastrarImovel(codigo);
    }
}
