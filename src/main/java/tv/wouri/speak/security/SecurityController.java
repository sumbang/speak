package tv.wouri.speak.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tv.wouri.speak.models.*;
import tv.wouri.speak.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SecurityController {

    @Autowired
    AudioService audioService;
    @Autowired
    EnfantService enfantService;
    @Autowired
    EcouteService ecouteService;
    @Autowired
    UserService userService;
    @Autowired
    PaiementService paiementService;

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

        List<Audio> audioList = audioService.getAll();
        List<Enfant> enfantList = enfantService.getAll();
        List<Ecoute> ecouteList = ecouteService.getAll();
        List<User> userList = userService.findByRole(Long.parseLong("1"));
        List<Paiement> paiements = paiementService.findByStatus(1);
        final int[] versement = new int[1];
        paiements.forEach(paiement -> {
            int valeur = paiement.getMontantPaiement().intValue();
            versement[0]+= valeur;
        });

        model.addAttribute("audio", audioList.size()+"");
        model.addAttribute("enfants", enfantList.size()+"");
        model.addAttribute("lecture", ecouteList.size()+"");
        model.addAttribute("parents",userList.size()+"");
        model.addAttribute("versements", versement[0]+"");
        model.addAttribute("taux", "0");
        return "dashboard/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException
    {
        request.logout();
        return "redirect:/login";
    }

}
