/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo;

import com.payfact.modelo.persistencia.entidades.Tipocobranza;
import com.payfact.modelo.persistencia.jpacontrollers.TipocobranzaJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Elchido
 */
public class ModeloTipoCobranza extends Modelo {
    
    private TipocobranzaJpaController controlador;

    public ModeloTipoCobranza() {
        this.controlador = new TipocobranzaJpaController(this.emf);
    }
    
    public void create (Tipocobranza tipocobranza){
        this.controlador.create(tipocobranza);
    }
    
    public Tipocobranza findById (Integer id){
        return this.controlador.findTipocobranza(id);
    }
    
    public List<Tipocobranza> findByNombreTipo (String nombreTipo){
        EntityManager em = this.controlador.getEntityManager();
        
        try {
            List<Tipocobranza> tipocobranza = em.createNamedQuery("Tipocobranza.findByNombretipo", Tipocobranza.class)
                    .setParameter("nombretipo",nombreTipo)
                    .getResultList();
            return tipocobranza;
        } catch (NoResultException e) {
            return null;
        }
    }
    
    
    
}
