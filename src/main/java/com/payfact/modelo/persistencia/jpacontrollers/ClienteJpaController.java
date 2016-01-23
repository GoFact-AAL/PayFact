/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payfact.modelo.persistencia.jpacontrollers;

import com.payfact.modelo.persistencia.entidades.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.payfact.modelo.persistencia.entidades.Factura;
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
public class ClienteJpaController implements Serializable {

	public ClienteJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Cliente cliente) {
		if (cliente.getFacturaList() == null) {
			cliente.setFacturaList(new ArrayList<Factura>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			List<Factura> attachedFacturaList = new ArrayList<Factura>();
			for (Factura facturaListFacturaToAttach : cliente.getFacturaList()) {
				facturaListFacturaToAttach = em.getReference(facturaListFacturaToAttach.getClass(), facturaListFacturaToAttach.getIdfactura());
				attachedFacturaList.add(facturaListFacturaToAttach);
			}
			cliente.setFacturaList(attachedFacturaList);
			em.persist(cliente);
			for (Factura facturaListFactura : cliente.getFacturaList()) {
				Cliente oldIdclienteOfFacturaListFactura = facturaListFactura.getIdcliente();
				facturaListFactura.setIdcliente(cliente);
				facturaListFactura = em.merge(facturaListFactura);
				if (oldIdclienteOfFacturaListFactura != null) {
					oldIdclienteOfFacturaListFactura.getFacturaList().remove(facturaListFactura);
					oldIdclienteOfFacturaListFactura = em.merge(oldIdclienteOfFacturaListFactura);
				}
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Cliente persistentCliente = em.find(Cliente.class, cliente.getIdcliente());
			List<Factura> facturaListOld = persistentCliente.getFacturaList();
			List<Factura> facturaListNew = cliente.getFacturaList();
			List<String> illegalOrphanMessages = null;
			for (Factura facturaListOldFactura : facturaListOld) {
				if (!facturaListNew.contains(facturaListOldFactura)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Factura " + facturaListOldFactura + " since its idcliente field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			List<Factura> attachedFacturaListNew = new ArrayList<Factura>();
			for (Factura facturaListNewFacturaToAttach : facturaListNew) {
				facturaListNewFacturaToAttach = em.getReference(facturaListNewFacturaToAttach.getClass(), facturaListNewFacturaToAttach.getIdfactura());
				attachedFacturaListNew.add(facturaListNewFacturaToAttach);
			}
			facturaListNew = attachedFacturaListNew;
			cliente.setFacturaList(facturaListNew);
			cliente = em.merge(cliente);
			for (Factura facturaListNewFactura : facturaListNew) {
				if (!facturaListOld.contains(facturaListNewFactura)) {
					Cliente oldIdclienteOfFacturaListNewFactura = facturaListNewFactura.getIdcliente();
					facturaListNewFactura.setIdcliente(cliente);
					facturaListNewFactura = em.merge(facturaListNewFactura);
					if (oldIdclienteOfFacturaListNewFactura != null && !oldIdclienteOfFacturaListNewFactura.equals(cliente)) {
						oldIdclienteOfFacturaListNewFactura.getFacturaList().remove(facturaListNewFactura);
						oldIdclienteOfFacturaListNewFactura = em.merge(oldIdclienteOfFacturaListNewFactura);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = cliente.getIdcliente();
				if (findCliente(id) == null) {
					throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
			Cliente cliente;
			try {
				cliente = em.getReference(Cliente.class, id);
				cliente.getIdcliente();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Factura> facturaListOrphanCheck = cliente.getFacturaList();
			for (Factura facturaListOrphanCheckFactura : facturaListOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Factura " + facturaListOrphanCheckFactura + " in its facturaList field has a non-nullable idcliente field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(cliente);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Cliente> findClienteEntities() {
		return findClienteEntities(true, -1, -1);
	}

	public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
		return findClienteEntities(false, maxResults, firstResult);
	}

	private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Cliente.class));
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

	public Cliente findCliente(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Cliente.class, id);
		} finally {
			em.close();
		}
	}

	public int getClienteCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Cliente> rt = cq.from(Cliente.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
