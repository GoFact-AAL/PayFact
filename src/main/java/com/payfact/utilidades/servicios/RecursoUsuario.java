/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.payfact.utilidades.servicios;

import com.payfact.modelo.ModeloUsuario;
import com.payfact.modelo.persistencia.entidades.Usuario;
import com.payfact.utilidades.JsonTransformer;
import java.util.HashMap;
import static spark.Spark.get;
import static spark.Spark.post;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

/**
 *
 * @author camm
 */
public class RecursoUsuario {
	private static final String API_CONTEXT = "/user";
	private ModeloUsuario modelo;
	private Usuario usuario;

	public RecursoUsuario() {
		this.modelo = new ModeloUsuario();
		setUpResources();
	}

	void setUpResources(){
		get(API_CONTEXT + "/inicio/:id", "application/json", (req, resp) -> {
			return getVistaInicio(req);
		}, new MustacheTemplateEngine());

		post(API_CONTEXT + "/inicio", "application/json", (req, resp) -> {
			String username = req.queryParams("username");
			redireccionarUsuario(username, resp);
			return "";
		}, new JsonTransformer());
	}

	private ModelAndView getVistaInicio(Request req) {
		Map map = new HashMap();
		this.usuario = this.modelo.findById(Integer.parseInt(req.params(":id")));
		map.put("name", this.usuario.getUsername());
		return new ModelAndView(map, "inicio.mustache");
	}

	private void redireccionarUsuario(String username, Response resp) {
		this.usuario = modelo.find(username);
		if (usuario == null) {
			resp.redirect("/fail");
		} else {
			resp.redirect(API_CONTEXT + "/inicio/" + this.usuario.getIdusuario());
		}
	}
}
