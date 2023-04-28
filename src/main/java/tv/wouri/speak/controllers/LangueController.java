package tv.wouri.speak.controllers;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tv.wouri.speak.config.Setting;
import tv.wouri.speak.models.Categorie;
import tv.wouri.speak.models.Langue;
import tv.wouri.speak.service.LangueService;

import java.io.File;

@Controller
@RequestMapping("langue")
public class LangueController {

    @Autowired
    private LangueService langueService;

    @GetMapping
    public String index() {
        return "redirect:/langue/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {

        Page<Langue> page = langueService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        return "langue/list";

    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("langue", new Langue());
        return "langue/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("langue", langueService.get(id));
        return "langue/form";
    }

    @PostMapping(value = "/save")
    public String save(Langue langue,final RedirectAttributes ra) {
        Langue save = langueService.save(langue);
        ra.addFlashAttribute("successFlash", "Enregistrement réussie.");
        return "redirect:/langue/1";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, final RedirectAttributes ra) {
        langueService.delete(id);
        ra.addFlashAttribute("successFlash", "Opération réussie.");
        return "redirect:/langue/1";

    }
}
