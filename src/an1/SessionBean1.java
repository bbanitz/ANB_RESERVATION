/*
 * SessionBean1.java
 *
 * Created on 13 janv. 2009, 08:46:39
 * Copyright bbernard
 */
package an1;

import an1.exceptions.NonexistentEntityException;
import an1.exceptions.PreexistingEntityException;
import an1.exceptions.RollbackFailureException;
import an1.persistence.CodesEcrituresJpaController;
import an1.persistence.Ecritures;
import an1.persistence.EcrituresJpaController;
import an1.persistence.MembresJpaController;
import an1.persistence.Membres;
import an1.persistence.MembresFamilles;
import an1.persistence.GroupesUtilisateurs;
import an1.persistence.GroupesUtilisateursJpaController;
import an1.persistence.MembresFamillesJpaController;
import an1.persistence.Reservations;
import an1.persistence.ReservationsJpaController;
import an1.persistence.Servicerefuge;
import an1.persistence.ServicerefugeJpaController; //import com.sun.rave.web.ui.appbase.AbstractSessionBean;
//import com.sun.sql.rowset.CachedRowSetXImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Session scope data bean for your application. Create properties here to
 * represent cached data that should be made available across multiple HTTP
 * requests for an individual user.
 * </p>
 * 
 * <p>
 * An instance of this class will be created for you automatically, the first
 * time your application evaluates a value binding expression or method binding
 * expression that references a managed bean using this class.
 * </p>
 */
public class SessionBean1 {
	private MembresFamillesJpaController membresFamillesJpaController;
	private String Connecté = "non connecté";
    private Membres MembreLoggé;
    private Boolean retourVersMembre=false;
    private Ecritures ecritureEnCours;
    private CodesEcrituresJpaController codesEcrituresJpaController;
    private String[] selectedCheckboxAffichage={"Demande","Option","Ferme","AcomptePayé","Payé"};
    private static final SelectItem[] checkBoxAffichage = new SelectItem[]{
        new SelectItem(Reservations.StatutReservation.Demande),
        new SelectItem(Reservations.StatutReservation.Option),
        new SelectItem(Reservations.StatutReservation.Ferme),
        new SelectItem(Reservations.StatutReservation.AcomptePayé),
        new SelectItem(Reservations.StatutReservation.Payé)};
	
	public Membres getMembreLoggé() {
		return MembreLoggé;
	}

	public void setMembreLoggé(Membres membreLoggé) {
		MembreLoggé = membreLoggé;
	}

	public String getConnecté() {
		return Connecté;
	}

    public void voirActionListener(ActionEvent event) {
    	 setMembreEnCours((Membres) event.getComponent().getAttributes().get("membre"));
    	 
    	 System.out.println("Membre"+membreEnCours.getNom());
    }
	public String voirMembre() {
    	 
     	return "listemembres";
    }
	public void statusChanged(ValueChangeEvent vce) {
		System.out.println("statut changed");
	}
	public String getMembreEnCoursString() {
		if (membreEnCours == null)
			return "pas de membre en cours";
		StringBuffer message = new StringBuffer();
		message.append("Nom: " + membreEnCours.getNom() + "<br/>");
		message.append("PrÃ©nom: " + membreEnCours.getPrenom() + "<br/>");
		message.append("adresse1: " + membreEnCours.getAdresse1() + "<br/>");
		message.append("adresse2: " + membreEnCours.getAdresse2() + "<br/>");
		message.append("Code postal: " + membreEnCours.getCodePostal()
				+ "<br/>");
		message.append("Ville: " + membreEnCours.getVille() + "<br/>");
		message.append("Email: " + membreEnCours.getEmail() + "<br/>");
		message.append("TÃ©lÃ©phone domicile: "
				+ membreEnCours.getTelephoneDomicile() + "<br/>");
		message.append("TÃ©lÃ©phone portable: " + membreEnCours.getTelephoneGsm()
				+ "<br/>");
		message.append("mot de passe: " + membreEnCours.getPassword()
				+ "<br/>");
		return message.toString();
	}

	public void setConnecté(String connecté) {
		Connecté = connecté;
	}

