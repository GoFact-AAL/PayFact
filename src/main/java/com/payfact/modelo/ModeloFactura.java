/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo;

import com.payfact.modelo.persistencia.jpacontrollers.FacturaJpaController;

/**
 *
 * @author camm
 */
public class ModeloFactura extends Modelo {

	private FacturaJpaController controlador;

	public ModeloFactura() {
		this.controlador = new FacturaJpaController(this.emf);
	}
}
