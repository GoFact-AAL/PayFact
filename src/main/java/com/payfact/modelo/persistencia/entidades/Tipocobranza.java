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
	@NamedQuery(name = "Tipocobranza.findAll", query = "SELECT t FROM Tipocobranza t"),
	@NamedQuery(name = "Tipocobranza.findByIdtipocobranza", query = "SELECT t FROM Tipocobranza t WHERE t.idtipocobranza = :idtipocobranza"),
	@NamedQuery(name = "Tipocobranza.findByNombretipo", query = "SELECT t FROM Tipocobranza t WHERE t.nombretipo = :nombretipo"),
	@NamedQuery(name = "Tipocobranza.findByResultado", query = "SELECT t FROM Tipocobranza t WHERE t.resultado = :resultado")})
public class Tipocobranza implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
	private Integer idtipocobranza;
	@Column(length = 20)
	private String nombretipo;
	@Column(length = 100)
	private String resultado;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipocobranza")
	private List<Cobranza> cobranzaList;

	public Tipocobranza() {
	}

	public Tipocobranza(Integer idtipocobranza) {
		this.idtipocobranza = idtipocobranza;
	}

	public Integer getIdtipocobranza() {
		return idtipocobranza;
	}

	public void setIdtipocobranza(Integer idtipocobranza) {
		this.idtipocobranza = idtipocobranza;
	}

	public String getNombretipo() {
		return nombretipo;
	}

	public void setNombretipo(String nombretipo) {
		this.nombretipo = nombretipo;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
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
		hash += (idtipocobranza != null ? idtipocobranza.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Tipocobranza)) {
			return false;
		}
		Tipocobranza other = (Tipocobranza) object;
		if ((this.idtipocobranza == null && other.idtipocobranza != null) || (this.idtipocobranza != null && !this.idtipocobranza.equals(other.idtipocobranza))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.payfact.modelo.persistencia.entidades.Tipocobranza[ idtipocobranza=" + idtipocobranza + " ]";
	}
	
}
