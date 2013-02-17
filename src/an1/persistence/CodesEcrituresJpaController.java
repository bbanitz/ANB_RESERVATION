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
public class CodesEcrituresJpaController {

	private EntityTransaction utx = null;

	public EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("anbJPA");
		EntityManager em = emf.createEntityManager();
		return em;
	}

	public void create(Codesecriture codeEcriture) throws PreexistingEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;

		try {
			em = getEntityManager();
			utx = em.getTransaction();

			utx.begin();
			em.persist(codeEcriture);
			utx.commit();
		} catch (Exception ex) {
			try {
				utx.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException(
						"An error occurred attempting to roll back the transaction.",
						re);
			}
			if (findCodeEcritures(codeEcriture.getCode()) != null) {
				throw new PreexistingEntityException("codeEcriture " + codeEcriture
						+ " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Codesecriture codeEcriture) throws NonexistentEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			utx = em.getTransaction();
			utx.begin();
			codeEcriture = em.merge(codeEcriture);
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
				String code = codeEcriture.getCode();
				if (findCodeEcritures(code) == null) {
					throw new NonexistentEntityException("The membres with id "
							+ code + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(String code) throws NonexistentEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			utx = em.getTransaction();
			utx.begin();
			Codesecriture codeEcriture;
			try {
				codeEcriture = em.getReference(Codesecriture.class, code);
				codeEcriture.getCode();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The codeecriture with id "
						+ code + " no longer exists.", enfe);
			}
			em.remove(codeEcriture);
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

	public List<Codesecriture> findEcrituresEntities() {
		return findCodeEcrituresEntities(true, -1, -1);
	}

	public List<Codesecriture> findEcrituresEntities(int maxResults, int firstResult) {
		return findCodeEcrituresEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<Codesecriture> findCodeEcrituresEntities(boolean all, int maxResults,
			int firstResult) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select object(o) from Codesecriture as o");
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Codesecriture findCodeEcritures(String code) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Codesecriture.class, code);
		} finally {
			em.close();
		}
	}

	public int getCodeEcrituresCount() {
		EntityManager em = getEntityManager();
		try {
			return ((Long) em
					.createQuery("select count(o) from Codesecriture as o")
					.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
