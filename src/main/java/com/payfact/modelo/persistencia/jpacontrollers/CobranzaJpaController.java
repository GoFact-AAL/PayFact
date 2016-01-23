/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo.persistencia.jpacontrollers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.payfact.modelo.persistencia.entidades.Abono;
import com.payfact.modelo.persistencia.entidades.Cobranza;
import com.payfact.modelo.persistencia.entidades.Tipocobranza;
import com.payfact.modelo.persistencia.entidades.Usuario;
import com.payfact.modelo.persistencia.jpacontrollers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camm
 */
public class CobranzaJpaController implements Serializable {

	public CobranzaJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Cobranza cobranza) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Abono idabono = cobranza.getIdabono();
			if (idabono != null) {
				idabono = em.getReference(idabono.getClass(), idabono.getIdabono());
				cobranza.setIdabono(idabono);
			}
			Tipocobranza idtipocobranza = cobranza.getIdtipocobranza();
			if (idtipocobranza != null) {
				idtipocobranza = em.getReference(idtipocobranza.getClass(), idtipocobranza.getIdtipocobranza());
				cobranza.setIdtipocobranza(idtipocobranza);
			}
			Usuario idusuario = cobranza.getIdusuario();
			if (idusuario != null) {
				idusuario = em.getReference(idusuario.getClass(), idusuario.getIdusuario());
				cobranza.setIdusuario(idusuario);
			}
			em.persist(cobranza);
			if (idabono != null) {
				idabono.getCobranzaList().add(cobranza);
				idabono = em.merge(idabono);
			}
			if (idtipocobranza != null) {
				idtipocobranza.getCobranzaList().add(cobranza);
				idtipocobranza = em.merge(idtipocobranza);
			}
			if (idusuario != null) {
				idusuario.getCobranzaList().add(cobranza);
				idusuario = em.merge(idusuario);
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Cobranza cobranza) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Cobranza persistentCobranza = em.find(Cobranza.class, cobranza.getIdcobranza());
			Abono idabonoOld = persistentCobranza.getIdabono();
			Abono idabonoNew = cobranza.getIdabono();
			Tipocobranza idtipocobranzaOld = persistentCobranza.getIdtipocobranza();
			Tipocobranza idtipocobranzaNew = cobranza.getIdtipocobranza();
			Usuario idusuarioOld = persistentCobranza.getIdusuario();
			Usuario idusuarioNew = cobranza.getIdusuario();
			if (idabonoNew != null) {
				idabonoNew = em.getReference(idabonoNew.getClass(), idabonoNew.getIdabono());
				cobranza.setIdabono(idabonoNew);
			}
			if (idtipocobranzaNew != null) {
				idtipocobranzaNew = em.getReference(idtipocobranzaNew.getClass(), idtipocobranzaNew.getIdtipocobranza());
				cobranza.setIdtipocobranza(idtipocobranzaNew);
			}
			if (idusuarioNew != null) {
				idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdusuario());
				cobranza.setIdusuario(idusuarioNew);
			}
			cobranza = em.merge(cobranza);
			if (idabonoOld != null && !idabonoOld.equals(idabonoNew)) {
				idabonoOld.getCobranzaList().remove(cobranza);
				idabonoOld = em.merge(idabonoOld);
			}
			if (idabonoNew != null && !idabonoNew.equals(idabonoOld)) {
				idabonoNew.getCobranzaList().add(cobranza);
				idabonoNew = em.merge(idabonoNew);
			}
			if (idtipocobranzaOld != null && !idtipocobranzaOld.equals(idtipocobranzaNew)) {
				idtipocobranzaOld.getCobranzaList().remove(cobranza);
				idtipocobranzaOld = em.merge(idtipocobranzaOld);
			}
			if (idtipocobranzaNew != null && !idtipocobranzaNew.equals(idtipocobranzaOld)) {
				idtipocobranzaNew.getCobranzaList().add(cobranza);
				idtipocobranzaNew = em.merge(idtipocobranzaNew);
			}
			if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
				idusuarioOld.getCobranzaList().remove(cobranza);
				idusuarioOld = em.merge(idusuarioOld);
			}
			if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
				idusuarioNew.getCobranzaList().add(cobranza);
				idusuarioNew = em.merge(idusuarioNew);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = cobranza.getIdcobranza();
				if (findCobranza(id) == null) {
					throw new NonexistentEntityException("The cobranza with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Cobranza cobranza;
			try {
				cobranza = em.getReference(Cobranza.class, id);
				cobranza.getIdcobranza();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The cobranza with id " + id + " no longer exists.", enfe);
			}
			Abono idabono = cobranza.getIdabono();
			if (idabono != null) {
				idabono.getCobranzaList().remove(cobranza);
				idabono = em.merge(idabono);
			}
			Tipocobranza idtipocobranza = cobranza.getIdtipocobranza();
			if (idtipocobranza != null) {
				idtipocobranza.getCobranzaList().remove(cobranza);
				idtipocobranza = em.merge(idtipocobranza);
			}
			Usuario idusuario = cobranza.getIdusuario();
			if (idusuario != null) {
				idusuario.getCobranzaList().remove(cobranza);
				idusuario = em.merge(idusuario);
			}
			em.remove(cobranza);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Cobranza> findCobranzaEntities() {
		return findCobranzaEntities(true, -1, -1);
	}

	public List<Cobranza> findCobranzaEntities(int maxResults, int firstResult) {
		return findCobranzaEntities(false, maxResults, firstResult);
	}

	private List<Cobranza> findCobranzaEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Cobranza.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Cobranza findCobranza(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Cobranza.class, id);
		} finally {
			em.close();
		}
	}

	public int getCobranzaCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Cobranza> rt = cq.from(Cobranza.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
