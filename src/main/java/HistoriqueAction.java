
import fr.insalyon.dasi.proactif.modele.Client;
import fr.insalyon.dasi.proactif.modele.DemandeIntervention;
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
public class HistoriqueAction extends Action {
    @Override
    public void execute(HttpServletRequest request){
        String mail = (String)request.getSession().getAttribute("login");
        Client c = serviceProActif.trouverClient(mail);
        List<DemandeIntervention> demandes = c.getDemandeInterventions();
        request.setAttribute("demandes",demandes);
    }
}
