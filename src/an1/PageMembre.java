package an1;

import java.util.List;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import an1.persistence.Membres;
import an1.persistence.Reservations;

public class PageMembre {
	private SessionBean1 sessionBean1;
	  public boolean isAdresse2NonNull() {
	        Membres m = sessionBean1.getMembreEnCours();
	        if (m == null) {
	            return false;
	        } else {
	            return (m.getAdresse2() != null);
	        }
	    }

    public PageMembre() {
    	 
        try {
        	sessionBean1 = (SessionBean1)FacesContext.getCurrentInstance()
        	.getExternalContext().getSessionMap().get("sessionBean1");
        	
        } catch (Exception e) {
//            log("Calendrier Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e : new FacesException(e);
        } 
    }
	public String deconnectionAction() {
		return "login";
	}
	public String calendrierAction() {
		return "calendrier";
	}
	public String modifMembreAction(){
		sessionBean1.setRetourVersMembre(true);
		return "modifMembre";
	}
	
	public String NouvelleReservationAction() {
		sessionBean1.setRetourVersMembre(true);
        Reservations res = new Reservations();
        res.setCreate(true);
        res.setStatut("Demande");
        sessionBean1.setReservationEnCours(res);
        return "reservation";
	}
	
    public String modifReservationAction() {
    	sessionBean1.setRetourVersMembre(true);
    	
        List<Reservations> res = sessionBean1.getReservationsMembreEnCours();
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).isSelected()) {
            	res.get(i).setCreate(false);
                sessionBean1.setReservationEnCours(res.get(i));
                break;
            }
            
        }
        return "reservation"; 
    }
}
