
import fr.insalyon.dasi.proactif.service.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class InscriptionAction extends Action {
    @Override
    public void execute(HttpServletRequest request){
        String civilite = request.getParameter("civilite");
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String dateString = request.getParameter("date");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        try {
              date = formatter.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        String mail = request.getParameter("mail");
        String tel = request.getParameter("tel");
        String adresse = request.getParameter("adresse");
        Service s = new Service();
        setServiceProActif(s);
        if(serviceProActif.trouverClient(mail)==null){
            request.setAttribute("code", true);
        }else{
            request.setAttribute("code", false);
        }
        serviceProActif.inscrireClient( nom, prenom, date, civilite, adresse, tel, mail);
    }
}

