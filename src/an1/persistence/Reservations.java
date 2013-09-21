/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package an1.persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author bbernard
 */
@Entity
@Table(name = "reservations")
@NamedQueries({
		@NamedQuery(name = "Reservations.findAll", query = "SELECT r FROM Reservations r"),
		@NamedQuery(name = "Reservations.findById", query = "SELECT r FROM Reservations r WHERE r.id = :id"),
		@NamedQuery(name = "Reservations.findByDateDebut", query = "SELECT r FROM Reservations r WHERE r.dateDebut = :dateDebut"),
		@NamedQuery(name = "Reservations.findByDateFin", query = "SELECT r FROM Reservations r WHERE r.dateFin = :dateFin"),
		@NamedQuery(name = "Reservations.findByNombreAdultesSection", query = "SELECT r FROM Reservations r WHERE r.nombreAdultesSection = :nombreAdultesSection"),
		@NamedQuery(name = "Reservations.findByNombreEnfantsSection", query = "SELECT r FROM Reservations r WHERE r.nombreEnfantsSection = :nombreEnfantsSection"),
		@NamedQuery(name = "Reservations.findByNombreAdultesAutreSection", query = "SELECT r FROM Reservations r WHERE r.nombreAdultesAutreSection = :nombreAdultesAutreSection"),
		@NamedQuery(name = "Reservations.findByNombreEnfantsAutreSection", query = "SELECT r FROM Reservations r WHERE r.nombreEnfantsAutreSection = :nombreEnfantsAutreSection"),
		@NamedQuery(name = "Reservations.findByNombreAdultesNonAN", query = "SELECT r FROM Reservations r WHERE r.nombreAdultesNonAN = :nombreAdultesNonAN"),
		@NamedQuery(name = "Reservations.findByNombreEnfantsNonAN", query = "SELECT r FROM Reservations r WHERE r.nombreEnfantsNonAN = :nombreEnfantsNonAN"),
		@NamedQuery(name = "Reservations.findByNombreAdultesGratuits", query = "SELECT r FROM Reservations r WHERE r.nombreAdultesGratuits = :nombreAdultesGratuits"),
		@NamedQuery(name = "Reservations.findByNombreEnfantsGratuits", query = "SELECT r FROM Reservations r WHERE r.nombreEnfantsGratuits = :nombreEnfantsGratuits") })
public class Reservations implements Serializable {

	/**
	 * @return the modificationLe
	 */
	public Date getModificationLe() {
		return modificationLe;
	}

	/**
	 * @param modificationLe
	 *            the modificationLe to set
	 */
	public void setModificationLe(Date modificationLe) {
		this.modificationLe = modificationLe;
	}

	public enum StatutReservation {
		Demande, Option, Ferme, AcomptePayé, Payé
	};

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @Basic(optional = false)
	@Column(name = "ID", nullable = false)
	private Integer id;
	@Column(name = "chambre1")
	private boolean chambre1;
	private boolean chambre2, chambre3, chambre4, chambre5, chambre6, chambre7,
			chambre8, chambre9;
	@Column(name = "date_creation")
	@Temporal(TemporalType.DATE)
	private Date dateCreation;
	@Column(name = "DATEDEBUT")
	@Temporal(TemporalType.DATE)
	private Date dateDebut;
	@Column(name = "DATEFIN")
	@Temporal(TemporalType.DATE)
	private Date dateFin;
	@OneToOne
	@JoinColumn(name = "IDMEMBRE")
	private Membres membre;
	@Column(name = "nombreAdultesSection")
	private Integer nombreAdultesSection;
	@Column(name = "nombreEnfantsSection")
	private Integer nombreEnfantsSection;
	@Column(name = "nombreAdultesAutreSection")
	private Integer nombreAdultesAutreSection;
	@Column(name = "nombreEnfantsAutreSection")
	private Integer nombreEnfantsAutreSection;
	@Column(name = "nombreAdultesNonAN")
	private Integer nombreAdultesNonAN;
	@Column(name = "nombreEnfantsNonAN")
	private Integer nombreEnfantsNonAN;
	@Column(name = "nombreAdultesGratuits")
	private Integer nombreAdultesGratuits;
	@Column(name = "nombreEnfantsGratuits")
	private Integer nombreEnfantsGratuits;
	@Column(name = "statut")
	private String statut;
	@Column(name = "modification_le")
	@Temporal(TemporalType.DATE)
	private Date modificationLe;
	@OneToOne
	@JoinColumn(name = "modification_par")
	private Membres modificationPar;

