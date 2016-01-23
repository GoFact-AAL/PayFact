/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
	@NamedQuery(name = "Cobranza.findAll", query = "SELECT c FROM Cobranza c"),
	@NamedQuery(name = "Cobranza.findByIdcobranza", query = "SELECT c FROM Cobranza c WHERE c.idcobranza = :idcobranza"),
	@NamedQuery(name = "Cobranza.findByFechadegestion", query = "SELECT c FROM Cobranza c WHERE c.fechadegestion = :fechadegestion"),
	@NamedQuery(name = "Cobranza.findByFechacompromisocobro", query = "SELECT c FROM Cobranza c WHERE c.fechacompromisocobro = :fechacompromisocobro"),
	@NamedQuery(name = "Cobranza.findByObservaciones", query = "SELECT c FROM Cobranza c WHERE c.observaciones = :observaciones"),
	@NamedQuery(name = "Cobranza.findByTipocobranza", query = "SELECT c FROM Cobranza c WHERE c.tipocobranza = :tipocobranza")})
public class Cobranza implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
	private Integer idcobranza;
	@Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
	private Date fechadegestion;
	@Temporal(TemporalType.DATE)
	private Date fechacompromisocobro;
	@Column(length = 100)
	private String observaciones;
	@Column(length = 15)
	private String tipocobranza;
	@JoinColumn(name = "IDABONO", referencedColumnName = "IDABONO", nullable = false)
    @ManyToOne(optional = false)
	private Abono idabono;
	@JoinColumn(name = "IDTIPOCOBRANZA", referencedColumnName = "IDTIPOCOBRANZA", nullable = false)
    @ManyToOne(optional = false)
	private Tipocobranza idtipocobranza;
	@JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO", nullable = false)
    @ManyToOne(optional = false)
	private Usuario idusuario;

	public Cobranza() {
	}

	public Cobranza(Integer idcobranza) {
		this.idcobranza = idcobranza;
	}

	public Cobranza(Integer idcobranza, Date fechadegestion) {
		this.idcobranza = idcobranza;
		this.fechadegestion = fechadegestion;
	}

	public Integer getIdcobranza() {
		return idcobranza;
	}

	public void setIdcobranza(Integer idcobranza) {
		this.idcobranza = idcobranza;
	}

	public Date getFechadegestion() {
		return fechadegestion;
	}

	public void setFechadegestion(Date fechadegestion) {
		this.fechadegestion = fechadegestion;
	}

	public Date getFechacompromisocobro() {
		return fechacompromisocobro;
	}

	public void setFechacompromisocobro(Date fechacompromisocobro) {
		this.fechacompromisocobro = fechacompromisocobro;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getTipocobranza() {
		return tipocobranza;
	}

	public void setTipocobranza(String tipocobranza) {
		this.tipocobranza = tipocobranza;
	}

	public Abono getIdabono() {
		return idabono;
	}

	public void setIdabono(Abono idabono) {
		this.idabono = idabono;
	}

	public Tipocobranza getIdtipocobranza() {
		return idtipocobranza;
	}

	public void setIdtipocobranza(Tipocobranza idtipocobranza) {
		this.idtipocobranza = idtipocobranza;
	}

	public Usuario getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Usuario idusuario) {
		this.idusuario = idusuario;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idcobranza != null ? idcobranza.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Cobranza)) {
			return false;
		}
		Cobranza other = (Cobranza) object;
		if ((this.idcobranza == null && other.idcobranza != null) || (this.idcobranza != null && !this.idcobranza.equals(other.idcobranza))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.payfact.modelo.persistencia.entidades.Cobranza[ idcobranza=" + idcobranza + " ]";
	}
	
}
