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
	@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
	@NamedQuery(name = "Usuario.findByIdusuario", query = "SELECT u FROM Usuario u WHERE u.idusuario = :idusuario"),
	@NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username"),
	@NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
	private Integer idusuario;
	@Column(length = 20)
	private String username;
	@Column(length = 60)
	private String password;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idusuario")
	private List<Cobranza> cobranzaList;

	public Usuario() {
	}

	public Usuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		hash += (idusuario != null ? idusuario.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) object;
		if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.payfact.modelo.persistencia.entidades.Usuario[ idusuario=" + idusuario + " ]";
	}
	
}
