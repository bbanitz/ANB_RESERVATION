package an1.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the codesecriture database table.
 * 
 */
@Entity
@Table(name="codesecriture")
public class Codesecriture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private String codegroupe;

	private String designation;

    public Codesecriture() {
    }

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodegroupe() {
		return this.codegroupe;
	}

	public void setCodegroupe(String codegroupe) {
		this.codegroupe = codegroupe;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

}