/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.controlador;

import com.payfact.modelo.ModeloCobranza;
import com.payfact.modelo.ModeloTipoCobranza;
import com.payfact.modelo.persistencia.entidades.Cobranza;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;

/**
 *
 * @author camm
 */
public class CobranzasHandler {
	private ModeloTipoCobranza tipoCobranza;
	private ModeloCobranza cobranza;

	public CobranzasHandler() {
		tipoCobranza = new ModeloTipoCobranza();
		cobranza = new ModeloCobranza();
	}

	public TemplateViewRoute manjeadorCobranzasForm = (req, resp) -> {
		Map map = new HashMap<>();
		map.put("tipos", tipoCobranza.findAll());
		return new ModelAndView(map, "cobranza.mustache");
	};

	public Route manjeadorCobranza = (req, post) -> {
		Integer tipo = Integer.parseInt(req.params("cobranza"));
		Integer opcion = Integer.parseInt(req.params("cobranza"));
		String strDate = (String) req.params("fecha");
		Date date = new Date(strDate);
		String observacion = (String) req.params("descripcion");
		String responsable = (String) req.params("responsable");
		Cobranza cobranza1 = new Cobranza(tipo, date);
		cobranza1.setObservaciones(observacion);
		this.cobranza.create(cobranza1);
		post.redirect("/inicio/gestion/clientes");
		return post;
	};
}
