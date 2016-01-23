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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
	@NamedQuery(name = "Factura.findByIdfactura", query = "SELECT f FROM Factura f WHERE f.idfactura = :idfactura"),
	@NamedQuery(name = "Factura.findByIdentificador", query = "SELECT f FROM Factura f WHERE f.identificador = :identificador"),
	@NamedQuery(name = "Factura.findByTotal", query = "SELECT f FROM Factura f WHERE f.total = :total")})
public class Factura implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
	private Integer idfactura;
	@Column(length = 25)
	private String identificador;
	@Basic(optional = false)
    @Column(nullable = false)
	private int total;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idfactura")
	private List<Abono> abonoList;
	@JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE", nullable = false)
    @ManyToOne(optional = false)
	private Cliente idcliente;

	public Factura() {
	}

	public Factura(Integer idfactura) {
		this.idfactura = idfactura;
	}

	public Factura(Integer idfactura, int total) {
		this.idfactura = idfactura;
		this.total = total;
	}

	public Integer getIdfactura() {
		return idfactura;
	}

	public void setIdfactura(Integer idfactura) {
		this.idfactura = idfactura;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Abono> getAbonoList() {
		return abonoList;
	}

	public void setAbonoList(List<Abono> abonoList) {
		this.abonoList = abonoList;
	}

	public Cliente getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Cliente idcliente) {
		this.idcliente = idcliente;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idfactura != null ? idfactura.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Factura)) {
			return false;
		}
		Factura other = (Factura) object;
		if ((this.idfactura == null && other.idfactura != null) || (this.idfactura != null && !this.idfactura.equals(other.idfactura))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.payfact.modelo.persistencia.entidades.Factura[ idfactura=" + idfactura + " ]";
	}
	
}
