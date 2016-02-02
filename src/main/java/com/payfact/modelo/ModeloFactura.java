/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.payfact.modelo;
import com.payfact.modelo.persistencia.jpacontrollers.FacturaJpaController;
import com.payfact.modelo.persistencia.entidades.Factura;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
/**
 *
 * @author camm
 */
public class ModeloFactura extends Modelo {

	private FacturaJpaController controlador;

	public ModeloFactura() {
		this.controlador = new FacturaJpaController(this.emf);
	}

	public void create (Factura factura){
		this.controlador.create(factura);
	}

	public Factura findById(Integer id){
		return this.controlador.findFactura(id);
	}

	public Factura findByIdentificador (String identificador) {
		EntityManager em = this.controlador.getEntityManager();

		try {
			Factura factura = em.createNamedQuery("Factura.findByIdentificador", Factura.class)
					.setParameter("identificador", identificador)
					.getSingleResult();
			return factura;
		} catch (NoResultException e) {
			return null;
		}
	}
}
