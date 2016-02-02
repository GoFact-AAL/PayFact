/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.controlador;

import com.payfact.modelo.ModeloFactura;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.TemplateViewRoute;

/**
 *
 * @author camm
 */
public class AbonoHandler {
	private ModeloFactura facturaModel;

	public AbonoHandler() {
		this.facturaModel = new ModeloFactura();
	}

	public TemplateViewRoute mostrarAbonos = (req, resp) -> {
		Map map = new HashMap<>();
		Integer idFact = Integer.parseInt(req.params(":idF"));
		map.put("abonos", this.facturaModel.findById(idFact).getAbonoList());
		return new ModelAndView(map, "abonos.mustache");
	};
}
