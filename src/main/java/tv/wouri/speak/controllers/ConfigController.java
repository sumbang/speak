package tv.wouri.speak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tv.wouri.speak.models.Categorie;
import tv.wouri.speak.models.Enfant;
import tv.wouri.speak.models.Paiement;
import tv.wouri.speak.models.User;
import tv.wouri.speak.search.EnfantSearch;
import tv.wouri.speak.search.PaiementSearch;
import tv.wouri.speak.service.EcouteService;
import tv.wouri.speak.service.EnfantService;
import tv.wouri.speak.service.PaiementService;
import tv.wouri.speak.service.UserService;

import java.util.List;

@Controller
@RequestMapping("config")
public class ConfigController {
    @Autowired
    private EnfantService enfantService;
    @Autowired
    private PaiementService paiementService;
    @Autowired
    private EcouteService ecouteService;
    @Autowired
    private UserService userService;


    @GetMapping(value = "/children/{pageNumber}")
    public String children(@PathVariable Integer pageNumber, Model model) {

        Page<Enfant> page = enfantService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        List<User> users = userService.getAll();

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("users", users);
        model.addAttribute("search", new EnfantSearch());

        return "config/enfant";

    }

    @GetMapping(value = "/search1/{pageNumber}")
    public String children1(@PathVariable Integer pageNumber, Model model) {

        Page<Enfant> page = enfantService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        List<User> users = userService.getAll();

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("users", users);
        model.addAttribute("search", new EnfantSearch());

        return "config/search1";

    }

    @GetMapping(value = "/paiement/{pageNumber}")
    public String paiements(@PathVariable Integer pageNumber, Model model) {

        Page<Paiement> page = paiementService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        List<User> users = userService.getAll();

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("users", users);
        model.addAttribute("search", new PaiementSearch());

        return "config/paiement";

    }

    @GetMapping(value = "/searhc2/{pageNumber}")
    public String paiements1(@PathVariable Integer pageNumber, Model model) {

        Page<Paiement> page = paiementService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        List<User> users = userService.getAll();

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("users", users);
        model.addAttribute("search", new PaiementSearch());

        return "config/search2";

    }

    @GetMapping("/detail-enfant/{id}")
    public String detail1(@PathVariable Long id, Model model) {
        model.addAttribute("enfant", enfantService.get(id));
        return "config/detail-child";
    }

    @GetMapping("/detail-paiement/{id}")
    public String detail2(@PathVariable Long id, Model model) {
        model.addAttribute("enfant", paiementService.get(id));
        return "config/detail-paiement";
    }

}
