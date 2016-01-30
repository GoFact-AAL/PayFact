/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.controlador;

import com.payfact.modelo.ModeloCliente;
import com.payfact.modelo.ModeloFactura;
import com.payfact.modelo.persistencia.entidades.Cliente;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.TemplateViewRoute;

/**
 *
 * @author camm
 */
public class FacturasRecurso {
	private ModeloFactura modeloFactura;
	private ModeloCliente modeloCliente;
	private Cliente cliente;

	public FacturasRecurso() {
		this.modeloFactura = new ModeloFactura();
		this.modeloCliente = new ModeloCliente();
	}

	public TemplateViewRoute manejadorFacturas =
			(req, resp) -> {
				Map map = new HashMap();
				Integer id = Integer.parseInt(req.params(":id"));
				this.cliente = this.modeloCliente.findById(id);
				map.put("facturas", this.cliente.getFacturaList());
				return new ModelAndView(map, "factura.mustache");
			};}
