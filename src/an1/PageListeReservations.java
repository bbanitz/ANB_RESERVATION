/*
 * PageListeReservations.java
 *
 * Created on 27 juil. 2009, 10:07:29
 * Copyright bbernard
 */
package an1;

import an1.exceptions.NonexistentEntityException;
import an1.persistence.Reservations;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */
public class PageListeReservations {

    private String[] selectedCheckbox1={"Demande"};
    private String selectedRadiobox1;
    private List<Reservations> listeReservationsEnCours;
    private SessionBean1 sessionBean1;

    /**
     * @return the CheckBox1
     */
    public PageListeReservations() {
    	sessionBean1 = (SessionBean1)FacesContext.getCurrentInstance()
    	.getExternalContext().getSessionMap().get("sessionBean1");
    	listeReservationsEnCours = getSessionBean1().getReservationsJPAControler().LitReservationsStatut(selectedCheckbox1);
    }
    public SelectItem[] getCheckBox1() {
        return checkBox1;
    }
 
    private static final SelectItem[] checkBox1 = new SelectItem[]{
        new SelectItem(Reservations.StatutReservation.Demande),
        new SelectItem(Reservations.StatutReservation.Option),
        new SelectItem(Reservations.StatutReservation.Ferme),
        new SelectItem(Reservations.StatutReservation.AcomptePayé),
        new SelectItem(Reservations.StatutReservation.Payé)};

     /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected SessionBean1 getSessionBean1() {
        return sessionBean1;
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
     /**
     * @return the selectedChecbox1
     */
    public String[] getSelectedCheckbox1() {
        return selectedCheckbox1;
    }

    /**
     * @param selectedChecbox1 the selectedChecbox1 to set
     */
    public void setSelectedCheckbox1(String[] selectedChecbox1) {
        this.selectedCheckbox1 = selectedChecbox1;
    }

    public void selectManyCheckbox1_processValueChange(ValueChangeEvent vce) {
        listeReservationsEnCours = getSessionBean1().getReservationsJPAControler().LitReservationsStatut((String[]) vce.getNewValue());

    }

    /**
     * @return the listeReservationsEnCours
     */
    public List<Reservations> getListeReservationsEnCours() {
        return listeReservationsEnCours;
    }

    /**
     * @param listeReservationsEnCours the listeReservationsEnCours to set
     */
    public void setListeReservationsEnCours(List<Reservations> listeReservationsEnCours) {
        this.setListeReservationsEnCours(listeReservationsEnCours);
    }

    /**
     * @return the selectedRadiobox1
     */
    public String getSelectedRadiobox1() {
        return selectedRadiobox1;
    }

    /**
     * @param selectedRadiobox1 the selectedRadiobox1 to set
     */
    public void setSelectedRadiobox1(String selectedRadiobox1) {
        this.selectedRadiobox1 = selectedRadiobox1;
    }

    public String listeMembres_action() {
        //return null means stay on the same page
        return "listeMembres";
    }

    public String menuBar1_action() {
        //return null means stay on the same page
        return null;
    }

    public String menuDeconnection_action() {
        //return null means stay on the same page
        return "login";
    }

    public String menuCalendrier_action() {
        //return null means stay on the same page
        return "calendrier";
    }



    public String btModifierStatuts_action() {
        //return null means stay on the same page
        List<Reservations> res = listeReservationsEnCours;
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).isSelected()) {
                try {
                    Reservations r = res.get(i);
                    //System.out.println(selectedRadiobox1);
                    String sel =selectedRadiobox1.toString();
                    //System.out.println(sel);
                    r.setStatut(sel);
                    r.setModificationLe(new Date());
                    r.setModificationPar(getSessionBean1().getMembreLoggé());
                    getSessionBean1().getReservationsJPAControler().edit(r);

                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
                } catch (an1.exceptions.RollbackFailureException ex) {
                    Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
}

