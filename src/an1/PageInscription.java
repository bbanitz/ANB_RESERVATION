package an1;

import javax.faces.context.FacesContext;

import an1.persistence.Membres;

public class PageInscription {
	private Membres nouveauMembre;
	private SessionBean1 sessionBean1;
	public PageInscription() {
		
		sessionBean1 = (SessionBean1) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("sessionBean1");
		nouveauMembre = sessionBean1.getMembreEnCours();
		if (nouveauMembre == null) {
			nouveauMembre = new Membres();
			//return;
		}
			
	}

	public void setNouveauMembre(Membres nouveauMembre) {
		this.nouveauMembre = nouveauMembre;
	}

	public Membres getNouveauMembre() {
		return nouveauMembre;
	}

	public String validation_action() {
		
		sessionBean1.setMembreEnCours(nouveauMembre);
		return "confirmation";
	}

	public String annulation_action() {
		System.out.println("annulation");
		return null;
	}

}
