/*
 * pageReservation.java
 *
 * Created on 8 juin 2009, 09:00:23
 * Copyright bbernard
 */
package an1;

import an1.exceptions.PreexistingEntityException;
import an1.exceptions.RollbackFailureException;
import an1.persistence.Reservations;
//import com.icesoft.faces.component.jsfcl.data.SelectInputDateBean;
//import com.icesoft.faces.component.selectinputdate.SelectInputDate;
//import com.sun.rave.faces.converter.SqlDateConverter;
//import com.sun.rave.web.ui.appbase.AbstractPageBean;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.IntegerConverter;
import javax.faces.event.ValueChangeEvent;

import com.icesoft.faces.component.selectinputdate.SelectInputDate;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */
public class pageReservation {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

 
 
    private DateTimeConverter sqlDateConverter1 = new DateTimeConverter();
    private SessionBean1 sessionBean1;
    private SelectInputDate dateFin = new SelectInputDate();

    public SelectInputDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(SelectInputDate sid) {
        this.dateFin = sid;
    }
    private SelectInputDate dateDebut = new SelectInputDate();

    public SelectInputDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(SelectInputDate sid) {
        this.dateDebut = sid;
    }


    public DateTimeConverter getSqlDateConverter1() {
        return sqlDateConverter1;
    }

    public void setSqlDateConverter1(DateTimeConverter sdc) {
        this.sqlDateConverter1 = sdc;
    }
    private IntegerConverter integerConverter1 = new IntegerConverter();

    public IntegerConverter getIntegerConverter1() {
        return integerConverter1;
    }

    public void setIntegerConverter1(IntegerConverter ic) {
        this.integerConverter1 = ic;
    }

    // </editor-fold>
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public pageReservation() {
    	sessionBean1 = (SessionBean1)FacesContext.getCurrentInstance()
    	.getExternalContext().getSessionMap().get("sessionBean1");

    }
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

    public String btValidation_action() {
        //return null means stay on the same page
        try {
        	Reservations reserv=getSessionBean1().getReservationEnCours();
            Date now=new Date();
            reserv.setDateCreation(now);
            reserv.setModificationLe(now);
            reserv.setModificationPar(getSessionBean1().getMembreLoggé());
             if (reserv.isCreate()) {
                reserv.setMembre(getSessionBean1().getMembreEnCours());
                getSessionBean1().getReservationsJPAControler().create(reserv);
            } else {
                getSessionBean1().getReservationsJPAControler().edit(reserv);
            }
            getSessionBean1().refreshReservationsMembreEnCours();
       } catch (PreexistingEntityException ex) {
            Logger.getLogger(pageReservation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(pageReservation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(pageReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour();
    }

    private String retour() {
    	if (sessionBean1.getRetourVersMembre()) return "retourMembre"; else return "retourListeMembres";
    }
    
    public String btAnnulation_action() {
        //return null means stay on the same page
        return retour();
    }

   
    public void sdDateDebut_processValueChange(ValueChangeEvent vce) {
        dateFin.setValue(dateDebut.getValue());
    }
}

