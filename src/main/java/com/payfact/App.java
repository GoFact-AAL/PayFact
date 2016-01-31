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

import com.payfact.controlador.CobranzasHandler;
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
		post("/inicio/gestion/clientes/:idU/cobranza/:idC/factura/:idF", (req, post) -> "Post Cobranza");

		// Lista Clientes
		get("/inicio/cobranza/clientes", (req, resp) -> "Lista de clientes");
		get("/inicio/abonos/clientes", (req, resp) -> "Lista de clientes");

		// Listar Facturas
		get("/inicio/clientes/:id", (req, resp) -> "Facturas del cliente");

		// Listar Cobros
		get("/inicio/clientes/:idCli/cobros/:idFact", (req, resp) -> "Cobros de la factura");

		// Listar Abonos
		get("/inicio/clientes/:idCli/abonos/:idFact", (req, resp) -> "Abonos de la factura");
	}

	private Usuario getAuthenticatedUser(Request request) {
		return request.session().attribute(USER_SESSION_ID);
	}
}
