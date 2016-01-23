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
import com.payfact.modelo.persistencia.entidades.Usuario;
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
public class UsuarioJpaController implements Serializable {

	public UsuarioJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Usuario usuario) {
		if (usuario.getCobranzaList() == null) {
			usuario.setCobranzaList(new ArrayList<Cobranza>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			List<Cobranza> attachedCobranzaList = new ArrayList<Cobranza>();
			for (Cobranza cobranzaListCobranzaToAttach : usuario.getCobranzaList()) {
				cobranzaListCobranzaToAttach = em.getReference(cobranzaListCobranzaToAttach.getClass(), cobranzaListCobranzaToAttach.getIdcobranza());
				attachedCobranzaList.add(cobranzaListCobranzaToAttach);
			}
			usuario.setCobranzaList(attachedCobranzaList);
			em.persist(usuario);
			for (Cobranza cobranzaListCobranza : usuario.getCobranzaList()) {
				Usuario oldIdusuarioOfCobranzaListCobranza = cobranzaListCobranza.getIdusuario();
				cobranzaListCobranza.setIdusuario(usuario);
				cobranzaListCobranza = em.merge(cobranzaListCobranza);
				if (oldIdusuarioOfCobranzaListCobranza != null) {
					oldIdusuarioOfCobranzaListCobranza.getCobranzaList().remove(cobranzaListCobranza);
					oldIdusuarioOfCobranzaListCobranza = em.merge(oldIdusuarioOfCobranzaListCobranza);
				}
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdusuario());
			List<Cobranza> cobranzaListOld = persistentUsuario.getCobranzaList();
			List<Cobranza> cobranzaListNew = usuario.getCobranzaList();
			List<String> illegalOrphanMessages = null;
			for (Cobranza cobranzaListOldCobranza : cobranzaListOld) {
				if (!cobranzaListNew.contains(cobranzaListOldCobranza)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Cobranza " + cobranzaListOldCobranza + " since its idusuario field is not nullable.");
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
			usuario.setCobranzaList(cobranzaListNew);
			usuario = em.merge(usuario);
			for (Cobranza cobranzaListNewCobranza : cobranzaListNew) {
				if (!cobranzaListOld.contains(cobranzaListNewCobranza)) {
					Usuario oldIdusuarioOfCobranzaListNewCobranza = cobranzaListNewCobranza.getIdusuario();
					cobranzaListNewCobranza.setIdusuario(usuario);
					cobranzaListNewCobranza = em.merge(cobranzaListNewCobranza);
					if (oldIdusuarioOfCobranzaListNewCobranza != null && !oldIdusuarioOfCobranzaListNewCobranza.equals(usuario)) {
						oldIdusuarioOfCobranzaListNewCobranza.getCobranzaList().remove(cobranzaListNewCobranza);
						oldIdusuarioOfCobranzaListNewCobranza = em.merge(oldIdusuarioOfCobranzaListNewCobranza);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = usuario.getIdusuario();
				if (findUsuario(id) == null) {
					throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
			Usuario usuario;
			try {
				usuario = em.getReference(Usuario.class, id);
				usuario.getIdusuario();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Cobranza> cobranzaListOrphanCheck = usuario.getCobranzaList();
			for (Cobranza cobranzaListOrphanCheckCobranza : cobranzaListOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Cobranza " + cobranzaListOrphanCheckCobranza + " in its cobranzaList field has a non-nullable idusuario field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(usuario);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Usuario> findUsuarioEntities() {
		return findUsuarioEntities(true, -1, -1);
	}

	public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
		return findUsuarioEntities(false, maxResults, firstResult);
	}

	private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Usuario.class));
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

	public Usuario findUsuario(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Usuario.class, id);
		} finally {
			em.close();
		}
	}

	public int getUsuarioCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Usuario> rt = cq.from(Usuario.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
