/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.controlador;

import spark.ModelAndView;
import spark.TemplateViewRoute;

/**
 *
 * @author camm
 */
public class CobranzasHandler {
	public TemplateViewRoute manjeadorCobranzasForm = (req, resp) -> {
		return new ModelAndView(null, "cobranza.mustache");
	};
}
