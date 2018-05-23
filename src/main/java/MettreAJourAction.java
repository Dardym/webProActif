
import fr.insalyon.dasi.proactif.modele.DemandeIntervention;
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
public class MettreAJourAction extends Action {
    @Override
    public void execute(HttpServletRequest request){
        DemandeIntervention di = serviceProActif.trouverDemande(Long.valueOf(request.getParameter("id")));
        String description = request.getParameter("description");
        di.setDescription(description);
        serviceProActif.mettreAJourDemande(di);
    }
    
}
