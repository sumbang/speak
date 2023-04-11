package tv.wouri.speak.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SecurityController {

    @GetMapping("/accessDenied")
    public String error()
    {
        return "accessDenied";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/")
    public String index(Model model){

       /* Excercise excercise = exerciceService.findByLast();
        List<Affectation> eleveList = affectationService.findBYear(excercise.getId());
        List<Salle> salleList = new ArrayList<Salle>();
        List<User> parentList = new ArrayList<User>();
        List<Enfant> enfantList = enfantService.findByYear(excercise.getId());
        List<Frais> fraisList = fraisService.findByYear(excercise.getId());
        List<Paiement> paiementList = paiementService.findByYear(excercise.getId());

        final int[] impayes = new int[1];
        final int[] versement = new int[1];

        eleveList.forEach(item ->{
            if (!salleList.contains(item.getSalle())) salleList.add(item.getSalle());
        });

        enfantList.forEach(item -> {
            if(!parentList.contains(item.getUser())) parentList.add(item.getUser());
        });

        fraisList.forEach(item -> {
            int valeur = item.getFraisMontant().intValue();
            List<Affectation> eleves = affectationService.findBYSalleear(item.getSalle().getId(),excercise.getId());
            impayes[0]+= valeur * eleves.size();
        });

        paiementList.forEach(item -> {
            if(item.getRefOutPaiement() != null) {
                int valeur = item.getMontantPaiement().intValue();
                versement[0]+= valeur;
            }
        });

        double taux = 0.0;

        if(impayes[0] >0 ) taux = (versement[0] / impayes[0]) * 100;

        model.addAttribute("excercise", excercise.getLibelleExercise());
        model.addAttribute("eleves", eleveList.size()+"");
        model.addAttribute("salles", salleList.size()+"");
        model.addAttribute("parents", parentList.size()+"");
        model.addAttribute("factures", impayes[0]+"");
        model.addAttribute("versements", versement[0]+"");
        model.addAttribute("taux", taux+""); */
        return "dashboard/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException
    {
        request.logout();
        return "redirect:/login";
    }

}
