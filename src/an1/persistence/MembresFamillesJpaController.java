/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package an1.persistence;

import an1.persistence.MembresFamilles;
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
public class MembresFamillesJpaController {

    private EntityTransaction utx = null;



    public EntityManager getEntityManager() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("anbJPA");
    	EntityManager em=emf.createEntityManager();
    	
    	return em;
    }

	public int getMaxId() {
        EntityManager em = getEntityManager();
        try {
            Query q=em.createQuery("select max(o.id) from MembresFamilles as o");
            @SuppressWarnings("rawtypes")
			List l=q.getResultList();
            int max=(Integer)(l.get(0));
            return max;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        finally {
            em.close();
        }

    }

    public void create(MembresFamilles membresFamilles) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	em = getEntityManager();
        	if (membresFamilles.getId()==null) membresFamilles.setId(getMaxId()+1);
            utx=em.getTransaction();
            utx.begin();
            em.persist(membresFamilles);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findMembresFamilles(membresFamilles.getId()) != null) {
                throw new PreexistingEntityException("MembresFamilles " + membresFamilles + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MembresFamilles membresFamilles) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            utx=em.getTransaction();
            utx.begin();
            membresFamilles = em.merge(membresFamilles);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = membresFamilles.getId();
                if (findMembresFamilles(id) == null) {
                    throw new NonexistentEntityException("The membresFamilles with id " + id + " no longer exists.");
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
            MembresFamilles membresFamilles;
            try {
                membresFamilles = em.getReference(MembresFamilles.class, id);
                membresFamilles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The membresFamilles with id " + id + " no longer exists.", enfe);
            }
            em.remove(membresFamilles);
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

    public List<MembresFamilles> findMembresFamillesEntities() {
        return findMembresFamillesEntities(true, -1, -1);
    }

    public List<MembresFamilles> findMembresFamillesEntities(int maxResults, int firstResult) {
        return findMembresFamillesEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
	private List<MembresFamilles> findMembresFamillesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from MembresFamilles as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MembresFamilles findMembresFamilles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MembresFamilles.class, id);
        } finally {
            em.close();
        }
    }

    public int getMembresFamillesCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from MembresFamilles as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
