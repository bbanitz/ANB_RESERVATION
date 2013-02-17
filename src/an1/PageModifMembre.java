/*
 * PageModifMembre.java
 *
 * Created on 27 janv. 2009, 09:21:15
 * Copyright bbernard
 */
package an1;

import an1.persistence.MembresFamilles;

import com.icesoft.faces.component.ext.RowSelectorEvent;
//import com.icesoft.faces.component.jsfcl.data.DefaultTableDataModel;
//import com.icesoft.faces.component.jsfcl.data.SelectInputDateBean;
//import com.sun.rave.faces.converter.SqlDateConverter;
//import com.sun.rave.web.ui.appbase.AbstractPageBean;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.IntegerConverter;


/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */
public class PageModifMembre {
    @SuppressWarnings("rawtypes")
	private ArrayList listeType;

    private SessionBean1 sessionBean1;
    private IntegerConverter integerConverter1 = new IntegerConverter();

    public IntegerConverter getIntegerConverter1() {
        return integerConverter1;
    }

    public void setIntegerConverter1(IntegerConverter ic) {
        this.integerConverter1 = ic;
    }
     private DateTimeConverter sqlDateConverter1 = new DateTimeConverter();

    public DateTimeConverter getSqlDateConverter1() {
        return sqlDateConverter1;
    }

    public void setSqlDateConverter1(DateTimeConverter sdc) {
        this.sqlDateConverter1 = sdc;
    }

    // </editor-fold>


    @SuppressWarnings({ "unchecked", "rawtypes" })
	public PageModifMembre() {


        // </editor-fold>
        // Perform application initialization that must complete
        // *after* managed components are initialized
        // TODO - add your own initialization code here
    	sessionBean1 = (SessionBean1)FacesContext.getCurrentInstance()
    	.getExternalContext().getSessionMap().get("sessionBean1");

        listeType=new ArrayList();
        listeType.add("conjoint");
        listeType.add("enfant");
        sqlDateConverter1.setDateStyle("long");
        sqlDateConverter1.setPattern("dd/mm/yyyy");

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

    private String retour() {
    	if (sessionBean1.getRetourVersMembre()) return "retourMembre"; else return "retourListeMembres";
    }
    
    public String btValidation_action() {
        //return null means stay on the same page
        getSessionBean1().editMembreEnCours();
        getSessionBean1().litTableMembres();
        return retour();
    }

      public String btEffaceMembreFamille_action() {
        //return null means stay on the same page
        //System.out.println("delete");
        List<MembresFamilles> mf=getSessionBean1().getMembreEnCours().getMembresFamille();
        for (int i=0;i<mf.size();i++) {
        	if (mf.get(i).isSelected()) {
        		
        	  mf.remove(i);	
        	}
        }
        return null;
    }

        public void rowSelector1_processAction(RowSelectorEvent rse) {
        //int selectedRowIndex = rse.getRow();
        //getSessionBean1().getMembreEnCours().getMembresFamille().remove(selectedRowIndex);
        System.out.println(getSessionBean1().getMembreEnCours().getNom());
    }


    public String btNouveauMembreFamille_action() {
        //return null means stay on the same page
        getSessionBean1().nouveauMembreFamille();
        return null;
    }

    /**
     * @return the listeType
     */
    @SuppressWarnings("rawtypes")
	public ArrayList getListeType() {
        return listeType;
    }

    /**
     * @param listeType the listeType to set
     */
    public void setListeType(@SuppressWarnings("rawtypes") ArrayList listeType) {
        this.listeType = listeType;
    }
    
    public String btAnnulation_action() {
        //return null means stay on the same page
        getSessionBean1().litTableMembres();
        //getSessionBean1().setMembreEnCours(null);
        return retour();
    }

}

