package an1;

import an1.persistence.Membres;
import javax.faces.context.FacesContext;

public class PageConfirmation {
	private Membres nouveauMembre;
	private SessionBean1 sessionBean1;
	private StringBuffer PageHTML = null;
	public PageConfirmation() {
		
		sessionBean1 = (SessionBean1) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("sessionBean1");
		nouveauMembre = sessionBean1.getMembreEnCours();
		if (nouveauMembre == null) {
			
			return;
		}
			
	}	
	
	public String Validation_action() 
	{
		double codeAcces = Math.random();
		String codeAccesStr=Double.toString(codeAcces);
		PageHTML=new StringBuffer();
		PageHTML.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		PageHTML.append("<html>");
		PageHTML.append("<head>");
		PageHTML.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
		PageHTML.append("<title>Confirmation Email</title>");
		PageHTML.append("</head>");
		PageHTML.append("<body>");		
		PageHTML.append("Cher "+nouveauMembre.getPrenom()+"<br/>");
		PageHTML.append("Nous avons bien enregistré votre inscription pour la confirmer veuillez cliquer sur le lien suivant :");
		PageHTML.append("<a href=\"http://localhost:8080/essai2/ConfirmationEmail?valeur="+codeAccesStr+">Lien</a>" );
		//PageHTML.append("<input name=\"valeur\" id=\"idValeur\" value=\"test\">Message :</input>");
		//PageHTML.append("<button value=\"ok\" type=\"submit\">Ok</button>");
		//PageHTML.append("</form>");
		PageHTML.append("</body>");
		PageHTML.append("</html>");
		EnvoiMail mail = new EnvoiMail(); 
 		mail.envoyerMailSMTP("bernard@banitz.fr", PageHTML.toString(), true);
        return null;
		
	}
	
}