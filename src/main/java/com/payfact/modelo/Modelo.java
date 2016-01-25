/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author camm
 */
public class Modelo {
	protected EntityManagerFactory emf;

	public Modelo() {
		this.emf = Persistence.createEntityManagerFactory("com_PayFact_jar_1.0PU");
	}
}
