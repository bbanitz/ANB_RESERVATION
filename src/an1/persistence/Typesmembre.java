package an1.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the typesmembres database table.
 * 
 */
@Entity
@Table(name="typesmembres")
public class Typesmembre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TYPESMEMBRES_CODE_GENERATOR", sequenceName="SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TYPESMEMBRES_CODE_GENERATOR")
	private String code;

	private String libelle;

    public Typesmembre() {
    }

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}