
import fr.insalyon.dasi.proactif.modele.Client;
import fr.insalyon.dasi.proactif.modele.DemandeIntervention;
import fr.insalyon.dasi.proactif.modele.Statut;
import java.util.ArrayList;
import java.util.List;
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
public class DemandeEnCoursAction extends Action {
    @Override
    public void execute(HttpServletRequest request){
        String mail = (String)request.getSession().getAttribute("login");
        Client c = serviceProActif.trouverClient(mail);
        List<DemandeIntervention> tamp = c.getDemandeInterventions();
        List<DemandeIntervention> demandes = new ArrayList<DemandeIntervention>();
        for(DemandeIntervention di : tamp){
            System.out.println(di.getStatut());
            if(di.getStatut() == Statut.EN_COURS){
                demandes.add(di);
            }
        }
        request.setAttribute("demandes",demandes);
    }
    
}
