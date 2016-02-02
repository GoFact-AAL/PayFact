/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.controlador;

import com.payfact.modelo.ModeloCliente;
import com.payfact.modelo.ModeloCobranza;
import com.payfact.modelo.ModeloFactura;
import com.payfact.modelo.ModeloUsuario;
import com.payfact.modelo.persistencia.entidades.Abono;
import com.payfact.modelo.persistencia.entidades.Factura;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import spark.TemplateViewRoute;

/**
 *
 * @author camm
 */
public class CobrosHandler {
	private ModeloUsuario modeloUsuario;

	public CobrosHandler() {
		this.modeloUsuario = new ModeloUsuario();
	}

	public TemplateViewRoute mostrarCobros = (req, resp) -> {
		Map map = new HashMap<>();
		Integer idUser = Integer.parseInt(req.params(":idU"));
		map.put("cobros", this.modeloUsuario.findById(idUser).getCobranzaList());
		return new ModelAndView(map, "cobros.mustache");
	};
}
