
import fr.insalyon.dasi.proactif.modele.Client;
import fr.insalyon.dasi.proactif.modele.DemandeIntervention;
import fr.insalyon.dasi.proactif.modele.DemandeAnimal;
import fr.insalyon.dasi.proactif.modele.DemandeLivraison;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author maxim
 */
public class CreerDemandeAction extends Action {
    @Override
    public void execute(HttpServletRequest request){
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        String heureString = request.getParameter("date");
        SimpleDateFormat sft = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat sftDate = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = "";
        Date date = new Date();
        System.out.println(date);
        try {
            dateString = sftDate.format(date) + " " + heureString;
            System.out.println("C EST LA DATE STRING A PARSER");
            System.out.println(dateString);
            date = sft.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        String clientMail = (String)request.getSession().getAttribute("login");
        Client client = serviceProActif.trouverClient(clientMail);
        DemandeIntervention di = null;
        switch (type){
            
            case "incident" : 
                di = new DemandeIntervention(description, date, client);
            break;
            
            case "animal" :
                String animal = request.getParameter("animal");
                di = new DemandeAnimal(animal, description, date, client);
            break;
            
            case "livraison" :
                String objet = request.getParameter("objet");
                String entreprise = request.getParameter("entreprise");
                di = new DemandeLivraison(objet, entreprise, description, date, client);
            break;
            
            default : break;
        }
        System.out.println(di);
        try {
            serviceProActif.creerDemande(di);
            request.setAttribute("employeDispo", true);
        } catch (Exception ex) {
            request.setAttribute("employeDispo", false);
        }
    }
}
