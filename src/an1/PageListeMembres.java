/*
 * PageListeMembres.java
 *
 * Created on 15 janv. 2009, 11:21:14
 * Copyright bbernard
 */
package an1;

import an1.exceptions.PreexistingEntityException;
import an1.exceptions.RollbackFailureException;
import an1.persistence.Ecritures;
import an1.persistence.Membres;
import an1.persistence.Reservations;
import an1.persistence.Servicerefuge;
import an1.exceptions.NonexistentEntityException;
import com.icesoft.faces.async.render.OnDemandRenderer;
import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;
import com.icesoft.faces.component.dragdrop.DragEvent;
import com.icesoft.faces.component.ext.HtmlCommandButton;
import com.icesoft.faces.component.ext.RowSelectorEvent;
//import com.icesoft.faces.component.jsfcl.data.BorderLayoutBean;
//import com.icesoft.faces.component.jsfcl.data.DefaultTableDataModel;
//import com.icesoft.faces.component.jsfcl.data.MenuBarBean;
//import com.icesoft.faces.component.jsfcl.data.PopupBean;
import com.icesoft.faces.component.paneltabset.PanelTabSet;
import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;
//import com.sun.data.provider.impl.CachedRowSetDataProvider;
//import com.sun.rave.faces.converter.SqlDateConverter;
//import com.sun.rave.faces.data.CachedRowSetDataModel;
//import com.sun.rave.web.ui.appbase.AbstractPageBean;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */
public class PageListeMembres implements Renderable {

    /**
     * @return the CheckBox1
     */

    private PersistentFacesState state = null;
    private RenderManager renderManager;
    private OnDemandRenderer personGroup = null;
    private boolean showPopupEffacement = false;
    private String recherche;

    private enum Effacement {

        reservation, serviceRefuge, membre
    };
    private Effacement effacement;

    public void setRenderManager(RenderManager renderManager) {
        if (renderManager != null) {
            this.renderManager = renderManager;
            personGroup = renderManager.getOnDemandRenderer("personGroup");
            personGroup.add(this);
        }
    }

    public RenderManager getRenderManager() {
        return renderManager;
    }

    public PersistentFacesState getState() {
        return state;
    }

    public void renderingException(RenderingException arg0) {
        personGroup.remove(this);
    }

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */

    private DateTimeConverter sqlDateConverter1 = new DateTimeConverter();

    public DateTimeConverter getSqlDateConverter1() {
        return sqlDateConverter1;
    }

    public void setSqlDateConverter1(DateTimeConverter sdc) {
        this.sqlDateConverter1 = sdc;
    }
    private PanelTabSet panelTabSetMembre = new PanelTabSet();

    public PanelTabSet getPanelTabSetMembre() {
        return panelTabSetMembre;
    }

    public void setPanelTabSetMembre(PanelTabSet pts) {
        this.panelTabSetMembre = pts;
    }
    private HtmlCommandButton btEffaceMembre = new HtmlCommandButton();

    public HtmlCommandButton getBtEffaceMembre() {
        return btEffaceMembre;
    }

    public void setBtEffaceMembre(HtmlCommandButton hcb) {
        this.btEffaceMembre = hcb;
    }
    private HtmlCommandButton btNouveauMembre = new HtmlCommandButton();

    public HtmlCommandButton getBtNouveauMembre() {
        return btNouveauMembre;
    }

    public void setBtNouveauMembre(HtmlCommandButton hcb) {
        this.btNouveauMembre = hcb;
    }
    private HtmlCommandButton btEdit = new HtmlCommandButton();

    public HtmlCommandButton getBtEdit() {
        return btEdit;
    }

    public void setBtEdit(HtmlCommandButton hcb) {
        this.btEdit = hcb;
    }
    private DateTimeConverter sqlDateConverter2 = new DateTimeConverter();

    public DateTimeConverter getSqlDateConverter2() {
        return sqlDateConverter2;
    }

    public void setSqlDateConverter2(DateTimeConverter sdc) {
        this.sqlDateConverter2 = sdc;
    }

    // </editor-fold>
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    private SessionBean1 sessionBean1;
    
