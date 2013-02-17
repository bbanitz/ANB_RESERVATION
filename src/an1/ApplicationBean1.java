package an1;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import an1.persistence.Typesmembre;

public class ApplicationBean1 {
	private List<SelectItem> typesMembres;
	public List<SelectItem> getTypesMembres() {
		return typesMembres;
	}
	public void setTypesMembres(List<SelectItem> typesMembres) {
		this.typesMembres = typesMembres;
	}
	public String getConnected() {
		return "oui";
	}
	public ApplicationBean1() {
    	try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("anbJPA");
			EntityManager em=emf.createEntityManager();
			Query q=em.createQuery("select o from Typesmembre as o");
			@SuppressWarnings("unchecked")
			List<Typesmembre> listeTypesMembres=q.getResultList();
			typesMembres=new ArrayList<SelectItem>();
			for (Typesmembre m:listeTypesMembres) {
				SelectItem item=new SelectItem(m.getCode(),m.getLibelle());
				typesMembres.add(item);
				System.out.println(m.getCode()+": "+m.getLibelle());
			}
			em.close();
			emf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 
		}
	}

}
