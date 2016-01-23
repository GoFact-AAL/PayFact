/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.payfact.controlador;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.TemplateViewRoute;

/**
 *
 * @author camm
 */
public class IndexHandler {
	public TemplateViewRoute index = (request, response) -> {
		Map map = new HashMap();
		map.put("name", "PayFact!");
		return new ModelAndView(map, "index.mustache");
	};
}