    public PageListeMembres() {
    	FacesContext context=FacesContext.getCurrentInstance();
    	ExternalContext exC=context.getExternalContext();
    	sessionBean1 = (SessionBean1)exC.getSessionMap().get("sessionBean1");
    }



 //   public void prerender() {
 //       panelTabSetMembre.setRendered(getSessionBean1().isValidMembreEnCours());
 //       btEffaceMembre.setRendered(getSessionBean1().isValidMembreEnCours());
 //       btEdit.setRendered(getSessionBean1().isValidMembreEnCours());
 //   }

 
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

    public String dataTable1_firstPageAction() {
        {
            return null;
        }
    }

    public String dataTable1_previousPageAction() {
        {
            return null;
        }
    }

    public String dataTable1_nextPageAction() {
        {
            return null;
        }
    }

    public String dataTable1_lastPageAction() {
        {
            return null;
        }
    }

    /*selection listener is used when a row is selected on the data table component
     *@param e RowSelectorEvent */
    public void rowSelector1_processAction(RowSelectorEvent rse) {
        int selectedRowIndex = rse.getRow();
        getSessionBean1().setMembreEnCours(getSessionBean1().getListeMembres().get(selectedRowIndex));
    //System.out.println(getSessionBean1().getMembreEnCours().getNom());
    }

    public void panelTabSetMembre_processTabChange(TabChangeEvent tce) {
        System.out.println("changement tab");
    }

    public boolean isAdresse2NonNull() {
        Membres m = getSessionBean1().getMembreEnCours();
        if (m == null) {
            return false;
        } else {
            return (m.getAdresse2() != null);
        }
    }

    public String btEdit_action() {
        //return null means stay on the same page
    	sessionBean1.setRetourVersMembre(false);
        return "edit";
    }

    public void panelGroup2_processMyEvent(DragEvent de) {
    }

    /*selection listener is used when a row is selected on the data table component
     *@param e RowSelectorEvent */
    public void rowSelector2_processAction(RowSelectorEvent rse) {
        int selectedRowIndex = rse.getRow();
        getSessionBean1().setServiceRefugeEnCours(getSessionBean1().getServicesRefugeEnCours().get(selectedRowIndex));
        for (int i = 0; i < getSessionBean1().getServicesRefugeEnCours().size(); i++) {
            if (getSessionBean1().getServicesRefugeEnCours().get(i).isSelected()) {
                System.out.println(getSessionBean1().getServicesRefugeEnCours().get(i).getMembre().getNom() + " " + getSessionBean1().getServicesRefugeEnCours().get(i).getDateDebut() + " " + getSessionBean1().getServicesRefugeEnCours().get(i).getDateFin());
            }
        }
    }

