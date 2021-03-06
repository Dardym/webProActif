
import fr.insalyon.dasi.proactif.modele.DemandeIntervention;
import fr.insalyon.dasi.proactif.modele.Statut;
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
public class CloturerAction extends Action {
    @Override
    public void execute(HttpServletRequest request){
        DemandeIntervention di = serviceProActif.trouverDemande(Long.valueOf(request.getParameter("id")));
        String s = request.getParameter("statut");
        Statut statut = null;
        if(s.equals("En cours")){
                statut = Statut.EN_COURS;
            } else if (s.equals("Probleme")){
                statut = Statut.PROBLEME;
            } else if (s.equals("Termine")){
                statut = Statut.FINI;
            }
        String commentaire = request.getParameter("commentaire");
        Date heureDeFin = new Date();
        serviceProActif.cloturerDemande(di, statut, commentaire, heureDeFin);
    }
    
}
