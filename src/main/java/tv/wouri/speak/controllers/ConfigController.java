package tv.wouri.speak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tv.wouri.speak.config.Setting;
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

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

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
    public String children1(@PathVariable Integer pageNumber, Model model, @RequestParam Optional<String> prenom, @RequestParam Optional<Long> parent, @RequestParam Optional<String> datnaiss, @RequestParam Optional<String> sexe) {

        EnfantSearch search = new EnfantSearch();
        if(prenom.isPresent()) search.setPrenom(prenom.get());
        if(parent.isPresent()) search.setParent(parent.get());
        if(datnaiss.isPresent()) search.setDatenaiss(datnaiss.get());
        if(sexe.isPresent()) search.setSexe(sexe.get());

        Pageable pageable = PageRequest.of(pageNumber - 1, Setting.PAGE_SIZE, Sort.by("id").descending());
        Page<Enfant> page = enfantService.searchEnfant(search, pageable);

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

    @GetMapping(value = "/search2/{pageNumber}")
    public String paiements1(@PathVariable Integer pageNumber, Model model, @RequestParam Optional<String> datepaiement, @RequestParam Optional<String> refIn, @RequestParam Optional<String> refOut, @RequestParam Optional<Double> montant, @RequestParam Optional<String> mode, @RequestParam Optional<Long> payeur) throws ParseException {

        PaiementSearch search = new PaiementSearch();
        if(datepaiement.isPresent()) search.setDatepaiement(datepaiement.get());
        if(refIn.isPresent()) search.setRefIn(refIn.get());
        if(refOut.isPresent()) search.setRefOut(refOut.get());
        if(montant.isPresent()) search.setMontant(montant.get());
        if(mode.isPresent()) search.setModep(mode.get());
        if(payeur.isPresent()) search.setPayeur(payeur.get());

        Pageable pageable = PageRequest.of(pageNumber - 1, Setting.PAGE_SIZE, Sort.by("id").descending());
        Page<Paiement> page = paiementService.searchUser(search, pageable);

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
