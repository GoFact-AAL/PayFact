/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo.persistencia.jpacontrollers;

import com.payfact.modelo.persistencia.entidades.Abono;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.payfact.modelo.persistencia.entidades.Factura;
import com.payfact.modelo.persistencia.entidades.Cobranza;
import com.payfact.modelo.persistencia.jpacontrollers.exceptions.IllegalOrphanException;
import com.payfact.modelo.persistencia.jpacontrollers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camm
 */
public class AbonoJpaController implements Serializable {

	public AbonoJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Abono abono) {
		if (abono.getCobranzaList() == null) {
			abono.setCobranzaList(new ArrayList<Cobranza>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Factura idfactura = abono.getIdfactura();
			if (idfactura != null) {
				idfactura = em.getReference(idfactura.getClass(), idfactura.getIdfactura());
				abono.setIdfactura(idfactura);
			}
			List<Cobranza> attachedCobranzaList = new ArrayList<Cobranza>();
			for (Cobranza cobranzaListCobranzaToAttach : abono.getCobranzaList()) {
				cobranzaListCobranzaToAttach = em.getReference(cobranzaListCobranzaToAttach.getClass(), cobranzaListCobranzaToAttach.getIdcobranza());
				attachedCobranzaList.add(cobranzaListCobranzaToAttach);
			}
			abono.setCobranzaList(attachedCobranzaList);
			em.persist(abono);
			if (idfactura != null) {
				idfactura.getAbonoList().add(abono);
				idfactura = em.merge(idfactura);
			}
			for (Cobranza cobranzaListCobranza : abono.getCobranzaList()) {
				Abono oldIdabonoOfCobranzaListCobranza = cobranzaListCobranza.getIdabono();
				cobranzaListCobranza.setIdabono(abono);
				cobranzaListCobranza = em.merge(cobranzaListCobranza);
				if (oldIdabonoOfCobranzaListCobranza != null) {
					oldIdabonoOfCobranzaListCobranza.getCobranzaList().remove(cobranzaListCobranza);
					oldIdabonoOfCobranzaListCobranza = em.merge(oldIdabonoOfCobranzaListCobranza);
				}
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Abono abono) throws IllegalOrphanException, NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Abono persistentAbono = em.find(Abono.class, abono.getIdabono());
			Factura idfacturaOld = persistentAbono.getIdfactura();
			Factura idfacturaNew = abono.getIdfactura();
			List<Cobranza> cobranzaListOld = persistentAbono.getCobranzaList();
			List<Cobranza> cobranzaListNew = abono.getCobranzaList();
			List<String> illegalOrphanMessages = null;
			for (Cobranza cobranzaListOldCobranza : cobranzaListOld) {
				if (!cobranzaListNew.contains(cobranzaListOldCobranza)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Cobranza " + cobranzaListOldCobranza + " since its idabono field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			if (idfacturaNew != null) {
				idfacturaNew = em.getReference(idfacturaNew.getClass(), idfacturaNew.getIdfactura());
				abono.setIdfactura(idfacturaNew);
			}
			List<Cobranza> attachedCobranzaListNew = new ArrayList<Cobranza>();
			for (Cobranza cobranzaListNewCobranzaToAttach : cobranzaListNew) {
				cobranzaListNewCobranzaToAttach = em.getReference(cobranzaListNewCobranzaToAttach.getClass(), cobranzaListNewCobranzaToAttach.getIdcobranza());
				attachedCobranzaListNew.add(cobranzaListNewCobranzaToAttach);
			}
			cobranzaListNew = attachedCobranzaListNew;
			abono.setCobranzaList(cobranzaListNew);
			abono = em.merge(abono);
			if (idfacturaOld != null && !idfacturaOld.equals(idfacturaNew)) {
				idfacturaOld.getAbonoList().remove(abono);
				idfacturaOld = em.merge(idfacturaOld);
			}
			if (idfacturaNew != null && !idfacturaNew.equals(idfacturaOld)) {
				idfacturaNew.getAbonoList().add(abono);
				idfacturaNew = em.merge(idfacturaNew);
			}
			for (Cobranza cobranzaListNewCobranza : cobranzaListNew) {
				if (!cobranzaListOld.contains(cobranzaListNewCobranza)) {
					Abono oldIdabonoOfCobranzaListNewCobranza = cobranzaListNewCobranza.getIdabono();
					cobranzaListNewCobranza.setIdabono(abono);
					cobranzaListNewCobranza = em.merge(cobranzaListNewCobranza);
					if (oldIdabonoOfCobranzaListNewCobranza != null && !oldIdabonoOfCobranzaListNewCobranza.equals(abono)) {
						oldIdabonoOfCobranzaListNewCobranza.getCobranzaList().remove(cobranzaListNewCobranza);
						oldIdabonoOfCobranzaListNewCobranza = em.merge(oldIdabonoOfCobranzaListNewCobranza);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = abono.getIdabono();
				if (findAbono(id) == null) {
					throw new NonexistentEntityException("The abono with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Abono abono;
			try {
				abono = em.getReference(Abono.class, id);
				abono.getIdabono();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The abono with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Cobranza> cobranzaListOrphanCheck = abono.getCobranzaList();
			for (Cobranza cobranzaListOrphanCheckCobranza : cobranzaListOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Abono (" + abono + ") cannot be destroyed since the Cobranza " + cobranzaListOrphanCheckCobranza + " in its cobranzaList field has a non-nullable idabono field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			Factura idfactura = abono.getIdfactura();
			if (idfactura != null) {
				idfactura.getAbonoList().remove(abono);
				idfactura = em.merge(idfactura);
			}
			em.remove(abono);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Abono> findAbonoEntities() {
		return findAbonoEntities(true, -1, -1);
	}

	public List<Abono> findAbonoEntities(int maxResults, int firstResult) {
		return findAbonoEntities(false, maxResults, firstResult);
	}

	private List<Abono> findAbonoEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Abono.class));
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

	public Abono findAbono(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Abono.class, id);
		} finally {
			em.close();
		}
	}

	public int getAbonoCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Abono> rt = cq.from(Abono.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
