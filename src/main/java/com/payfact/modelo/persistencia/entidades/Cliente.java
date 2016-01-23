/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo.persistencia.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author camm
 */
@Entity
@Table(catalog = "", schema = "PAYFACT")
@NamedQueries({
	@NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
	@NamedQuery(name = "Cliente.findByIdcliente", query = "SELECT c FROM Cliente c WHERE c.idcliente = :idcliente"),
	@NamedQuery(name = "Cliente.findByCedulaidentidad", query = "SELECT c FROM Cliente c WHERE c.cedulaidentidad = :cedulaidentidad"),
	@NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
	@NamedQuery(name = "Cliente.findByApellido", query = "SELECT c FROM Cliente c WHERE c.apellido = :apellido"),
	@NamedQuery(name = "Cliente.findByDireccion", query = "SELECT c FROM Cliente c WHERE c.direccion = :direccion"),
	@NamedQuery(name = "Cliente.findByTelefono1", query = "SELECT c FROM Cliente c WHERE c.telefono1 = :telefono1"),
	@NamedQuery(name = "Cliente.findByTelefono2", query = "SELECT c FROM Cliente c WHERE c.telefono2 = :telefono2")})
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
	private Integer idcliente;
	@Basic(optional = false)
    @Column(nullable = false, length = 10)
	private String cedulaidentidad;
	@Basic(optional = false)
    @Column(nullable = false, length = 30)
	private String nombre;
	@Basic(optional = false)
    @Column(nullable = false, length = 30)
	private String apellido;
	@Basic(optional = false)
    @Column(nullable = false, length = 100)
	private String direccion;
	@Basic(optional = false)
    @Column(nullable = false, length = 15)
	private String telefono1;
	@Basic(optional = false)
    @Column(nullable = false, length = 15)
	private String telefono2;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idcliente")
	private List<Factura> facturaList;

	public Cliente() {
	}

	public Cliente(Integer idcliente) {
		this.idcliente = idcliente;
	}

	public Cliente(Integer idcliente, String cedulaidentidad, String nombre, String apellido, String direccion, String telefono1, String telefono2) {
		this.idcliente = idcliente;
		this.cedulaidentidad = cedulaidentidad;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
	}

	public Integer getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}

	public String getCedulaidentidad() {
		return cedulaidentidad;
	}

	public void setCedulaidentidad(String cedulaidentidad) {
		this.cedulaidentidad = cedulaidentidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public List<Factura> getFacturaList() {
		return facturaList;
	}

	public void setFacturaList(List<Factura> facturaList) {
		this.facturaList = facturaList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idcliente != null ? idcliente.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Cliente)) {
			return false;
		}
		Cliente other = (Cliente) object;
		if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.payfact.modelo.persistencia.entidades.Cliente[ idcliente=" + idcliente + " ]";
	}
	
}
