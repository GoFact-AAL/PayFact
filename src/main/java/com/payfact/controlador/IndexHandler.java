/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.payfact.controlador;

import spark.Route;

/**
 *
 * @author camm
 */
public class IndexHandler {
	public Route  index = (request, response) -> "PayFact!";
}