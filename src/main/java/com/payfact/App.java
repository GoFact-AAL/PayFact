/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact;

/**
 *
 * @author camm
 */

import com.payfact.controlador.AbonoHandler;
import com.payfact.controlador.CobranzasHandler;
import com.payfact.controlador.CobrosHandler;
import com.payfact.controlador.IndexHandler;
import com.payfact.modelo.persistencia.entidades.Usuario;
import com.payfact.controlador.FacturasRecurso;
import com.payfact.controlador.RecursoUsuario;
import spark.Request;
import static spark.Spark.*;
import spark.template.mustache.MustacheTemplateEngine;

public class App {
	private static final String USER_SESSION_ID = "user";

	public static void main(String[] args) {
		// Controles
		RecursoUsuario recursoUsuario = new RecursoUsuario();
		FacturasRecurso recursoFactura = new FacturasRecurso();
		CobranzasHandler recursoCobranza = new CobranzasHandler();
		AbonoHandler recursoAbono = new AbonoHandler();
		CobrosHandler recursoCobros = new CobrosHandler();

		staticFileLocation("/public");

		// Usuario
		get("/", new IndexHandler().index, new MustacheTemplateEngine());
		get("/fail", new IndexHandler().indexFail, new MustacheTemplateEngine());
		get("/inicio/:id", new IndexHandler().menuInicio, new MustacheTemplateEngine());
		post("/", recursoUsuario.redirigirIngreso);

		// Nueva Cobranza
		get("/inicio/gestion/clientes", recursoUsuario.manejadorInicio, new MustacheTemplateEngine());
		get("/inicio/gestion/clientes/:id", recursoFactura.manejadorFacturas, new MustacheTemplateEngine());
		get("/inicio/gestion/clientes/:idU/cobranza/:idC/factura/:idF", recursoCobranza.manjeadorCobranzasForm, new MustacheTemplateEngine());
		post("/inicio/gestion/clientes", recursoCobranza.manjeadorCobranza);

		// Lista Clientes
		get("/inicio/cobranza/clientes", recursoUsuario.manejadorInicioCobranza, new MustacheTemplateEngine());
		get("/inicio/abonos/clientes", recursoUsuario.manejadorInicioAbono, new MustacheTemplateEngine());

		// Listar Facturas
		get("/inicio/cobranza/clientes/:id", recursoFactura.manejadorFacturasCobranza, new MustacheTemplateEngine());
		get("/inicio/abonos/clientes/:id", recursoFactura.manejadorFacturasAbono, new MustacheTemplateEngine());

		// Listar Cobros
		get("/inicio/cobranza/clientes/:idU/cobranza/:idC/factura/:idF", recursoCobros.mostrarCobros, new MustacheTemplateEngine());

		// Listar Abonos
		get("/inicio/abono/clientes/:idU/cobranza/:idC/factura/:idF", recursoAbono.mostrarAbonos, new MustacheTemplateEngine());
	}

	private Usuario getAuthenticatedUser(Request request) {
		return request.session().attribute(USER_SESSION_ID);
	}
}
