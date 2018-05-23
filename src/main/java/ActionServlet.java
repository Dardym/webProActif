/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.maps.model.LatLng;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.modele.DemandeAnimal;
import fr.insalyon.dasi.proactif.modele.DemandeIntervention;
import fr.insalyon.dasi.proactif.modele.DemandeLivraison;
import fr.insalyon.dasi.proactif.modele.Statut;
import fr.insalyon.dasi.proactif.service.ResultatConnexion;
import fr.insalyon.dasi.proactif.service.StatutResultatConnexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author maxim
 */
@WebServlet(name = "ActionServletSession", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        this.init();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession(true); 
        String todo = request.getParameter("action");
        
        if(todo.equals("login")){
            Action connexionAction = new ConnexionAction();
            connexionAction.execute(request);
            ResultatConnexion rc = (ResultatConnexion)request.getAttribute("rc");
            PrintWriter out = response.getWriter();
            
            if(rc.getStatut() == StatutResultatConnexion.CLIENT){
                session.setAttribute("login",request.getParameter("login"));
                JsonObject j = new JsonObject();
                j.addProperty("typeConnexion","client");
                out.println(j);
            }
            else if(rc.getStatut() == StatutResultatConnexion.EMPLOYE){
                session.setAttribute("login",request.getParameter("login"));
                JsonObject j = new JsonObject();
                j.addProperty("typeConnexion","employe");
                out.println(j);
            }
            else if(rc.getStatut() == StatutResultatConnexion.ECHEC){
                JsonObject j = new JsonObject();
                j.addProperty("typeConnexion","echec");
                out.println(j);
            }
        } else if (todo.equals("inscription")){
            PrintWriter out = response.getWriter();
            Action InscriptionAction = new InscriptionAction();
            InscriptionAction.execute(request);
            JsonObject j = new JsonObject();
            if((boolean)request.getAttribute("code")){
                j.addProperty("code", Boolean.TRUE);
            }else{
                j.addProperty("code", Boolean.FALSE);
            }
            out.println(j);
            
        } else if (todo.equals("creerDemande")){
            PrintWriter out = response.getWriter();
            Action creerDemandeAction = new CreerDemandeAction();
            JsonObject j = new JsonObject();
            creerDemandeAction.execute(request);
            if((boolean)request.getAttribute("employeDispo")){
                j.addProperty("employeDispo", Boolean.TRUE);
            } else{
                j.addProperty("employeDispo", Boolean.FALSE);
            }
            out.println(j);
        } else if (todo.equals("consultation")){
            PrintWriter out = response.getWriter();
            Action demandeEnCoursAction = new DemandeEnCoursAction();
            demandeEnCoursAction.execute(request);
            printListeDemande(out, (List<DemandeIntervention>)request.getAttribute("demandes"));
            
        } else if (todo.equals("historique")){
            PrintWriter out = response.getWriter();
            Action historiqueAction = new HistoriqueAction();
            historiqueAction.execute(request);
            printListeDemande(out, (List<DemandeIntervention>)request.getAttribute("demandes"));
        } else if (todo.equals("carte")){
            PrintWriter out = response.getWriter();
            Action carteAction = new CarteAction();
            carteAction.execute(request);
            printListeDemande(out,(List<DemandeIntervention>)request.getAttribute("demandesJour"));
        } else if (todo.equals("cloturer")){
            PrintWriter out = response.getWriter();
            Action cloturerAction = new CloturerAction();
            cloturerAction.execute(request);
            JsonObject j = new JsonObject();
            j.addProperty("cloturer","true");
            out.println(j);
        } else if (todo.equals("annuler")){
            PrintWriter out = response.getWriter();
            Action annulerDemandeAction = new AnnulerDemandeAction();
            annulerDemandeAction.execute(request);
            JsonObject j = new JsonObject();
            j.addProperty("annuler","true");
            out.println(j);
        } else if (todo.equals("mettre a jour")){
            PrintWriter out = response.getWriter();
            Action mettreAJourAction = new MettreAJourAction();
            mettreAJourAction.execute(request);
            JsonObject j = new JsonObject();
            j.addProperty("mettreAJour","true");
            out.println(j);
        }
        
        /*try (PrintWriter out = response.getWriter()) {
             TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ActionServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ActionServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }*/
        
        this.destroy();
    }
    
    public static void printListeDemande(PrintWriter out, List<DemandeIntervention> demandes){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        JsonArray jsonListe = new JsonArray();
        for(DemandeIntervention di : demandes){
           
            JsonObject jsonDI = new JsonObject();
            LatLng l = new LatLng();
            double lat = di.getClient().getCoordonnees().lat;
            double lng = di.getClient().getCoordonnees().lng;
            jsonDI.addProperty("lat",lat);
            jsonDI.addProperty("lng",lng);
            jsonDI.addProperty("id", di.getId());
            jsonDI.addProperty("clientNom",di.getClient().getNom());
            jsonDI.addProperty("clientPrenom",di.getClient().getPrenom());
            
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfHeure = new SimpleDateFormat("HH:mm");
            String dateString = sdfDate.format(di.getHorodate());
            String heureString = sdfHeure.format(di.getHorodate());
            String heureFinString = "";
            if(di.getHeureFin()!=null){
                heureFinString = sdfHeure.format(di.getHeureFin());
            }
            System.out.println(heureFinString);
            jsonDI.addProperty("heure",heureString);
            jsonDI.addProperty("heureDeFin",heureFinString);
            jsonDI.addProperty("date",dateString);
            jsonDI.addProperty("employeNom",di.getEmploye().getNom());
            jsonDI.addProperty("employePrenom",di.getEmploye().getPrenom());
            jsonDI.addProperty("description",di.getDescription());
            jsonDI.addProperty("commentaire",di.getCommentaire());
            
            String type ="";
            if(di.getClass() == DemandeIntervention.class){
                type = "Incident";
            } else if (di.getClass() == DemandeLivraison.class){
                type = "Livraison";
            } else if ( di.getClass() == DemandeAnimal.class){
                type = "Animal";
            }
            jsonDI.addProperty("type", type);
            
            String statut ="";
            if(di.getStatut() == Statut.EN_COURS){
                statut = "En cours";
            } else if (di.getStatut() == Statut.FINI){
                statut = "Terminé";
            } else if (di.getStatut() == Statut.PROBLEME){
                statut = "Problème";
            }
            jsonDI.addProperty("statut", statut);
            
            jsonListe.add(jsonDI);
        }
        
        JsonObject container = new JsonObject();
        container.add("demandes",jsonListe);
        out.println(gson.toJson(container));
    }
    
    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
