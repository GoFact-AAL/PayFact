/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo;

import com.payfact.modelo.persistencia.entidades.Cliente;
import com.payfact.modelo.persistencia.jpacontrollers.ClienteJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author camm
 */
public class ModeloCliente extends Modelo {

	private ClienteJpaController controlador;

	public ModeloCliente() {
		this.controlador = new ClienteJpaController(this.emf);
	}

	public List<Cliente> findAll(){
		return this.controlador.findClienteEntities();
	}

	public Cliente findById(Integer id){
		return this.controlador.findCliente(id);
	}
        
        public Cliente find (String cedula){
            
            EntityManager em = this.controlador.getEntityManager();
            try {
                Cliente cliente = em.createNamedQuery("Cliente.findByCedulaidentidad", Cliente.class)
                        .setParameter("cedulaidentidad", cedula)
                        .getSingleResult();
                return cliente;
            } catch (NoResultException e) {
                return null;
            }
        }
        
        
}
