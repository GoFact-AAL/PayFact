/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo;

import com.payfact.modelo.persistencia.entidades.Usuario;
import com.payfact.modelo.persistencia.jpacontrollers.UsuarioJpaController;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author camm
 */
public class ModeloUsuario extends Modelo {
	private UsuarioJpaController controlador;

	public ModeloUsuario() {
		this.controlador = new UsuarioJpaController(this.emf);
	}

	public void create(Usuario usuario){
		this.controlador.create(usuario);
	}

	public Usuario findById(Integer id){
		return this.controlador.findUsuario(id);
	}

	public Usuario find(String username){
		EntityManager em = this.controlador.getEntityManager();
		try{
		Usuario usuario = em.createNamedQuery("Usuario.findByUsername", Usuario.class)
				.setParameter("username", username)
				.getSingleResult();
			return usuario;
		} catch(NoResultException ex){
			return null;
		}
	}
}
