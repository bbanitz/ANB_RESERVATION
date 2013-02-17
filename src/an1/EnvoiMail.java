package an1;

import javax.mail.internet.*;
import javax.mail.*;
import java.util.*;

/**
 * Classe permettant d'envoyer un mail.
 */
public class EnvoiMail {
	private final static String MAILER_VERSION = "Java";

	public boolean envoyerMailSMTP(String Destinataire,String LeMessage, boolean debug) {
		boolean result = false;
		String serveur="smtp.banitz.fr";
		String adresseRetour="danielle.noth@banitz.fr";
		try {
			Properties prop = System.getProperties();
			prop.put("mail.smtp.host", serveur);
			Session session = Session.getDefaultInstance(prop, null);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(adresseRetour));
			InternetAddress[] internetAddresses = new InternetAddress[1];
			internetAddresses[0] = new InternetAddress(Destinataire);
			message.setRecipients(Message.RecipientType.TO, internetAddresses);
			message.setSubject("Test");
			//message.setText(LeMessage);
			message.setHeader("X-Mailer", MAILER_VERSION);
			message.setSentDate(new Date());
	        message.setContent(LeMessage, "text/html");
			session.setDebug(debug);
			Transport.send(message);
			result = true;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
