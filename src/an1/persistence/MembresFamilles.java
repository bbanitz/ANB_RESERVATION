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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bbernard
 */
@Entity
@Table(name = "membresfamilles")
@NamedQueries({@NamedQuery(name = "MembresFamilles.findAll", query = "SELECT m FROM MembresFamilles m"), @NamedQuery(name = "MembresFamilles.findByPrenom", query = "SELECT m FROM MembresFamilles m WHERE m.prenom = :prenom"), @NamedQuery(name = "MembresFamilles.findByDateNaissance", query = "SELECT m FROM MembresFamilles m WHERE m.dateNaissance = :dateNaissance"), @NamedQuery(name = "MembresFamilles.findByNom", query = "SELECT m FROM MembresFamilles m WHERE m.nom = :nom"), @NamedQuery(name = "MembresFamilles.findByType", query = "SELECT m FROM MembresFamilles m WHERE m.type = :type"), @NamedQuery(name = "MembresFamilles.findByDateAdhesion", query = "SELECT m FROM MembresFamilles m WHERE m.dateAdhesion = :dateAdhesion"), @NamedQuery(name = "MembresFamilles.findById", query = "SELECT m FROM MembresFamilles m WHERE m.id = :id")})
public class MembresFamilles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "date_naissance")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    @Column(name = "nom")
    private String nom;
    @Column(name = "type")
    private String type;
    
    //@Basic(optional = false)
    @ManyToOne
    @JoinColumn(name="codeMembre")
    private Membres membre;

    @Column(name = "date_adhesion")
    @Temporal(TemporalType.DATE)
    private Date dateAdhesion;
    @Id
    //@Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    
    private transient boolean selected;

    public MembresFamilles() {
    }

    public MembresFamilles(Integer id) {
        this.id = id;
    }

    public MembresFamilles(Membres membre) {
        this.membre = membre;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Membres getMembre() {
        return membre;
    }

    public void setMembre(Membres membre) {
        this.membre = membre;
    }

    public Date getDateAdhesion() {
        return dateAdhesion;
    }

    public void setDateAdhesion(Date dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MembresFamilles)) {
            return false;
        }
        MembresFamilles other = (MembresFamilles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "an1.MembresFamilles[id=" + id + "]";
    }

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

}
