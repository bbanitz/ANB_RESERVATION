/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package an1.persistence;

import an1.exceptions.NonexistentEntityException;
import an1.exceptions.PreexistingEntityException;
import an1.exceptions.RollbackFailureException;

import java.util.ArrayList;
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
public class ReservationsJpaController {
	// @Resource
	private EntityTransaction utx = null;

	// @PersistenceUnit(unitName = "an")
	// private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("anbJPA");
		EntityManager em = emf.createEntityManager();

		return em;
	}

	public void create(Reservations reservations)
			throws PreexistingEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			utx = em.getTransaction();
			utx.begin();
			em.persist(reservations);
			utx.commit();
		} catch (Exception ex) {
			try {
				utx.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException(
						"An error occurred attempting to roll back the transaction.",
						re);
			}
			if (findReservations(reservations.getId()) != null) {
				throw new PreexistingEntityException("Reservations "
						+ reservations + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Reservations reservations)
			throws NonexistentEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			utx = em.getTransaction();
			utx.begin();
			reservations = em.merge(reservations);
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
				Integer id = reservations.getId();
				if (findReservations(id) == null) {
					throw new NonexistentEntityException(
							"The reservations with id " + id
									+ " no longer exists.");
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
			Reservations reservations;
			try {
				reservations = em.getReference(Reservations.class, id);
				reservations.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException(
						"The reservations with id " + id + " no longer exists.",
						enfe);
			}
			em.remove(reservations);
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

	public List<Reservations> findReservationsEntities() {
		return findReservationsEntities(true, -1, -1);
	}

	public List<Reservations> findReservationsEntities(int maxResults,
			int firstResult) {
		return findReservationsEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<Reservations> findReservationsEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select object(o) from Reservations as o");
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Reservations findReservations(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Reservations.class, id);
		} finally {
			em.close();
		}
	}

	public int getReservationsCount() {
		EntityManager em = getEntityManager();
		try {
			return ((Long) em.createQuery(
					"select count(o) from Reservations as o").getSingleResult())
					.intValue();
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Reservations> getReservationsMembre(Membres membre) {
		EntityManager em = getEntityManager();
		try {
			Query q = em
					.createQuery("select object(o) from Reservations as o where o.membre=:membre");
			q.setParameter("membre", membre);
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Reservations> getReservations(Date jour, String[] selectedValues) {
		EntityManager em = getEntityManager();
		try {
			StringBuffer requete = new StringBuffer();
			boolean premier = true;
			requete.append("select object(o) from Reservations as o where o.dateDebut<=:idDate and o.dateFin>=:idDate");
			if (selectedValues.length > 0) {
				requete.append(" and (");

				for (String selected : selectedValues) {
					if (premier)
						premier = false;
					else
						requete.append(" or ");
					if (selected.equals(Reservations.StatutReservation.Demande
							.toString()))
						requete.append("o.statut=\"" + selected + "\"");
					else if (selected
							.equals(Reservations.StatutReservation.Option
									.toString()))
						requete.append("o.statut=\"" + selected + "\"");
					else if (selected
							.equals(Reservations.StatutReservation.Ferme
									.toString()))
						requete.append("o.statut=\"" + selected + "\"");
					else if (selected
							.equals(Reservations.StatutReservation.AcomptePayé
									.toString()))
						requete.append("o.statut=\"" + selected + "\"");
					else if (selected
							.equals(Reservations.StatutReservation.Payé
									.toString()))
						requete.append("o.statut=\"" + selected + "\"");
				}
				requete.append(")");
			}
			else return new ArrayList<Reservations>();
			System.out.println(requete);
			Query q = em.createQuery(requete.toString());
			q.setParameter("idDate", jour);
			return q.getResultList();
		} finally {
			em.close();
		}

	}

	public void effaceReservationsMembre(Membres membre) {
		 for (Reservations reservation:getReservationsMembre(membre)) {
			 try {
				destroy(reservation.getId());
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
	public List<Reservations> LitReservationsStatut(String[] selectedValues) {
		StringBuffer requete = new StringBuffer();
		if (selectedValues == null || selectedValues.length == 0) {
			return null;
		}
		for (String s : selectedValues)
			System.out.println(s);
		boolean premier = true;
		requete.append("select object(o) from Reservations as o where ");
		for (String selected : selectedValues) {
			if (premier)
				premier = false;
			else
				requete.append(" or ");
			if (selected.equals(Reservations.StatutReservation.Demande
					.toString()))
				requete.append("o.statut=\"" + selected + "\"");
			else if (selected.equals(Reservations.StatutReservation.Option
					.toString()))
				requete.append("o.statut=\"" + selected + "\"");
			else if (selected.equals(Reservations.StatutReservation.Ferme
					.toString()))
				requete.append("o.statut=\"" + selected + "\"");
			else if (selected.equals(Reservations.StatutReservation.AcomptePayé
					.toString()))
				requete.append("o.statut=\"" + selected + "\"");
			else if (selected.equals(Reservations.StatutReservation.Payé
					.toString()))
				requete.append("o.statut=\"" + selected + "\"");
		}
		System.out.println(requete);
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery(requete.toString());
			return q.getResultList();
		} finally {
			em.close();
		}
	}

}
