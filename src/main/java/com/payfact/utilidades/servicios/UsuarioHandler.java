/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.utilidades.servicios;

import com.payfact.modelo.persistencia.entidades.Usuario;
import java.util.HashMap;
import java.util.Map;
import spark.Filter;
import spark.ModelAndView;
import spark.Request;
import static spark.Spark.halt;

/**
 *
 * @author camm
 */
public class UsuarioHandler {
	private Usuario usuario;
	private static final String USER_SESSION_ID = "user";

	public Filter verificarUsuario = (req, resp) -> {
		Usuario usuario = getAuthenticatedUser(req);
		if(usuario == null) {
			resp.redirect("/index");
		}
	};

	public ModelAndView getVistaInicio(Request req) {
		Map map = new HashMap();
		map.put("name", this.usuario.getUsername());
		return new ModelAndView(map, "inicio.mustache");
	}

	private Usuario getAuthenticatedUser(Request request) {
		return request.session().attribute(USER_SESSION_ID);
	}
}
