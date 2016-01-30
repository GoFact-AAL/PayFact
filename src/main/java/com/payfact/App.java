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
import javax.ws.rs.WebApplicationException;

public class App {
	private static final String API_CONTEXT = "/user";
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
		get(API_CONTEXT + "/inicio/:id", recursoUsuario.manejadorInicio, new MustacheTemplateEngine());

		// Get facturas
		get(API_CONTEXT + "/inicio/:idUser/cliente/:id", recursoFactura.manejadorFacturas, new MustacheTemplateEngine());
                
                get("*", new IndexHandler().index404, new MustacheTemplateEngine());
        }

	private Usuario getAuthenticatedUser(Request request) {
		return request.session().attribute(USER_SESSION_ID);
	}
}
