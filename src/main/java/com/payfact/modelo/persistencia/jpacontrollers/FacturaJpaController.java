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
import com.payfact.modelo.persistencia.entidades.Cliente;
import com.payfact.modelo.persistencia.entidades.Abono;
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
public class FacturaJpaController implements Serializable {

	public FacturaJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Factura factura) {
		if (factura.getAbonoList() == null) {
			factura.setAbonoList(new ArrayList<Abono>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Cliente idcliente = factura.getIdcliente();
			if (idcliente != null) {
				idcliente = em.getReference(idcliente.getClass(), idcliente.getIdcliente());
				factura.setIdcliente(idcliente);
			}
			List<Abono> attachedAbonoList = new ArrayList<Abono>();
			for (Abono abonoListAbonoToAttach : factura.getAbonoList()) {
				abonoListAbonoToAttach = em.getReference(abonoListAbonoToAttach.getClass(), abonoListAbonoToAttach.getIdabono());
				attachedAbonoList.add(abonoListAbonoToAttach);
			}
			factura.setAbonoList(attachedAbonoList);
			em.persist(factura);
			if (idcliente != null) {
				idcliente.getFacturaList().add(factura);
				idcliente = em.merge(idcliente);
			}
			for (Abono abonoListAbono : factura.getAbonoList()) {
				Factura oldIdfacturaOfAbonoListAbono = abonoListAbono.getIdfactura();
				abonoListAbono.setIdfactura(factura);
				abonoListAbono = em.merge(abonoListAbono);
				if (oldIdfacturaOfAbonoListAbono != null) {
					oldIdfacturaOfAbonoListAbono.getAbonoList().remove(abonoListAbono);
					oldIdfacturaOfAbonoListAbono = em.merge(oldIdfacturaOfAbonoListAbono);
				}
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Factura factura) throws IllegalOrphanException, NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Factura persistentFactura = em.find(Factura.class, factura.getIdfactura());
			Cliente idclienteOld = persistentFactura.getIdcliente();
			Cliente idclienteNew = factura.getIdcliente();
			List<Abono> abonoListOld = persistentFactura.getAbonoList();
			List<Abono> abonoListNew = factura.getAbonoList();
			List<String> illegalOrphanMessages = null;
			for (Abono abonoListOldAbono : abonoListOld) {
				if (!abonoListNew.contains(abonoListOldAbono)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Abono " + abonoListOldAbono + " since its idfactura field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			if (idclienteNew != null) {
				idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getIdcliente());
				factura.setIdcliente(idclienteNew);
			}
			List<Abono> attachedAbonoListNew = new ArrayList<Abono>();
			for (Abono abonoListNewAbonoToAttach : abonoListNew) {
				abonoListNewAbonoToAttach = em.getReference(abonoListNewAbonoToAttach.getClass(), abonoListNewAbonoToAttach.getIdabono());
				attachedAbonoListNew.add(abonoListNewAbonoToAttach);
			}
			abonoListNew = attachedAbonoListNew;
			factura.setAbonoList(abonoListNew);
			factura = em.merge(factura);
			if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
				idclienteOld.getFacturaList().remove(factura);
				idclienteOld = em.merge(idclienteOld);
			}
			if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
				idclienteNew.getFacturaList().add(factura);
				idclienteNew = em.merge(idclienteNew);
			}
			for (Abono abonoListNewAbono : abonoListNew) {
				if (!abonoListOld.contains(abonoListNewAbono)) {
					Factura oldIdfacturaOfAbonoListNewAbono = abonoListNewAbono.getIdfactura();
					abonoListNewAbono.setIdfactura(factura);
					abonoListNewAbono = em.merge(abonoListNewAbono);
					if (oldIdfacturaOfAbonoListNewAbono != null && !oldIdfacturaOfAbonoListNewAbono.equals(factura)) {
						oldIdfacturaOfAbonoListNewAbono.getAbonoList().remove(abonoListNewAbono);
						oldIdfacturaOfAbonoListNewAbono = em.merge(oldIdfacturaOfAbonoListNewAbono);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = factura.getIdfactura();
				if (findFactura(id) == null) {
					throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
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
			Factura factura;
			try {
				factura = em.getReference(Factura.class, id);
				factura.getIdfactura();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Abono> abonoListOrphanCheck = factura.getAbonoList();
			for (Abono abonoListOrphanCheckAbono : abonoListOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the Abono " + abonoListOrphanCheckAbono + " in its abonoList field has a non-nullable idfactura field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			Cliente idcliente = factura.getIdcliente();
			if (idcliente != null) {
				idcliente.getFacturaList().remove(factura);
				idcliente = em.merge(idcliente);
			}
			em.remove(factura);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Factura> findFacturaEntities() {
		return findFacturaEntities(true, -1, -1);
	}

	public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
		return findFacturaEntities(false, maxResults, firstResult);
	}

	private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Factura.class));
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

	public Factura findFactura(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Factura.class, id);
		} finally {
			em.close();
		}
	}

	public int getFacturaCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Factura> rt = cq.from(Factura.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
