
import fr.insalyon.dasi.proactif.service.ResultatConnexion;
import fr.insalyon.dasi.proactif.service.Service;
import fr.insalyon.dasi.proactif.service.StatutResultatConnexion;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author maxim
 */
public class ConnexionAction extends Action {
    @Override
    public void execute(HttpServletRequest request){
        String login = request.getParameter("login");
        String mdp = request.getParameter("password");
        Service s = new Service();
        setServiceProActif(s);
        ResultatConnexion rc = serviceProActif.connexion(login,mdp);
        request.setAttribute("rc",rc);
    }
}