	private transient boolean create;
	private transient boolean selected;

	public Reservations() {
	}

	public Reservations(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Integer getNombreAdultesSection() {
		if (nombreAdultesSection == null)
			return 0;
		else
			return nombreAdultesSection;
	}

	public void setNombreAdultesSection(Integer nombreAdultesSection) {
		this.nombreAdultesSection = nombreAdultesSection;
	}

	public Integer getNombreEnfantsSection() {
		if (nombreEnfantsSection == null)
			return 0;
		else
			return nombreEnfantsSection;
	}

	public void setNombreEnfantsSection(Integer nombreEnfantsSection) {
		this.nombreEnfantsSection = nombreEnfantsSection;
	}

	public Integer getNombreAdultesAutreSection() {
		if (nombreAdultesAutreSection == null)
			return 0;
		else
			return nombreAdultesAutreSection;
	}

	public void setNombreAdultesAutreSection(Integer nombreAdultesAutreSection) {
		this.nombreAdultesAutreSection = nombreAdultesAutreSection;
	}

	public Integer getNombreEnfantsAutreSection() {
		if (nombreEnfantsAutreSection == null)
			return 0;
		else
			return nombreEnfantsAutreSection;
	}

	public void setNombreEnfantsAutreSection(Integer nombreEnfantsAutreSection) {
		this.nombreEnfantsAutreSection = nombreEnfantsAutreSection;
	}

	public Integer getNombreAdultesNonAN() {
		if (nombreAdultesNonAN == null)
			return 0;
		else
			return nombreAdultesNonAN;
	}

	public void setNombreAdultesNonAN(Integer nombreAdultesNonAN) {
		this.nombreAdultesNonAN = nombreAdultesNonAN;
	}

	public Integer getNombreEnfantsNonAN() {
		if (nombreEnfantsNonAN == null)
			return 0;
		else
			return nombreEnfantsNonAN;
	}

	public void setNombreEnfantsNonAN(Integer nombreEnfantsNonAN) {
		this.nombreEnfantsNonAN = nombreEnfantsNonAN;
	}

	public Integer getNombreAdultesGratuits() {
		if (nombreAdultesGratuits == null)
			return 0;
		else
			return nombreAdultesGratuits;
	}

	public void setNombreAdultesGratuits(Integer nombreAdultesGratuits) {
		this.nombreAdultesGratuits = nombreAdultesGratuits;
	}

	public Integer getNombreEnfantsGratuits() {
		if (nombreEnfantsGratuits == null)
			return 0;
		else
			return nombreEnfantsGratuits;
	}

	public void setNombreEnfantsGratuits(Integer nombreEnfantsGratuits) {
		this.nombreEnfantsGratuits = nombreEnfantsGratuits;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Reservations)) {
			return false;
		}
		Reservations other = (Reservations) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "an1.persistence.Reservations[id=" + id + "]";
	}

	/**
	 * @return the create
	 */
	public boolean isCreate() {
		return create;
	}

	/**
	 * @param create
	 *            the create to set
	 */
	public void setCreate(boolean create) {
		this.create = create;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int nombrePersonnes() {
		return (getNombreAdultesAutreSection() + getNombreAdultesGratuits()
				+ getNombreAdultesNonAN() + getNombreAdultesSection()
				+ getNombreEnfantsAutreSection() + getNombreEnfantsGratuits()
				+ getNombreEnfantsNonAN() + getNombreEnfantsSection());
	}

	/**
	 * @return the membre
	 */
	public Membres getMembre() {
		return membre;
	}

	/**
	 * @param membre
	 *            the membre to set
	 */
	public void setMembre(Membres membre) {
		this.membre = membre;
	}

	public String getNom() {
		return membre.getNom();
	}

	public String getPrenom() {
		return membre.getPrenom();
	}

	/**
	 * @return the statut
	 */
	public String getStatut() {
		return statut;
	}

	/**
	 * @param statut
	 *            the statut to set
	 */
	public void setStatut(String statut) {
		this.statut = statut;
	}

	/**
	 * @return the modificationPar
	 */
	public Membres getModificationPar() {
		return modificationPar;
	}

	/**
	 * @param modificationPar
	 *            the modificationPar to set
	 */
	public void setModificationPar(Membres modificationPar) {
		this.modificationPar = modificationPar;
	}

	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation
	 *            the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * @return the chambre1
	 */
	public boolean isChambre1() {
		return chambre1;
	}

	/**
	 * @param chambre1
	 *            the chambre1 to set
	 */
	public void setChambre1(boolean chambre1) {
		this.chambre1 = chambre1;
	}

	/**
	 * @return the chambre2
	 */
	public boolean isChambre2() {
		return chambre2;
	}

	/**
	 * @param chambre2
	 *            the chambre2 to set
	 */
	public void setChambre2(boolean chambre2) {
		this.chambre2 = chambre2;
	}

	/**
	 * @return the chambre3
	 */
	public boolean isChambre3() {
		return chambre3;
	}

	/**
	 * @param chambre3
	 *            the chambre3 to set
	 */
	public void setChambre3(boolean chambre3) {
		this.chambre3 = chambre3;
	}

	/**
	 * @return the chambre4
	 */
	public boolean isChambre4() {
		return chambre4;
	}

	/**
	 * @param chambre4
	 *            the chambre4 to set
	 */
	public void setChambre4(boolean chambre4) {
		this.chambre4 = chambre4;
	}

	/**
	 * @return the chambre5
	 */
	public boolean isChambre5() {
		return chambre5;
	}

	/**
	 * @param chambre5
	 *            the chambre5 to set
	 */
	public void setChambre5(boolean chambre5) {
		this.chambre5 = chambre5;
	}

	/**
	 * @return the chambre6
	 */
	public boolean isChambre6() {
		return chambre6;
	}

	/**
	 * @param chambre6
	 *            the chambre6 to set
	 */
	public void setChambre6(boolean chambre6) {
		this.chambre6 = chambre6;
	}

	/**
	 * @return the chambre7
	 */
	public boolean isChambre7() {
		return chambre7;
	}

	/**
	 * @param chambre7
	 *            the chambre7 to set
	 */
	public void setChambre7(boolean chambre7) {
		this.chambre7 = chambre7;
	}

	/**
	 * @return the chambre8
	 */
	public boolean isChambre8() {
		return chambre8;
	}

	/**
	 * @param chambre8
	 *            the chambre8 to set
	 */
	public void setChambre8(boolean chambre8) {
		this.chambre8 = chambre8;
	}

	/**
	 * @return the chambre9
	 */
	public boolean isChambre9() {
		return chambre9;
	}

	/**
	 * @param chambre9
	 *            the chambre9 to set
	 */
	public void setChambre9(boolean chambre9) {
		this.chambre9 = chambre9;
	}

	public String getListeChambres() {
		boolean first = true;
		StringBuffer sb = new StringBuffer();
		if (chambre1) {
			first = false;
			sb.append("1");
		}
		if (chambre2) {
			if (!first)
				sb.append(", ");
			first = false;
			sb.append("2");
		}
		if (chambre3) {
			if (!first)
				sb.append(", ");
			first = false;
			sb.append("3");
		}
		if (chambre4) {
			if (!first)
				sb.append(", ");
			first = false;
			sb.append("4");
		}
		if (chambre5) {
			if (!first)
				sb.append(", ");
			first = false;
			sb.append("5");
		}
		if (chambre6) {
			if (!first)
				sb.append(", ");
			first = false;
			sb.append("6");
		}
		if (chambre7) {
			if (!first)
				sb.append(", ");
			first = false;
			sb.append("7");
		}
		if (chambre8) {
			if (!first)
				sb.append(", ");
			first = false;
			sb.append("8");
		}
		if (chambre9) {
			if (!first)
				sb.append(", ");
			first = false;
			sb.append("9");
		}
		return sb.toString();

	}

}
