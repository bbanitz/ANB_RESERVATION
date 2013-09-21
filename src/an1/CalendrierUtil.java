/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package an1;

import an1.persistence.Reservations;
import an1.persistence.ReservationsJpaController;
import an1.persistence.Servicerefuge;
import an1.persistence.ServicerefugeJpaController;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author bbernard
 */
public class CalendrierUtil {

    private ArrayList<UnMois> listeMois;
    private Date dateCentrale;
    private ServicerefugeJpaController servicerefugeJpaController;
    private ReservationsJpaController reservationsJpaController;
    private List<Servicerefuge> serviceRefugeEnCours;
    private List<Reservations> reservationsEnCours;

    public void refresh() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(dateCentrale);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.MONTH, false);
        if (cal.get(Calendar.MONTH) == Calendar.DECEMBER) {
            cal.roll(Calendar.YEAR, false);
        }

        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
        FieldPosition fp1 = new FieldPosition(DateFormat.MONTH_FIELD);
        FieldPosition fp2 = new FieldPosition(DateFormat.DAY_OF_WEEK_FIELD);
        FieldPosition fp3 = new FieldPosition(DateFormat.YEAR_FIELD);
        listeMois = new ArrayList<UnMois>();
        for (int i = 0; i < 3; i++) {
            cal.set(Calendar.DAY_OF_MONTH, 1);
            UnMois unMois = new UnMois();
            unMois.setId("mois" + i);
            StringBuffer sb = new StringBuffer();
            sb = df.format(cal.getTime(), sb, fp1);
            StringBuffer sb1 = new StringBuffer();
            sb1 = df.format(cal.getTime(), sb, fp3);
            unMois.nomMois = sb.substring(fp1.getBeginIndex(), fp1.getEndIndex()) + " " + sb1.substring(fp3.getBeginIndex(), fp3.getEndIndex());
            listeMois.add(unMois);
            int Max=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int j = 1; j <= Max; j++) {
                UnJour jour = new UnJour();
                jour.setDateJour(cal.getTime());
                jour.setJourSemaine(cal.get(Calendar.DAY_OF_WEEK));
//                System.out.println(jour.getDateJour());
                sb = df.format(cal.getTime(), sb, fp2);
                jour.setNom(sb.substring(fp2.getBeginIndex(), fp2.getEndIndex()) + " " + j);
                unMois.getListeJours().add(jour);
                //System.out.println(j+" "+jour.getDateJour()+" "+jour.nom);
                cal.roll(Calendar.DAY_OF_MONTH, true);
            }
            
//            System.out.println("++++++++++++++++++++++++++++++++");
            cal.roll(Calendar.MONTH, true);
            if (cal.get(Calendar.MONTH) == Calendar.JANUARY) {
                cal.roll(Calendar.YEAR, true);
            }

        }
