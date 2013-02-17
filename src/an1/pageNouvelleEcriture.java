package an1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import an1.exceptions.NonexistentEntityException;
import an1.exceptions.RollbackFailureException;
import an1.persistence.Codesecriture;

public class pageNouvelleEcriture {
	
	private SessionBean1 sessionBean1;
    public pageNouvelleEcriture() {
        try {
        	sessionBean1 = (SessionBean1)FacesContext.getCurrentInstance()
        	.getExternalContext().getSessionMap().get("sessionBean1");
        	
        } catch (Exception e) {

            throw e instanceof FacesException ? (FacesException) e : new FacesException(e);
        } 
    	
    }
    
	public List<SelectItem> getListeCodes() {
		List<Codesecriture> liste=sessionBean1.getCodesEcrituresJpaController().findEcrituresEntities();
		List<SelectItem>  l = new ArrayList<SelectItem>();
		Iterator<Codesecriture> it=liste.iterator();
		while (it.hasNext()) {
			Codesecriture cod=it.next();
			SelectItem si= new SelectItem(cod.getCode(),cod.getCode()+" : "+cod.getDesignation()); 
			l.add(si);
		}
		return l;
	}
	
	public String btValidation_action() {
		try {
			sessionBean1.getEcrituresJPAController().edit(sessionBean1.getEcritureEnCours());
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
		return "listemembres";
	}
	
	public String btAnnulation_action() {
		return "listemembres";
	}
    
	public void valueChangeEvent(ValueChangeEvent ev) {
		String Libelle=sessionBean1.getCodesEcrituresJpaController().findCodeEcritures(ev.getNewValue().toString()).getDesignation();
		System.out.println(Libelle);
		
		sessionBean1.getEcritureEnCours().setLibelle(Libelle);
	}
}
