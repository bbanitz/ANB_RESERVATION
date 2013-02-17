/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package an1.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bbernard
 */
@Entity
@Table(name = "membres")
@NamedQueries({@NamedQuery(name = "Membres.findAll", query = "SELECT m FROM Membres m"), @NamedQuery(name = "Membres.findByCode", query = "SELECT m FROM Membres m WHERE m.code = :code"), @NamedQuery(name = "Membres.findByFamille", query = "SELECT m FROM Membres m WHERE m.famille = :famille"), @NamedQuery(name = "Membres.findByTitre", query = "SELECT m FROM Membres m WHERE m.titre = :titre"), @NamedQuery(name = "Membres.findByTypeMembre", query = "SELECT m FROM Membres m WHERE m.typeMembre = :typeMembre"), @NamedQuery(name = "Membres.findByPrenom", query = "SELECT m FROM Membres m WHERE m.prenom = :prenom"), @NamedQuery(name = "Membres.findByTelephoneDomicile", query = "SELECT m FROM Membres m WHERE m.telephoneDomicile = :telephoneDomicile"), @NamedQuery(name = "Membres.findByDateAdhesion", query = "SELECT m FROM Membres m WHERE m.dateAdhesion = :dateAdhesion"), @NamedQuery(name = "Membres.findByTelephoneProf", query = "SELECT m FROM Membres m WHERE m.telephoneProf = :telephoneProf"), @NamedQuery(name = "Membres.findByAdresse2", query = "SELECT m FROM Membres m WHERE m.adresse2 = :adresse2"), @NamedQuery(name = "Membres.findByTelephoneGsm", query = "SELECT m FROM Membres m WHERE m.telephoneGsm = :telephoneGsm"), @NamedQuery(name = "Membres.findByVille", query = "SELECT m FROM Membres m WHERE m.ville = :ville"), @NamedQuery(name = "Membres.findByTelephoneFax", query = "SELECT m FROM Membres m WHERE m.telephoneFax = :telephoneFax"), @NamedQuery(name = "Membres.findByNature", query = "SELECT m FROM Membres m WHERE m.nature = :nature"), @NamedQuery(name = "Membres.findByEmail", query = "SELECT m FROM Membres m WHERE m.email = :email"), @NamedQuery(name = "Membres.findByNom", query = "SELECT m FROM Membres m WHERE m.nom = :nom"), @NamedQuery(name = "Membres.findBySiteweb", query = "SELECT m FROM Membres m WHERE m.siteweb = :siteweb"), @NamedQuery(name = "Membres.findByAdresse1", query = "SELECT m FROM Membres m WHERE m.adresse1 = :adresse1"), @NamedQuery(name = "Membres.findByMemo", query = "SELECT m FROM Membres m WHERE m.memo = :memo"), @NamedQuery(name = "Membres.findByPays", query = "SELECT m FROM Membres m WHERE m.pays = :pays"), @NamedQuery(name = "Membres.findByPhoto", query = "SELECT m FROM Membres m WHERE m.photo = :photo"), @NamedQuery(name = "Membres.findBySexe", query = "SELECT m FROM Membres m WHERE m.sexe = :sexe"), @NamedQuery(name = "Membres.findByDateNaissance", query = "SELECT m FROM Membres m WHERE m.dateNaissance = :dateNaissance"), @NamedQuery(name = "Membres.findBySource", query = "SELECT m FROM Membres m WHERE m.source = :source"), @NamedQuery(name = "Membres.findByCodePostal", query = "SELECT m FROM Membres m WHERE m.codePostal = :codePostal"), @NamedQuery(name = "Membres.findByPassword", query = "SELECT m FROM Membres m WHERE m.password = :password"), @NamedQuery(name = "Membres.findByNomUtilisateur", query = "SELECT m FROM Membres m WHERE m.nomUtilisateur = :nomUtilisateur")})
public class Membres implements Serializable {
//    @Basic(optional = false)
    @javax.persistence.GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "code", nullable = false)
    @Id
    private Integer code;    private static final long serialVersionUID = 1L;
    private transient boolean selected;

    @Column(name = "famille", length = 255)
    private String famille;
    @Column(name = "titre")
    private Integer titre;
    @Column(name = "type_membre", length = 255)
    private String typeMembre;
    @Column(name = "prenom", length = 255)
    private String prenom;
    @Column(name = "telephone_domicile", length = 255)
    private String telephoneDomicile;
    @Column(name = "date_adhesion")
    @Temporal(TemporalType.DATE)
    private Date dateAdhesion;
    @Column(name = "telephone_prof", length = 255)
    private String telephoneProf;
    @Column(name = "adresse2", length = 255)
    private String adresse2;
    @Column(name = "telephone_gsm", length = 255)
    private String telephoneGsm;
    @Column(name = "ville", length = 255)
    private String ville;
    @Column(name = "telephone_fax", length = 255)
    private String telephoneFax;
    @Column(name = "nature", length = 255)
    private String nature;
    @Column(name = "email", length = 255)
    private String email;
    @Column(name = "nom", length = 255)
    private String nom;
    @Column(name = "siteweb", length = 255)
    private String siteweb;
    @Column(name = "adresse1", length = 255)
    private String adresse1;
    @Column(name = "memo", length = 255)
    private String memo;
    @Column(name = "pays", length = 255)
    private String pays;
    @Column(name = "photo", length = 255)
    private String photo;
    @Column(name = "sexe")
    private Integer sexe;
    @Column(name = "date_naissance")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    @Column(name = "source", length = 255)
    private String source;
    @Column(name = "code_postal", length = 255)
    private String codePostal;
    @Column(name = "password", length = 32)
    private String password;
    @Column(name = "NomUtilisateur", length = 64)
    private String nomUtilisateur;
    @Column(name="GroupeUtilisateur",length = 32)
    private String groupeUtilisateur;
    @OneToMany(mappedBy="membre", cascade=CascadeType.ALL)
    private List<MembresFamilles>MembresFamille=new ArrayList<MembresFamilles>();

    public Membres() {
    }

    public Membres(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public Integer getTitre() {
        return titre;
    }

    public void setTitre(Integer titre) {
        this.titre = titre;
    }

    public String getTypeMembre() {
        return typeMembre;
    }

    public void setTypeMembre(String typeMembre) {
        this.typeMembre = typeMembre;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephoneDomicile() {
        return telephoneDomicile;
    }

    public void setTelephoneDomicile(String telephoneDomicile) {
        this.telephoneDomicile = telephoneDomicile;
    }

    public Date getDateAdhesion() {
        return dateAdhesion;
    }

    public void setDateAdhesion(Date dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }

    public String getTelephoneProf() {
        return telephoneProf;
    }

    public void setTelephoneProf(String telephoneProf) {
        this.telephoneProf = telephoneProf;
    }

    public String getAdresse2() {
        return adresse2;
    }

    public void setAdresse2(String adresse2) {
        this.adresse2 = adresse2;
    }

    public String getTelephoneGsm() {
        return telephoneGsm;
    }

    public void setTelephoneGsm(String telephoneGsm) {
        this.telephoneGsm = telephoneGsm;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephoneFax() {
        return telephoneFax;
    }

    public void setTelephoneFax(String telephoneFax) {
        this.telephoneFax = telephoneFax;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public String getAdresse1() {
        return adresse1;
    }

    public void setAdresse1(String adresse1) {
        this.adresse1 = adresse1;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getSexe() {
        return sexe;
    }

    public void setSexe(Integer sexe) {
        this.sexe = sexe;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Membres)) {
            return false;
        }
        Membres other = (Membres) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "an1.Membres[code=" + code + "]";
    }

    /**
     * @return the MembresFamille
     */
    public List<MembresFamilles> getMembresFamille() {
        return MembresFamille;
    }

    /**
     * @param MembresFamille the MembresFamille to set
     */
    public void setMembresFamille(List<MembresFamilles> MembresFamille) {
        this.MembresFamille = MembresFamille;
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
     * @return the groupeUtilisateur
     */
    public String getGroupeUtilisateur() {
        return groupeUtilisateur;
    }

    /**
     * @param groupeUtilisateur the groupeUtilisateur to set
     */
    public void setGroupeUtilisateur(String groupeUtilisateur) {
        this.groupeUtilisateur = groupeUtilisateur;
    }

    public boolean isAdministrateur() {
    	if (groupeUtilisateur==null) return false;
    	return groupeUtilisateur.equals("Administrateur");
    }
}
