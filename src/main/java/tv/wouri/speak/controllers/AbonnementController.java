package tv.wouri.speak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Categorie;
import tv.wouri.speak.service.AbonnementService;
import tv.wouri.speak.service.CategorieService;

@Controller
@RequestMapping("abonnement")
public class AbonnementController {

    @Autowired
    private AbonnementService abonnementService;

    @GetMapping
    public String index() {
        return "redirect:/abonnement/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {

        Page<Abonnement> page = abonnementService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "abonnement/list";

    }

    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("abonnement", new Abonnement());
        return "abonnement/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        model.addAttribute("abonnement", abonnementService.get(id));
        return "abonnement/form";

    }

    @PostMapping(value = "/save")
    public String save(Abonnement excercise, final RedirectAttributes ra) {

        Abonnement save = abonnementService.save(excercise);
        ra.addFlashAttribute("successFlash", "Enregistrement réussie.");
        return "redirect:/abonnement/1";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, final RedirectAttributes ra) {

        abonnementService.delete(id);
        ra.addFlashAttribute("successFlash", "Opération réussie.");
        return "redirect:/abonnement/1";

    }
}
