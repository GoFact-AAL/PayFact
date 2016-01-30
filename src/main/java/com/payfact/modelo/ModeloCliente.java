/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo;

import com.payfact.modelo.persistencia.entidades.Cliente;
import com.payfact.modelo.persistencia.jpacontrollers.ClienteJpaController;
import java.util.List;

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
}
