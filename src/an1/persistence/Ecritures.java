package an1.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the ecritures database table.
 * 
 */
@Entity
@Table(name="ecritures")
public class Ecritures implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer numero;

	private String code;

	private String codeBanque;

	private float credit;

    @Temporal( TemporalType.TIMESTAMP)
	private Date date;

	private float debit;

	private String detailReglement;

	private String libelle;

	@ManyToOne()
    @JoinColumn(name = "membre")
    private Membres membre;
	
	private String reglePar;

    public Ecritures() {
    }

	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeBanque() {
		return this.codeBanque;
	}

	public void setCodeBanque(String codeBanque) {
		this.codeBanque = codeBanque;
	}

	public float getCredit() {
		return this.credit;
	}

	public void setCredit(float credit) {
		this.credit = credit;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getDebit() {
		return this.debit;
	}

	public void setDebit(float debit) {
		this.debit = debit;
	}

	public String getDetailReglement() {
		return this.detailReglement;
	}

	public void setDetailReglement(String detailReglement) {
		this.detailReglement = detailReglement;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Membres getMembre() {
		return this.membre;
	}

	public void setMembre(Membres membre) {
		this.membre = membre;
	}

	public String getReglePar() {
		return this.reglePar;
	}

	public void setReglePar(String reglePar) {
		this.reglePar = reglePar;
	}

}