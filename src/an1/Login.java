/*
 * Login.java
 *
 * Created on 13 janv. 2009, 08:46:40
 * Copyright bbernard
 */
package an1;

import an1.persistence.Membres;
import an1.persistence.GroupesUtilisateurs;
import an1.persistence.GroupesUtilisateurs.typeAcces;
import com.icesoft.faces.component.ext.HtmlInputSecret;
import com.icesoft.faces.component.ext.HtmlInputText;
//import com.icesoft.faces.component.jsfcl.data.PopupBean;
//import com.sun.rave.web.ui.appbase.AbstractPageBean;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpSession;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */
public class Login {
    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */

	public String nouvelleInscription_Action(){
		return "nouvelleInscription";
	}
     private void _init() throws Exception {
    }
    private HtmlInputText itUtilisateur = new HtmlInputText();

    public HtmlInputText getItUtilisateur() {
        return itUtilisateur;
    }

    public void setItUtilisateur(HtmlInputText hit) {
        this.itUtilisateur = hit;
    }
    private HtmlInputSecret isMotDePasse = new HtmlInputSecret();

    public HtmlInputSecret getIsMotDePasse() {
        return isMotDePasse;
    }

    public void setIsMotDePasse(HtmlInputSecret his) {
        this.isMotDePasse = his;
    }
    private SessionBean1 sessionBean1;
    private boolean PopUpVisible;
    
   
    public boolean isPopUpVisible() {
		return PopUpVisible;
	}

	public void setPopUpVisible(boolean popUpVisible) {
		PopUpVisible = popUpVisible;
	}

	/**
     * <p>Callback method that is called whenever a page is navigated to,
     * either directly via a URL, or indirectly via page navigation.
     * Customize this method to acquire resources that will be needed
     * for event handlers and lifecycle methods, whether or not this
     * page is performing post back processing.</p>
     * 
     * <p>Note that, if the current request is a postback, the property
     * values of the components do <strong>not</strong> represent any
     * values submitted with this request.  Instead, they represent the
     * property values that were saved for this view when it was rendered.</p>
     */
    //@Override
    public Login() {
        //FacesContext.getCurrentInstance();
		// Perform initializations inherited from our superclass

        try {
            _init();
        } catch (Exception e) {
 //           log("Page1 Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e: new FacesException(e);
        }
        
    }

 
    protected SessionBean1 getSessionBean1() {
    	if (sessionBean1==null) {
        	FacesContext context=FacesContext.getCurrentInstance();
        	ExternalContext exC=context.getExternalContext();
        	sessionBean1 = (SessionBean1)exC.getSessionMap().get("sessionBean1");
//        	FacesContext context = event.getFacesContext();
//        	HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
//        	sessionBean1 = (SessionBean1) session.getAttribute("sessionBean1");
        	     
    	}
        return sessionBean1;
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */


    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */

    public String button3_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        return "case2";
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */

    public String btValidation_action() {
        //return null means stay on the same page
        Membres findMembre = getSessionBean1().getMembresJpaController().findMembre(itUtilisateur.getValue().toString(), isMotDePasse.getValue().toString());
        if (findMembre!=null) {
        	getSessionBean1().setMembreLoggé(findMembre);
//            DÃ©finit les autorisations courantes
        	
            GroupesUtilisateurs courant=getSessionBean1().getGroupesUtilisateursJpaController().findGroupesUtilisateurs(findMembre.getGroupeUtilisateur());
            getSessionBean1().setGroupeUtilisateursEnCours(courant);
            if (courant.getListeMembres()!=typeAcces.aucunAcces) return "listeMembres";
            if (courant.getFicheMembre()!=typeAcces.aucunAcces) {
                getSessionBean1().setMembreEnCours(findMembre);
                return "pageMembre";
            }
        }
        setPopUpVisible(true);
        return null;
    }

    public String btFermerPopup_action() {
        //return null means stay on the same page
         setPopUpVisible(false);
        return null;
    }
}

