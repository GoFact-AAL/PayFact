/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo;

import com.payfact.modelo.persistencia.entidades.Abono;
import com.payfact.modelo.persistencia.jpacontrollers.AbonoJpaController;
import java.util.List;

/**
 *
 * @author Elchido
 */
public class ModeloAbono extends Modelo{
    
    private AbonoJpaController controlador;
    
    public ModeloAbono(){
        this.controlador = new AbonoJpaController(this.emf);
    }
    
    public void create(Abono abono){
        this.controlador.create(abono);
    }
    
    public Abono findById (Integer id){
        return this.controlador.findAbono(id);
    }
    
    public List<Abono> findAll (){
        return this.controlador.findAbonoEntities();
        
    }
    
    
}