    public String btNouveauMembre_action() {
        //return null means stay on the same page
        Membres membre = new Membres();
        try {
            getSessionBean1().getMembresJpaController().create(membre);
            membre.setTypeMembre("HOTE");
            getSessionBean1().setMembreEnCours(membre);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "edit";
    }

    public String btEffaceMembre_action() {
        effacement = Effacement.membre;
        setShowPopupEffacement(true);
        return null;
    }

    public String btEffaceServiceRefuge_action() {
        //return null means stay on the same page
        effacement = Effacement.serviceRefuge;
        setShowPopupEffacement(true);
        return null;
    }

    public String btEditServiceRefuge_action() {
        //return null means stay on the same page
    	if (sessionBean1.getServiceRefugeEnCours()==null) return null;
        return "serviceRefuge";
    }

    public String btNouveauServiceRefuge_action() {
        //return null means stay on the same page
        Servicerefuge serv = new Servicerefuge();
        serv.setMembre(getSessionBean1().getMembreEnCours());
        serv.setNombrePersonnes(1);
        getSessionBean1().setServiceRefugeEnCours(serv);
        return "serviceRefuge";
    }

    public String btEffaceReservation_action() {
        effacement = Effacement.reservation;
        setShowPopupEffacement(true);
        return null;
    }

    public void effaceServiceRefuge() {
        List<Servicerefuge> res = getSessionBean1().getServicesRefugeEnCours();
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).isSelected()) {
                try {
                    getSessionBean1().getServiceRefugeJpaController().destroy(res.get(i).getId());
                    res.remove(i);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
                } catch (an1.exceptions.RollbackFailureException ex) {
                    Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return;

    }

    public void effaceReservation() {
        
    	List<Reservations> res = getSessionBean1().getReservationsMembreEnCours();
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).isSelected()) {
                try {
                    getSessionBean1().getReservationsJPAControler().destroy(res.get(i).getId());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
                } catch (an1.exceptions.RollbackFailureException ex) {
                    Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(PageListeMembres.class.getName()).log(Level.SEVERE, null, ex);
                }
                res.remove(i);
            }
        }
        return;

    }

    public String btNouvelleReservation_action() {
        
        Reservations res = new Reservations();
        res.setCreate(true);
        res.setStatut("Demande");
        getSessionBean1().setReservationEnCours(res);
        getSessionBean1().setRetourVersMembre(false); 
        return "modifReservation";

    }

 
    /**
     * @return the showPopupEffacement
     */
    public boolean isShowPopupEffacement() {
        return showPopupEffacement;
    }

    /**
     * @param showPopupEffacement the showPopupEffacement to set
     */
    public void setShowPopupEffacement(boolean showPopupEffacement) {
        this.showPopupEffacement = showPopupEffacement;
    }

    public String btEffacementOui_action() {
        //return null means stay on the same page
        switch (effacement) {
            case reservation: {
                effaceReservation();
                break;
            }
            case serviceRefuge: {
                effaceServiceRefuge();
                break;
            }
            case membre: {
                getSessionBean1().effaceMembreEnCours();
                break;
            }
        }
        showPopupEffacement = false;
        return null;
    }

    public String btEffacementNon_action() {
        //return null means stay on the same page
        showPopupEffacement = false;
        return null;
    }

    public String btEditReservation_action() {
        //return null means stay on the same page
    	boolean oneSelected=false;
        List<Reservations> res = getSessionBean1().getReservationsMembreEnCours();
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).isSelected()) {
                getSessionBean1().setReservationEnCours(res.get(i));
                oneSelected=true;
                break;
            }
        }
        if (!oneSelected) return null;
        getSessionBean1().setRetourVersMembre(false);
        return "modifReservation";
    }


    public String menuDeconnection_action() {
        //return null means stay on the same page
        return "login";
    }
    public void rechercheChangeAction(ValueChangeEvent v)
    {
      String nom=v.getNewValue().toString();	
      sessionBean1.LitMembresParNom(nom);	
    }
    
    public void lanceRecherche(ActionEvent event) {
    	sessionBean1.LitMembresParNom(recherche);
    }
    public String menuCalendrier_action() {
        //return null means stay on the same page
        return "calendrier";
    }

    public String listeReservations_action() {
        //return null means stay on the same page
        return "listeReservations";
    }

    public String menuBar1_action() {
        //return null means stay on the same page
        return null;
    }

	public void setRecherche(String recherche) {
		this.recherche = recherche;
	}

	public String getRecherche() {
		return recherche;
	}
	
	public List<Ecritures> getEcrituresMembreEnCours() {
		return getSessionBean1().getEcrituresJPAController().getEcritures(getSessionBean1().getMembreEnCours());
		
	}
	
	public String getSoldeMembreEnCours(){
		float solde=getSessionBean1().getEcrituresJPAController().getSoldeEcritures(getSessionBean1().getMembreEnCours());
		return Float.toString(solde);
	}
	
	public String nouvelleEcriture_action() {
		Ecritures nouvelleEcriture=new Ecritures();
		nouvelleEcriture.setDate(new Date());
		nouvelleEcriture.setMembre(getSessionBean1().getMembreEnCours());
		nouvelleEcriture.setLibelle("test");
		getSessionBean1().setEcritureEnCours(nouvelleEcriture);
		return "ecriture";
	}
	public String editEcriture_action() {
		return "ecriture";
	}
	
	public String deleteEcriture_action() {
		return null;
	}
}


