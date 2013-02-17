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
@Table(name = "servicerefuge")
@NamedQueries({@NamedQuery(name = "Servicerefuge.findAll", query = "SELECT s FROM Servicerefuge s"), @NamedQuery(name = "Servicerefuge.findById", query = "SELECT s FROM Servicerefuge s WHERE s.id = :id"), @NamedQuery(name = "Servicerefuge.findByDateDebut", query = "SELECT s FROM Servicerefuge s WHERE s.dateDebut = :dateDebut"), @NamedQuery(name = "Servicerefuge.findByDateFin", query = "SELECT s FROM Servicerefuge s WHERE s.dateFin = :dateFin"), @NamedQuery(name = "Servicerefuge.findByIdMembreFamille", query = "SELECT s FROM Servicerefuge s WHERE s.idMembreFamille = :idMembreFamille")})
public class Servicerefuge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID" )
    private Integer id;
    @ManyToOne()
    @JoinColumn(name = "IDMEMBRE")
    private Membres membre;
    @Column(name = "DateDebut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Column(name = "DateFin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @Column(name = "ID_MEMBRE_FAMILLE")
    private Integer idMembreFamille;
    @Column(name="nombre_personnes")
    private Integer nombrePersonnes;

    private transient boolean selected;

    public Servicerefuge() {
    }

    public Servicerefuge(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Membres getMembre() {
        return membre;
    }

    public void setMembre(Membres membre) {
        this.membre = membre;
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

    public Integer getIdMembreFamille() {
        return idMembreFamille;
    }

    public void setIdMembreFamille(Integer idMembreFamille) {
        this.idMembreFamille = idMembreFamille;
    }

    public String getNom() {
        return membre.getNom();
    }

    public String getPrenom() {
        return membre.getPrenom();
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
        if (!(object instanceof Servicerefuge)) {
            return false;
        }
        Servicerefuge other = (Servicerefuge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "an1.persistence.Servicerefuge[id=" + id + "]";
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the nombrePersonnes
     */
    public Integer getNombrePersonnes() {
        return nombrePersonnes;
    }

    /**
     * @param nombrePersonnes the nombrePersonnes to set
     */
    public void setNombrePersonnes(Integer nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }
}
