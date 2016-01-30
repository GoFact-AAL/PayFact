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

		staticFileLocation("/public");

		// Usuario
		get("/", new IndexHandler().index, new MustacheTemplateEngine());
		get("/fail", new IndexHandler().indexFail, new MustacheTemplateEngine());
		post("/", recursoUsuario.redirigirIngreso);

		// Nueva Cobranza
		get("/inicio/gestion", recursoUsuario.manejadorInicio, new MustacheTemplateEngine());
		get("/inicio/gestion/:id", (req, resp) -> null, new MustacheTemplateEngine());
		get("/inicio/gestion/:idCli/cobranza/:id", new IndexHandler().index, new MustacheTemplateEngine());
		post("/inicio/gestion/:idCli/cobranza/:id", (req, post) -> "Post Cobranza");

		// Lista Clientes
		get("/inicio/clientes", (req, resp) -> "Lista de clientes");

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
