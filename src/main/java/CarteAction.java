
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
public class CarteAction extends Action {
    @Override
    public void execute(HttpServletRequest request){
        List<DemandeIntervention> demandesJour = serviceProActif.getDemandesDuJour();
        request.setAttribute("demandesJour",demandesJour);
    }
    
}

