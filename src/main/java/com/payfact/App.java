/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact;

/**
 *
 * @author camm
 */

import com.payfact.controlador.IndexHandler;
import static spark.Spark.*;

public class App {
	public static void main(String[] args) {
		get("/", new IndexHandler().index);
	}
}
