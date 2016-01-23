/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author camm
 */
@Entity
@Table(catalog = "", schema = "PAYFACT")
@NamedQueries({
	@NamedQuery(name = "Abono.findAll", query = "SELECT a FROM Abono a"),
	@NamedQuery(name = "Abono.findByIdabono", query = "SELECT a FROM Abono a WHERE a.idabono = :idabono"),
	@NamedQuery(name = "Abono.findByMonto", query = "SELECT a FROM Abono a WHERE a.monto = :monto"),
	@NamedQuery(name = "Abono.findByFechapago", query = "SELECT a FROM Abono a WHERE a.fechapago = :fechapago")})
public class Abono implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
	private Integer idabono;
	@Basic(optional = false)
    @Column(nullable = false)
	private int monto;
	@Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
	private Date fechapago;
	@JoinColumn(name = "IDFACTURA", referencedColumnName = "IDFACTURA", nullable = false)
    @ManyToOne(optional = false)
	private Factura idfactura;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idabono")
	private List<Cobranza> cobranzaList;

	public Abono() {
	}

	public Abono(Integer idabono) {
		this.idabono = idabono;
	}

	public Abono(Integer idabono, int monto, Date fechapago) {
		this.idabono = idabono;
		this.monto = monto;
		this.fechapago = fechapago;
	}

	public Integer getIdabono() {
		return idabono;
	}

	public void setIdabono(Integer idabono) {
		this.idabono = idabono;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}

	public Date getFechapago() {
		return fechapago;
	}

	public void setFechapago(Date fechapago) {
		this.fechapago = fechapago;
	}

	public Factura getIdfactura() {
		return idfactura;
	}

	public void setIdfactura(Factura idfactura) {
		this.idfactura = idfactura;
	}

	public List<Cobranza> getCobranzaList() {
		return cobranzaList;
	}

	public void setCobranzaList(List<Cobranza> cobranzaList) {
		this.cobranzaList = cobranzaList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idabono != null ? idabono.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Abono)) {
			return false;
		}
		Abono other = (Abono) object;
		if ((this.idabono == null && other.idabono != null) || (this.idabono != null && !this.idabono.equals(other.idabono))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.payfact.modelo.persistencia.entidades.Abono[ idabono=" + idabono + " ]";
	}
	
}
