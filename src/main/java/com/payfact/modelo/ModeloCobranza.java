/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo;

import com.payfact.modelo.persistencia.entidades.Cobranza;
import com.payfact.modelo.persistencia.jpacontrollers.CobranzaJpaController;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Elchido
 */
public class ModeloCobranza extends Modelo {
    
    private CobranzaJpaController controlador;
    
    public ModeloCobranza(){
        this.controlador = new CobranzaJpaController(this.emf);
    }
    
    public void create(Cobranza cobranza){
        this.controlador.create(cobranza);
    }
    
    public Cobranza findById (Integer id){
        return this.controlador.findCobranza(id);
    }
    
}       
