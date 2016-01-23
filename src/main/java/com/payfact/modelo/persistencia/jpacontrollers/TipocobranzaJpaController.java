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
import com.payfact.modelo.persistencia.entidades.Cobranza;
import com.payfact.modelo.persistencia.entidades.Tipocobranza;
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
public class TipocobranzaJpaController implements Serializable {

	public TipocobranzaJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Tipocobranza tipocobranza) {
		if (tipocobranza.getCobranzaList() == null) {
			tipocobranza.setCobranzaList(new ArrayList<Cobranza>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			List<Cobranza> attachedCobranzaList = new ArrayList<Cobranza>();
			for (Cobranza cobranzaListCobranzaToAttach : tipocobranza.getCobranzaList()) {
				cobranzaListCobranzaToAttach = em.getReference(cobranzaListCobranzaToAttach.getClass(), cobranzaListCobranzaToAttach.getIdcobranza());
				attachedCobranzaList.add(cobranzaListCobranzaToAttach);
			}
			tipocobranza.setCobranzaList(attachedCobranzaList);
			em.persist(tipocobranza);
			for (Cobranza cobranzaListCobranza : tipocobranza.getCobranzaList()) {
				Tipocobranza oldIdtipocobranzaOfCobranzaListCobranza = cobranzaListCobranza.getIdtipocobranza();
				cobranzaListCobranza.setIdtipocobranza(tipocobranza);
				cobranzaListCobranza = em.merge(cobranzaListCobranza);
				if (oldIdtipocobranzaOfCobranzaListCobranza != null) {
					oldIdtipocobranzaOfCobranzaListCobranza.getCobranzaList().remove(cobranzaListCobranza);
					oldIdtipocobranzaOfCobranzaListCobranza = em.merge(oldIdtipocobranzaOfCobranzaListCobranza);
				}
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Tipocobranza tipocobranza) throws IllegalOrphanException, NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Tipocobranza persistentTipocobranza = em.find(Tipocobranza.class, tipocobranza.getIdtipocobranza());
			List<Cobranza> cobranzaListOld = persistentTipocobranza.getCobranzaList();
			List<Cobranza> cobranzaListNew = tipocobranza.getCobranzaList();
			List<String> illegalOrphanMessages = null;
			for (Cobranza cobranzaListOldCobranza : cobranzaListOld) {
				if (!cobranzaListNew.contains(cobranzaListOldCobranza)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Cobranza " + cobranzaListOldCobranza + " since its idtipocobranza field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			List<Cobranza> attachedCobranzaListNew = new ArrayList<Cobranza>();
			for (Cobranza cobranzaListNewCobranzaToAttach : cobranzaListNew) {
				cobranzaListNewCobranzaToAttach = em.getReference(cobranzaListNewCobranzaToAttach.getClass(), cobranzaListNewCobranzaToAttach.getIdcobranza());
				attachedCobranzaListNew.add(cobranzaListNewCobranzaToAttach);
			}
			cobranzaListNew = attachedCobranzaListNew;
			tipocobranza.setCobranzaList(cobranzaListNew);
			tipocobranza = em.merge(tipocobranza);
			for (Cobranza cobranzaListNewCobranza : cobranzaListNew) {
				if (!cobranzaListOld.contains(cobranzaListNewCobranza)) {
					Tipocobranza oldIdtipocobranzaOfCobranzaListNewCobranza = cobranzaListNewCobranza.getIdtipocobranza();
					cobranzaListNewCobranza.setIdtipocobranza(tipocobranza);
					cobranzaListNewCobranza = em.merge(cobranzaListNewCobranza);
					if (oldIdtipocobranzaOfCobranzaListNewCobranza != null && !oldIdtipocobranzaOfCobranzaListNewCobranza.equals(tipocobranza)) {
						oldIdtipocobranzaOfCobranzaListNewCobranza.getCobranzaList().remove(cobranzaListNewCobranza);
						oldIdtipocobranzaOfCobranzaListNewCobranza = em.merge(oldIdtipocobranzaOfCobranzaListNewCobranza);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = tipocobranza.getIdtipocobranza();
				if (findTipocobranza(id) == null) {
					throw new NonexistentEntityException("The tipocobranza with id " + id + " no longer exists.");
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
			Tipocobranza tipocobranza;
			try {
				tipocobranza = em.getReference(Tipocobranza.class, id);
				tipocobranza.getIdtipocobranza();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The tipocobranza with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Cobranza> cobranzaListOrphanCheck = tipocobranza.getCobranzaList();
			for (Cobranza cobranzaListOrphanCheckCobranza : cobranzaListOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Tipocobranza (" + tipocobranza + ") cannot be destroyed since the Cobranza " + cobranzaListOrphanCheckCobranza + " in its cobranzaList field has a non-nullable idtipocobranza field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(tipocobranza);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Tipocobranza> findTipocobranzaEntities() {
		return findTipocobranzaEntities(true, -1, -1);
	}

	public List<Tipocobranza> findTipocobranzaEntities(int maxResults, int firstResult) {
		return findTipocobranzaEntities(false, maxResults, firstResult);
	}

	private List<Tipocobranza> findTipocobranzaEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Tipocobranza.class));
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

	public Tipocobranza findTipocobranza(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Tipocobranza.class, id);
		} finally {
			em.close();
		}
	}

	public int getTipocobranzaCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Tipocobranza> rt = cq.from(Tipocobranza.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
