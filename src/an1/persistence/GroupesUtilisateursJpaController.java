/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package an1.persistence;

import an1.exceptions.NonexistentEntityException;
import an1.exceptions.PreexistingEntityException;
import an1.exceptions.RollbackFailureException;
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
public class GroupesUtilisateursJpaController {
//    @Resource
    private EntityTransaction utx = null;
//    @PersistenceUnit(unitName = "an")
//    private EntityManagerFactory emf = null;


    public EntityManager getEntityManager() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("anbJPA");
    	EntityManager em=emf.createEntityManager();
    	utx = em.getTransaction(); 
    	return em;
    }

    public void create(GroupesUtilisateurs groupesUtilisateurs) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(groupesUtilisateurs);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findGroupesUtilisateurs(groupesUtilisateurs.getNom()) != null) {
                throw new PreexistingEntityException("GroupesUtilisateurs " + groupesUtilisateurs + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GroupesUtilisateurs groupesUtilisateurs) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            groupesUtilisateurs = em.merge(groupesUtilisateurs);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = groupesUtilisateurs.getNom();
                if (findGroupesUtilisateurs(id) == null) {
                    throw new NonexistentEntityException("The groupesUtilisateurs with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            GroupesUtilisateurs groupesUtilisateurs;
            try {
                groupesUtilisateurs = em.getReference(GroupesUtilisateurs.class, id);
                groupesUtilisateurs.getNom();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The groupesUtilisateurs with id " + id + " no longer exists.", enfe);
            }
            em.remove(groupesUtilisateurs);
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

    public List<GroupesUtilisateurs> findGroupesUtilisateursEntities() {
        return findGroupesUtilisateursEntities(true, -1, -1);
    }

    public List<GroupesUtilisateurs> findGroupesUtilisateursEntities(int maxResults, int firstResult) {
        return findGroupesUtilisateursEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
	private List<GroupesUtilisateurs> findGroupesUtilisateursEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from GroupesUtilisateurs as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public GroupesUtilisateurs findGroupesUtilisateurs(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GroupesUtilisateurs.class, id);
        } finally {
            em.close();
        }
    }

    public int getGroupesUtilisateursCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from GroupesUtilisateurs as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
