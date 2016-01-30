/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.payfact.controlador;

import com.payfact.modelo.persistencia.entidades.Usuario;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.TemplateViewRoute;

/**
 *
 * @author camm
 */
public class IndexHandler {
	private static final String USER_SESSION_ID = "user";

	public TemplateViewRoute index = (request, response) -> {
		Map map = new HashMap();
		map.put("name", "PayFact!");
		map.put("active", "hide");
		return new ModelAndView(map, "index.mustache");
	};

	public TemplateViewRoute indexFail = (request, response) -> {
		Map map = new HashMap();
		map.put("name", "PayFact!");
		map.put("active", "show");
		return new ModelAndView(map, "index.mustache");
	};

        public TemplateViewRoute index404 = (request, response) -> {
                return new ModelAndView(null,"404.mustache");
        };
        
	private Usuario getAuthenticatedUser(Request request) {
		return request.session().attribute(USER_SESSION_ID);
	}
}