//        System.out.println("-------------------------------------");

    }

    public CalendrierUtil(Date dateCentrale, ServicerefugeJpaController s,ReservationsJpaController r) {
        servicerefugeJpaController = s;
        reservationsJpaController = r;
        this.dateCentrale = dateCentrale;
        refresh();
    }

    public void moisSuivant() {
        Calendar c = new GregorianCalendar();
        c.setTime(dateCentrale);
        c.roll(Calendar.MONTH, true);
        if (c.get(Calendar.MONTH) == Calendar.JANUARY) {
            c.roll(Calendar.YEAR, true);
        }
        dateCentrale = c.getTime();
        refresh();
    }

    public void moisPrecedant() {
        Calendar c = new GregorianCalendar();
        c.setTime(dateCentrale);
        c.roll(Calendar.MONTH, false);
        if (c.get(Calendar.MONTH) == Calendar.DECEMBER) {
            c.roll(Calendar.YEAR, false);
        }
        dateCentrale = c.getTime();
//        System.out.println(dateCentrale);
        refresh();
    }

    public String getHTML(String[] selectedItems) {
        StringBuffer html = new StringBuffer();


        html.append("<table width=\"100%\" border=\"3\">");
        

       
        for (int mois = 0; mois < 3; mois++) {
             html.append("<tr><th style=\"font-size:22px; color:yellow\">" + listeMois.get(mois).getNomMois() + "</th></tr>");
            int indexJourS = (listeMois.get(mois).getUnJour(0).jourSemaine + 5) % 7 + 1;
            int indexJour = 0;
            html.append("<tr>");
            html.append("<td width=\"100%\"><table border=\"3\" width=\"100%\" style=\"background-color:cyan\">");
            html.append("<tr><th>Lu</th><th>Ma</th><th>Me</th><th>Je</th><th>Ve</th><th>Sa</th><th>Di</th></tr>");
            for (int semaine = 0; semaine < 6; semaine++) {
                html.append("<tr>");
                
                for (int jourSemaine = 1; jourSemaine <= 7; jourSemaine++) {
                    String couleur="grey";
                    if (jourSemaine==6||jourSemaine==7) couleur="yellow";
                    html.append("<td style=\"background-color:"+couleur+"\">");
                    if (indexJourS == jourSemaine) {
                        if (listeMois.get(mois).getListeJours().size() > indexJour) {
                            UnJour jour = listeMois.get(mois).getListeJours().get(indexJour);
                            
                            indexJour++;
                            indexJourS++;
                            if (indexJourS > 7) {
                                indexJourS = 1;
                            }
                            int nombrePersonnesService=0;
                            html.append("<div align=\"center\" style =\"font-size:16px; font-weight:bold;\">"+Integer.toString(indexJour)+"</div>");
                            if (jour.getServiceRefuge().size()>0) {
                                
                                for (int i=0;i<jour.getServiceRefuge().size();i++) nombrePersonnesService+=jour.getServiceRefuge().get(i).getNombrePersonnes();
                                //iceSubmit(form1,this,event)
                                //html.append("<a href=\"Calendrier.jspx?form1:valeurMois="+mois+"&form1:valeurJour="+indexJour+"\"><img src=\"./resources/bonhomme.gif\"></a> ");
                                html.append("<img onClick=\"javascript:document.getElementById('form1:valeurMois').value='"+mois+"';document.getElementById('form1:valeurJour').value='"+indexJour+"';document.forms['form1'].submit();\" src=\"./resources/bonhomme.gif\"> ");
                                html.append(nombrePersonnesService);
                                html.append("&nbsp");
                            }
                            int nbLits=nombrePersonnesService;
                            if (nbLits>0 || jour.getReservations(selectedItems).size()>0) {
                                html.append("<img onClick=\"javascript:document.getElementById('form1:valeurMois').value='"+mois+"';document.getElementById('form1:valeurJour').value='"+indexJour+"';document.forms['form1'].submit();\" src=\"./resources/lit.png\"> ");
                                
                                 for (int i=0;i<jour.getReservations(selectedItems).size();i++) {
                                   nbLits+=jour.getReservations(selectedItems).get(i).nombrePersonnes();
                                }
                                
                                html.append(nbLits);
                            }
                        }
                    }
                    html.append("</td>");

                }
                html.append("</tr>");
                
            }
            html.append("</table>");
            html.append("</tr>");
        }
        html.append("</table>");
        return html.toString();
    }

    /**
     * @return the serviceRefugeEnCours
     */
    public List<Servicerefuge> getServiceRefugeEnCours() {
        return serviceRefugeEnCours;
    }

    /**
     * @param serviceRefugeEnCours the serviceRefugeEnCours to set
     */
    public void setServiceRefugeEnCours(List<Servicerefuge> serviceRefugeEnCours) {
        this.serviceRefugeEnCours = serviceRefugeEnCours;
    }

    /**
     * @return the reservationsEnCours
     */
    public List<Reservations> getReservationsEnCours() {
        return reservationsEnCours;
    }

    /**
     * @param reservationsEnCours the reservationsEnCours to set
     */
    public void setReservationsEnCours(List<Reservations> reservationsEnCours) {
        this.reservationsEnCours = reservationsEnCours;
    }

    public class UnJour {

        private String nom;
        private Date dateJour;
        private int jourSemaine;

        public List<Servicerefuge> getServiceRefuge() {
            return servicerefugeJpaController.getServiceRefuge(dateJour);
        }

        public List<Reservations>getReservations(String[] selectedItems) {
            return reservationsJpaController.getReservations(dateJour,selectedItems);
        }
        /**
         * @return the nom
         */
        public String getNom() {
            return nom;
        }

        /**
         * @param nom the nom to set
         */
        public void setNom(String nom) {
            this.nom = nom;
        }

        /**
         * @return the dateJour
         */
        public Date getDateJour() {
            return dateJour;
        }

        /**
         * @param dateJour the dateJour to set
         */
        public void setDateJour(Date dateJour) {
            this.dateJour = dateJour;
        }

        /**
         * @return the jourSemaine
         */
        public int getJourSemaine() {
            return jourSemaine;
        }

        /**
         * @param jourSemaine the jourSemaine to set
         */
        public void setJourSemaine(int jourSemaine) {
            this.jourSemaine = jourSemaine;
        }
    }

    public class UnMois {

        private String nomMois;
        private String id;
        private ArrayList<UnJour> listeJours;

        public UnMois() {
            listeJours = new ArrayList<UnJour>();
        }

        public UnJour getUnJour(int jour) {
            return (UnJour) getListeJours().get(jour);
        }

        /**
         * @return the nomMois
         */
        public String getNomMois() {
            return nomMois;
        }

        /**
         * @param nomMois the nomMois to set
         */
        public void setNomMois(String nomMois) {
            this.nomMois = nomMois;
        }

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the listeJours
         */
        public ArrayList<UnJour> getListeJours() {
            return listeJours;
        }

        /**
         * @param listeJours the listeJours to set
         */
        public void setListeJours(ArrayList<UnJour> listeJours) {
            this.listeJours = listeJours;
        }
    }

    /**
     * @return the listeMois
     */
    public ArrayList<UnMois> getListeMois() {
        return listeMois;
    }

    /**
     * @param listeMois the listeMois to set
     */
    public void setListeMois(ArrayList<UnMois> listeMois) {
        this.listeMois = listeMois;

    }
}