	void editMembreEnCours() {
		try {
			membresJpaController.edit(membreEnCours);

		} catch (NonexistentEntityException ex) {
			Logger.getLogger(SessionBean1.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (RollbackFailureException ex) {
			Logger.getLogger(SessionBean1.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (Exception ex) {
			Logger.getLogger(SessionBean1.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	void nouveauMembreFamille() {
		MembresFamilles mf = new MembresFamilles(membreEnCours);

		try {
			membresFamillesJpaController.create(mf);
			membreEnCours.getMembresFamille().add(mf);
		} catch (PreexistingEntityException ex) {
			Logger.getLogger(SessionBean1.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (RollbackFailureException ex) {
			Logger.getLogger(SessionBean1.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (Exception ex) {
			Logger.getLogger(SessionBean1.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}

	/**
	 * <p>
	 * Automatically managed component initialization. <strong>WARNING:</strong>
	 * This method is automatically generated, so any user-specified code
	 * inserted here is subject to being replaced.
	 * </p>
	 */
	// private void _init() throws Exception {
	// membresRowSet.setDataSourceName("java:comp/env/jdbc/AN_MySQL");
	// membresRowSet.setCommand("SELECT * FROM membres");
	// membresRowSet.setTableName("membres");
	// }
	// </editor-fold>

	/**
	 * <p>
	 * Construct a new session data bean instance.
	 * </p>
	 */
	private GroupesUtilisateurs groupeUtilisateursEnCours;
	private Membres membreEnCours = null;
	private List<MembresFamilles> membresFamilleEnCours;

	private List<Servicerefuge> servicesRefugeEnCours;
	private List<Membres> listeMembres = new ArrayList<Membres>();
	private MembresJpaController membresJpaController;
	private GroupesUtilisateursJpaController groupesUtilisateursJpaController;
	// private CachedRowSetXImpl membresRowSet = new CachedRowSetXImpl();
	private ServicerefugeJpaController serviceRefugeJpaController;
	private Servicerefuge serviceRefugeEnCours;
	private CalendrierUtil calendrier1;
	private ReservationsJpaController reservationsJPAControler;
	private Reservations reservationEnCours;
	private List<Reservations> reservationsMembreEnCours;
	private EcrituresJpaController ecrituresJPAController;

	/*
	 * public CachedRowSetXImpl getMembresRowSet() { return membresRowSet; }
	 * 
	 * public void setMembresRowSet(CachedRowSetXImpl crsxi) {
	 * this.membresRowSet = crsxi; }
	 */

	public SessionBean1() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			membresJpaController = new MembresJpaController();
			membresFamillesJpaController = new MembresFamillesJpaController(); // facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(),
																				// null,
																				// "MembresFamillesJpaController");
			groupesUtilisateursJpaController = new GroupesUtilisateursJpaController();// facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(),
																						// null,
																						// "GroupesUtilisateursJpaController");
			serviceRefugeJpaController = new ServicerefugeJpaController();// facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(),
																			// null,
																			// "ServicerefugeJpaController");
			reservationsJPAControler = new ReservationsJpaController(); // facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(),
																		// null,
																		// "ReservationsJpaController");
			setEcrituresJPAController(new EcrituresJpaController());
			
			setCodesEcrituresJpaController(new CodesEcrituresJpaController());
			
			litTableMembres();
			
			calendrier1 = new CalendrierUtil(new Date(),
					getServiceRefugeJpaController(),
					getReservationsJPAControler());
		}

	}

	/**
	 * @return the membreEnCours
	 */
	public void LitMembresParNom(String nom) {
		listeMembres=membresJpaController.rechercheMembresParNomPartiel(nom);
	}
	
	public Membres getMembreEnCours() {
		return membreEnCours;
	}

	/**
	 * @param membreEnCours
	 *            the membreEnCours to set
	 */
	public void setMembreEnCours(Membres membreEnCours) {
		this.membreEnCours = membreEnCours;
		servicesRefugeEnCours = getServiceRefugeJpaController()
				.getServiceRefuge(membreEnCours);
		reservationsMembreEnCours = reservationsJPAControler
				.getReservationsMembre(membreEnCours);
	}

	/**
	 * @return the listeMembres
	 */
	public List<Membres> getListeMembres() {
		return listeMembres;
	}

	/**
	 * @param listeMembres
	 *            the listeMembres to set
	 */
	public void setListeMembres(List<Membres> listeMembres) {
		this.listeMembres = listeMembres;
	}

	/**
	 * @return the membresJpaController
	 */
	public MembresJpaController getMembresJpaController() {
		return membresJpaController;
	}

	/**
	 * @return the autorisationsJpaController
	 */
	public GroupesUtilisateursJpaController getGroupesUtilisateursJpaController() {
		return groupesUtilisateursJpaController;
	}

	/**
	 * @return the membresFamilleEnCours
	 */
	public List<MembresFamilles> getMembresFamilleEnCours() {
		return membresFamilleEnCours;
	}

	/**
	 * @param membresFamilleEnCours
	 *            the membresFamilleEnCours to set
	 */
	public void setMembresFamilleEnCours(
			List<MembresFamilles> membresFamilleEnCours) {
		this.membresFamilleEnCours = membresFamilleEnCours;
	}

	public boolean isValidMembreEnCours() {
		return (membreEnCours != null);
	}

	/**
	 * @return the membresFamillesJpaController
	 */
	public MembresFamillesJpaController getMembresFamillesJpaController() {
		return membresFamillesJpaController;
	}

	/**
	 * @return the serviceRefugeEnCours
	 */
	public List<Servicerefuge> getServicesRefugeEnCours() {
		return servicesRefugeEnCours;
	}

	/**
	 * @param serviceRefugeEnCours
	 *            the serviceRefugeEnCours to set
	 */
	public void setServicesRefugeEnCours(
			List<Servicerefuge> serviceRefugeEnCours) {
		this.servicesRefugeEnCours = serviceRefugeEnCours;
	}

	public void litTableMembres() {
		listeMembres = membresJpaController.findMembresTrieParNom();
	}

	public void effaceMembreEnCours() {
		if (membreEnCours != null) {
			try {
				membresJpaController.destroy(membreEnCours.getCode());
				membreEnCours = null;
				litTableMembres();
			} catch (NonexistentEntityException ex) {
				Logger.getLogger(SessionBean1.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (RollbackFailureException ex) {
				Logger.getLogger(SessionBean1.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (Exception ex) {
				Logger.getLogger(SessionBean1.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * @return the serviceRefugeEnCours
	 */
	public Servicerefuge getServiceRefugeEnCours() {
		return serviceRefugeEnCours;
	}

	/**
	 * @param serviceRefugeEnCours
	 *            the serviceRefugeEnCours to set
	 */
	public void setServiceRefugeEnCours(Servicerefuge serviceRefugeEnCours) {
		this.serviceRefugeEnCours = serviceRefugeEnCours;
	}

	/**
	 * @return the calendrier1
	 */
	public CalendrierUtil getCalendrier1() {
		return calendrier1;
	}

	/**
	 * @param calendrier1
	 *            the calendrier1 to set
	 */
	public void setCalendrier1(CalendrierUtil calendrier1) {
		this.calendrier1 = calendrier1;
	}

	/**
	 * @return the groupeUtilisateursEnCours
	 */
	public GroupesUtilisateurs getGroupeUtilisateursEnCours() {
		return groupeUtilisateursEnCours;
	}

	/**
	 * @param groupeUtilisateursEnCours
	 *            the groupeUtilisateursEnCours to set
	 */
	public void setGroupeUtilisateursEnCours(
			GroupesUtilisateurs groupeUtilisateursEnCours) {
		this.groupeUtilisateursEnCours = groupeUtilisateursEnCours;
	}

	/**
	 * @return the reservationJPAControler
	 */
	public ReservationsJpaController getReservationsJPAControler() {
		return reservationsJPAControler;
	}

	/**
	 * @return the reservationEnCours
	 */
	public Reservations getReservationEnCours() {
		return reservationEnCours;
	}

	/**
	 * @param reservationEnCours
	 *            the reservationEnCours to set
	 */
	public void setReservationEnCours(Reservations reservationEnCours) {
		this.reservationEnCours = reservationEnCours;
	}

	/**
	 * @return the reservationsMembreEnCours
	 */
	public List<Reservations> getReservationsMembreEnCours() {
		return reservationsMembreEnCours;
	}

	/**
	 * @param reservationsMembreEnCours
	 *            the reservationsMembreEnCours to set
	 */
	public void setReservationsMembreEnCours(
			List<Reservations> reservationsMembreEnCours) {
		this.reservationsMembreEnCours = reservationsMembreEnCours;
	}

	public void refreshServicesRefugesMembreEnCours() {
		servicesRefugeEnCours = getServiceRefugeJpaController()
				.getServiceRefuge(membreEnCours);
	}

	public void refreshReservationsMembreEnCours() {
		reservationsMembreEnCours = reservationsJPAControler
				.getReservationsMembre(membreEnCours);
	}

	/**
	 * @return the serviceRefugeJpaController
	 */
	public ServicerefugeJpaController getServiceRefugeJpaController() {
		return serviceRefugeJpaController;
	}

	/**
	 * @param serviceRefugeJpaController
	 *            the serviceRefugeJpaController to set
	 */
	public void setServiceRefugeJpaController(
			ServicerefugeJpaController serviceRefugeJpaController) {
		this.serviceRefugeJpaController = serviceRefugeJpaController;
	}

	public void setRetourVersMembre(Boolean retourVersMembre) {
		this.retourVersMembre = retourVersMembre;
	}

	public Boolean getRetourVersMembre() {
		return retourVersMembre;
	}

	public void setEcrituresJPAController(EcrituresJpaController ecrituresJPAController) {
		this.ecrituresJPAController = ecrituresJPAController;
	}

	public EcrituresJpaController getEcrituresJPAController() {
		return ecrituresJPAController;
	}

	public void setEcritureEnCours(Ecritures ecritureEnCours) {
		this.ecritureEnCours = ecritureEnCours;
	}

	public Ecritures getEcritureEnCours() {
		return ecritureEnCours;
	}

	public void setCodesEcrituresJpaController(
			CodesEcrituresJpaController codesEcrituresJpaController) {
		this.codesEcrituresJpaController = codesEcrituresJpaController;
	}

	public CodesEcrituresJpaController getCodesEcrituresJpaController() {
		return codesEcrituresJpaController;
	}

	public SelectItem[] getCheckBoxAffichage() {
		return checkBoxAffichage;
	}

	public void setSelectedCheckboxAffichage(String[] selectedCheckboxAffichage) {
		this.selectedCheckboxAffichage = selectedCheckboxAffichage;
	}

	public String[] getSelectedCheckboxAffichage() {
		return selectedCheckboxAffichage;
	}

}
