/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.payfact.controlador;

import com.payfact.modelo.ModeloCliente;
import com.payfact.modelo.ModeloUsuario;
import com.payfact.modelo.persistencia.entidades.Usuario;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;

/**
 *
 * @author camm
 */
public class RecursoUsuario {
	private static final String API_CONTEXT = "/user";
	private ModeloUsuario modeloUsuario = null;
	private ModeloCliente modeloCliente = null;
	private Usuario usuario;

	public RecursoUsuario() {
		this.modeloUsuario = new ModeloUsuario();
		this.modeloCliente = new ModeloCliente();
	}

	public Route redirigirIngreso = (req, resp) -> {
		String username = req.queryParams("username");
                String password = req.queryParams("password");
		Map map = new HashMap();
		this.usuario = this.modeloUsuario.find(username, password);
		if (usuario == null) {
			resp.redirect("/fail");
		} else {
			resp.redirect(API_CONTEXT + "/inicio/" + this.usuario.getIdusuario());
		}
		return "";
	};

	public TemplateViewRoute manejadorInicio = (req, resp) -> {
		Map map = new HashMap();
		if (this.usuario == null) {
			resp.redirect("/");
		} else{
			map.put("id", this.usuario.getIdusuario());
			map.put("name", this.usuario.getUsername());
			map.put("clientes", this.modeloCliente.findAll());
		}
		return new ModelAndView(map, "inicio.mustache");
	};
}
