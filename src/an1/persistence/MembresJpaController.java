/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package an1.persistence;

import an1.persistence.Membres;
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
public class MembresJpaController {
    
    private EntityTransaction utx = null;

    public EntityManager getEntityManager() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("anbJPA");
    	EntityManager em=emf.createEntityManager();
    	return em;
    }

    public void create(Membres membres) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        
        try {
            em = getEntityManager();
        	utx = em.getTransaction(); 
        	
        	utx.begin(); 
            em.persist(membres);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findMembres(membres.getCode()) != null) {
                throw new PreexistingEntityException("Membres " + membres + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Membres membres) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
        	utx = em.getTransaction(); 
            utx.begin();
            membres = em.merge(membres);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = membres.getCode();
                if (findMembres(id) == null) {
                    throw new NonexistentEntityException("The membres with id " + id + " no longer exists.");
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
        	utx = em.getTransaction(); 
            utx.begin();
            Membres membres;
            try {
                membres = em.getReference(Membres.class, id);
                membres.getCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The membres with id " + id + " no longer exists.", enfe);
            }
            em.remove(membres);
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

    public List<Membres> findMembresEntities() {
        return findMembresEntities(true, -1, -1);
    }

    public List<Membres> findMembresEntities(int maxResults, int firstResult) {
        return findMembresEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
	private List<Membres> findMembresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Membres as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Membres findMembres(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Membres.class, id);
        } finally {
            em.close();
        }
    }

    public int getMembresCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Membres as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Membres findMembre(String nomUtilisateur,String motDePasse) {
        EntityManager em = getEntityManager();
        try {
            String command="select object(o) from Membres as o where o.nomUtilisateur='"+nomUtilisateur+"' and o.password='"+motDePasse+"'";
            Query q=em.createQuery(command);
            return (Membres) q.getSingleResult();
        }
        catch (Exception e) {
            return null;
        }
        finally {
            em.close();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Membres> rechercheMembresParNomPartiel(String nom) {
        EntityManager em = getEntityManager();
        try {
            String command="select object(o) from Membres as o  where o.nom like :nomre order by o.nom";
            Query q=em.createQuery(command);
            q.setParameter("nomre", nom+"%");
            return q.getResultList();
        }
        finally {
            em.close();
        }
	
    }
    
    @SuppressWarnings("unchecked")
	public List<Membres> findMembresTrieParNom() {
               EntityManager em = getEntityManager();
        try {
            String command="select object(o) from Membres as o order by o.nom";
            Query q=em.createQuery(command);
            return q.getResultList();
        }
        finally {
            em.close();
        }

    }

}
