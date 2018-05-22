
import fr.insalyon.dasi.proactif.service.Service;
import static java.net.Proxy.Type.HTTP;
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



public abstract class Action {
    Service serviceProActif;
    public abstract void execute(HttpServletRequest request);
    public void setServiceProActif(Service serviceProActif){
        this.serviceProActif = serviceProActif;
    }
}
