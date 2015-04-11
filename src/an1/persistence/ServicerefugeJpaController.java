/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package an1.persistence;

import an1.exceptions.NonexistentEntityException;
import an1.exceptions.PreexistingEntityException;
import an1.exceptions.RollbackFailureException;
import java.util.Date;
import java.util.List;
//import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
//import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
//import javax.transaction.UserTransaction;

/**
 *
 * @author bbernard
 */
public class ServicerefugeJpaController {

    private EntityTransaction utx = null;
//    @PersistenceUnit(unitName = "an")
//    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("anbJPA");
    	EntityManager em=emf.createEntityManager();
    	utx = em.getTransaction(); 
    	return em;
    }

    public void create(Servicerefuge servicerefuge) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            utx.begin();
            em.persist(servicerefuge);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findServicerefuge(servicerefuge.getId()) != null) {
                throw new PreexistingEntityException("Servicerefuge " + servicerefuge + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicerefuge servicerefuge) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            utx.begin();
            servicerefuge = em.merge(servicerefuge);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = servicerefuge.getId();
                if (findServicerefuge(id) == null) {
                    throw new NonexistentEntityException("The servicerefuge with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            utx.begin();
            Servicerefuge servicerefuge;
            try {
                servicerefuge = em.getReference(Servicerefuge.class, id);
                servicerefuge.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicerefuge with id " + id + " no longer exists.", enfe);
            }
            em.remove(servicerefuge);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servicerefuge> findServicerefugeEntities() {
        return findServicerefugeEntities(true, -1, -1);
    }

    public List<Servicerefuge> findServicerefugeEntities(int maxResults, int firstResult) {
        return findServicerefugeEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
	private List<Servicerefuge> findServicerefugeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Servicerefuge as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Servicerefuge findServicerefuge(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicerefuge.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicerefugeCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Servicerefuge as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
	public List<Servicerefuge> getServiceRefuge(Membres m){
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Servicerefuge as o where o.membre=:membre");
            q.setParameter("membre", m);
            return q.getResultList();
        } finally {
            em.close();
        }

    }

    public void effaceServicesRefugeMembre(Membres membre) {
    	for (Servicerefuge service :getServiceRefuge(membre)) {
    		 try {
				destroy(service.getId());
			} catch (NonexistentEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RollbackFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    @SuppressWarnings("unchecked")
	public List<Servicerefuge> getServiceRefuge(Date jour){
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Servicerefuge as o where o.dateDebut<=:idDate and o.dateFin>=:idDate");
            q.setParameter("idDate", jour);
            System.out.println(jour);
            System.out.println(q.getResultList());
            return q.getResultList();
        } finally {
            em.close();
        }

    }

}
