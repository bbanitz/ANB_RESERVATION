/*
 * Calendrier.java
 *
 * Created on 14 mai 2009, 09:25:48
 * Copyright bbernard
 */
package an1;

//import an1.CalendrierUtil.UnJour;
//import an1.persistence.Servicerefuge;
//import com.sun.rave.faces.converter.SqlDateConverter;
//import com.sun.rave.web.ui.appbase.AbstractPageBean;
import java.util.Date;
//import java.util.List;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;


import an1.CalendrierUtil.UnJour;

//import org.apache.commons.beanutils.locale.converters.SqlDateLocaleConverter;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */
public class Calendrier {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    



    // </editor-fold>
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    private String valeurMois="";
    private String valeurJour="";
    private SessionBean1 sessionBean1;

    private Date dateCourante;
    public Calendrier() {
 
        try {
        	sessionBean1 = (SessionBean1)FacesContext.getCurrentInstance()
        	.getExternalContext().getSessionMap().get("sessionBean1");
        } catch (Exception e) {
//            log("Calendrier Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e : new FacesException(e);
        }

    // </editor-fold>
    // Perform application initialization that must complete
    // *after* managed components are initialized
    // TODO - add your own initialization code here
    }

    protected SessionBean1 getSessionBean1() {
        return sessionBean1;
    }

 
    public String btMoisSuivant_action() {
        //return null means stay on the same page
        getSessionBean1().getCalendrier1().moisSuivant();
        return null;
    }


    public String btMoisPrecedant_action() {
        //return null means stay on the same page
        getSessionBean1().getCalendrier1().moisPrecedant();
        return null;
    }

    /**
     * @return the dateCourante
     */
    public Date getDateCourante() {
        return dateCourante;
    }

    /**
     * @param dateCourante the dateCourante to set
     */
    public void setDateCourante(Date dateCourante) {
        this.dateCourante = dateCourante;
    }

    public String getHtml() {
    	if (valeurJour!="" && valeurMois!=""){
    		UnJour jourCourant=getSessionBean1().getCalendrier1().getListeMois().get(Integer.parseInt(valeurMois)).getListeJours().get(Integer.parseInt(valeurJour)-1);
    		getSessionBean1().getCalendrier1().setReservationsEnCours(jourCourant.getReservations(sessionBean1.getSelectedCheckboxAffichage()));
    		getSessionBean1().getCalendrier1().setServiceRefugeEnCours(jourCourant.getServiceRefuge());
    	}
    	
        return getSessionBean1().getCalendrier1().getHTML(sessionBean1.getSelectedCheckboxAffichage());
    }


    /**
     * @return the valeurCalendrier
     */
    public String getValeurMois() {
        return valeurMois;
    }

    /**
     * @param valeurCalendrier the valeurCalendrier to set
     */
    public void setValeurMois(String valeurMois) {
        this.valeurMois = valeurMois;
    }

    /**
     * @return the valeurJour
     */
    public String getValeurJour() {
        return valeurJour;
    }

    /**
     * @param valeurJour the valeurJour to set
     */
    public void setValeurJour(String valeurJour) {
        this.valeurJour = valeurJour;
    }
     private DateTimeConverter sqlDateConverter = new DateTimeConverter();

    public DateTimeConverter getSqlDateConverter() {
        return sqlDateConverter;
    }

    public void setSqlDateConverter(DateTimeConverter sdc) {
        this.sqlDateConverter = sdc;
    }

    public String menuDeconnection_action() {
        //return null means stay on the same page
        return "login";
    }

    public String menuListeMembres_action() {
        //return null means stay on the same page
        return "listemembres";
    }

    public String menuBar1_action() {
        //return null means stay on the same page
        return null;
    }

    public String menuItem3_action() {
        //return null means stay on the same page
        return null;
    }
}