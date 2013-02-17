/*
 * PageServiceRefuge.java
 *
 * Created on 4 juil. 2009, 18:58:17
 * Copyright bbernard
 */
package an1;

import an1.exceptions.PreexistingEntityException;
import an1.exceptions.RollbackFailureException;
import com.icesoft.faces.component.selectinputdate.SelectInputDate;
//import com.sun.rave.faces.converter.SqlDateConverter;
//import com.sun.rave.web.ui.appbase.AbstractPageBean;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.convert.DateTimeConverter;



/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */
public class PageServiceRefuge {

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private DateTimeConverter sqlDateConverter1 = new DateTimeConverter();
    private SessionBean1 sessionBean1;

    public DateTimeConverter getSqlDateConverter1() {
        return sqlDateConverter1;
    }

    public void setSqlDateConverter1(DateTimeConverter sdc) {
        this.sqlDateConverter1 = sdc;
    }
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

    // </editor-fold>

    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public PageServiceRefuge() {
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

    public void dateDebut_processValueChange(ValueChangeEvent vce) {
        dateFin.setValue(dateDebut.getValue());
    }

    public String btValidation_action() {
        try {
            //return null means stay on the same page
            getSessionBean1().getServiceRefugeJpaController().edit(getSessionBean1().getServiceRefugeEnCours());
            getSessionBean1().refreshServicesRefugesMembreEnCours();
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(PageServiceRefuge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PageServiceRefuge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PageServiceRefuge.class.getName()).log(Level.SEVERE, null, ex);
        }
          return "retour";
    }

   
    public String btAnnulation_action() {
        //return null means stay on the same page
    	
        return "retour";
    }
    
 /*   public void validationDateDebut(FacesContext context, UIComponent component,
    		Object value) throws ValidatorException {
        System.out.println("validation");
    	if (value==null)
    		throw new ValidatorException(new FacesMessage(
    				FacesMessage.SEVERITY_ERROR, "Date de d�but obligatoire",
    				"Date de d�but obligatoire"));
    }*/

}

