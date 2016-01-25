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
import com.payfact.utilidades.servicios.RecursoUsuario;
import com.payfact.utilidades.servicios.UsuarioHandler;
import spark.Request;
import static spark.Spark.*;
import spark.template.mustache.MustacheTemplateEngine;

public class App {
	private static final String API_CONTEXT = "/user";
	private static final String USER_SESSION_ID = "user";

	public static void main(String[] args) {
		// Controles
		RecursoUsuario recursoUsuario = new RecursoUsuario();

		// Usuario
		get("/", new IndexHandler().index, new MustacheTemplateEngine());
		get("/fail", new IndexHandler().indexFail, new MustacheTemplateEngine());
		post("/", recursoUsuario.redirigirIngreso);
		get(API_CONTEXT + "/inicio/:id", recursoUsuario.manejadorInicio, new MustacheTemplateEngine());
		// Get facturas
		get("/facturas", (req, resp) -> "Facturas");
	}

	private Usuario getAuthenticatedUser(Request request) {
		return request.session().attribute(USER_SESSION_ID);
	}
}
