/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package an1.persistence;

import an1.exceptions.NonexistentEntityException;
import an1.exceptions.PreexistingEntityException;
import an1.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

//import javax.transaction.UserTransaction;

/**
 * 
 * @author bbernard
 */
public class EcrituresJpaController {

	private EntityTransaction utx = null;

	public EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("anbJPA");
		EntityManager em = emf.createEntityManager();
		return em;
	}

	public void create(Ecritures ecriture) throws PreexistingEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;

		try {
			em = getEntityManager();
			utx = em.getTransaction();

			utx.begin();
			em.persist(ecriture);
			utx.commit();
		} catch (Exception ex) {
			try {
				utx.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException(
						"An error occurred attempting to roll back the transaction.",
						re);
			}
			if (findEcritures(ecriture.getNumero()) != null) {
				throw new PreexistingEntityException("Ecriture " + ecriture
						+ " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Ecritures ecriture) throws NonexistentEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			utx = em.getTransaction();
			utx.begin();
			ecriture = em.merge(ecriture);
			utx.commit();
		} catch (Exception ex) {
			try {
				utx.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException(
						"An error occurred attempting to roll back the transaction.",
						re);
			}
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = ecriture.getNumero();
				if (findEcritures(id) == null) {
					throw new NonexistentEntityException("The membres with id "
							+ id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			utx = em.getTransaction();
			utx.begin();
			Ecritures ecriture;
			try {
				ecriture = em.getReference(Ecritures.class, id);
				ecriture.getCode();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The ecriture with id "
						+ id + " no longer exists.", enfe);
			}
			em.remove(ecriture);
			utx.commit();
		} catch (Exception ex) {
			try {
				utx.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException(
						"An error occurred attempting to roll back the transaction.",
						re);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Ecritures> findEcrituresEntities() {
		return findEcrituresEntities(true, -1, -1);
	}

	public List<Ecritures> findEcrituresEntities(int maxResults, int firstResult) {
		return findEcrituresEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<Ecritures> findEcrituresEntities(boolean all, int maxResults,
			int firstResult) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select object(o) from ecritures as o");
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Ecritures findEcritures(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Ecritures.class, id);
		} finally {
			em.close();
		}
	}

	public int getEcrituresCount() {
		EntityManager em = getEntityManager();
		try {
			return ((Long) em
					.createQuery("select count(o) from ecritures as o")
					.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Ecritures> getEcritures(Membres m) {
		// TODO Auto-generated method stub
		EntityManager em = getEntityManager();
		try {
			Query q = em
					.createQuery("select object(o) from Ecritures as o where o.membre=:membre");
			q.setParameter("membre", m);
			return q.getResultList();

		} finally {
			em.close();
		}
	}

	public float getSoldeEcritures(Membres m) {
		EntityManager em = getEntityManager();
		try {
			Query q = em
					.createQuery("select sum(o.credit) from Ecritures as o where o.membre=:membre");
			q.setParameter("membre", m);
			Number credit = (Number) q.getSingleResult();
			q = em
					.createQuery("select sum(o.debit) from Ecritures as o where o.membre=:membre");
			q.setParameter("membre", m);
			Number debit = (Number) q.getSingleResult();
			float creditf=0;
			if (credit!=null) creditf=credit.floatValue();
			float debitf=0;
			if (debit!=null) debitf=debit.floatValue();
			return creditf - debitf;
		} finally {
			em.close();

		}

	}
}
