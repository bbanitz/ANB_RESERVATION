/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package an1.persistence;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author bbernard
 */


@Entity
@Table(name = "groupesutilisateurs")
@NamedQueries({@NamedQuery(name = "GroupesUtilisateurs.findAll", query = "SELECT g FROM GroupesUtilisateurs g"), @NamedQuery(name = "GroupesUtilisateurs.findByNom", query = "SELECT g FROM GroupesUtilisateurs g WHERE g.nom = :nom"), @NamedQuery(name = "GroupesUtilisateurs.findByAutorisations", query = "SELECT g FROM GroupesUtilisateurs g WHERE g.autorisations = :autorisations"), @NamedQuery(name = "GroupesUtilisateurs.findByServiceRefuge", query = "SELECT g FROM GroupesUtilisateurs g WHERE g.serviceRefuge = :serviceRefuge"), @NamedQuery(name = "GroupesUtilisateurs.findByListeMembres", query = "SELECT g FROM GroupesUtilisateurs g WHERE g.listeMembres = :listeMembres")})
public class GroupesUtilisateurs implements Serializable {

    public enum typeAcces {accesTotal,lectureSeule,lectureEcriture,aucunAcces} ;
    private static final long serialVersionUID = 1L;
    @Id
 //   @Basic(optional = false)
    @Column(name = "Nom")
    private String nom;
    @Column(name = "Autorisations")
    private typeAcces autorisations;
    @Column(name = "ServiceRefuge")
    private typeAcces serviceRefuge;
    @Column(name = "ListeMembres")
    private typeAcces listeMembres;
    @Column(name = "FicheMembre")
    private typeAcces ficheMembre;

    public GroupesUtilisateurs() {
    }

    public GroupesUtilisateurs(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public typeAcces getAutorisations() {
        return autorisations;
    }

    public void setAutorisations(typeAcces autorisations) {
        this.autorisations = autorisations;
    }

    public typeAcces getServiceRefuge() {
        return serviceRefuge;
    }

    public void setServiceRefuge(typeAcces serviceRefuge) {
        this.serviceRefuge = serviceRefuge;
    }

    public typeAcces getListeMembres() {
        return listeMembres;
    }

    public void setListeMembres(typeAcces listeMembres) {
        this.listeMembres = listeMembres;
    }

        /**
     * @return the ficheMembre
     */
    public typeAcces getFicheMembre() {
        return ficheMembre;
    }

    /**
     * @param ficheMembre the ficheMembre to set
     */
    public void setFicheMembre(typeAcces ficheMembre) {
        this.ficheMembre = ficheMembre;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nom != null ? nom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupesUtilisateurs)) {
            return false;
        }
        GroupesUtilisateurs other = (GroupesUtilisateurs) object;
        if ((this.nom == null && other.nom != null) || (this.nom != null && !this.nom.equals(other.nom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "an1.persistence.GroupesUtilisateurs[nom=" + nom + "]";
    }

}
