package tv.wouri.speak.controllers;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tv.wouri.speak.config.Setting;
import tv.wouri.speak.models.Audio;
import tv.wouri.speak.models.Categorie;
import tv.wouri.speak.service.CategorieService;
import tv.wouri.speak.service.FilesStorageService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("categorie")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;
    @Autowired
    FilesStorageService storageService;

    @GetMapping
    public String index() {
        return "redirect:/categorie/1";
    }

    private final Path root = Paths.get("uploads");

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {

        Page<Categorie> page = categorieService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "categorie/list";

    }

    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("categorie", new Categorie());
        return "categorie/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        model.addAttribute("categorie", categorieService.get(id));
        String url = MvcUriComponentsBuilder
                .fromMethodName(AudioController.class, "getFile", categorieService.get(id).getImage().toString()).build().toString();

        model.addAttribute("url", url);

        return "categorie/form1";

    }

    @PostMapping(value = "/save")
    public String save(Categorie categorie, @RequestParam("fichier") MultipartFile file, final RedirectAttributes ra) {

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = FilenameUtils.getExtension(fileName);
            if(!ext.isEmpty()) {
                File file1 = new File(root.getFileName()+"/"+categorie.getImage());
                FileSystemUtils.deleteRecursively(file1);
                String saveName = "CAT-"+Setting.randomString(7)+"."+ext;
                categorie.setImage(saveName);
                Categorie save = categorieService.save(categorie);
                storageService.save(file,saveName);
            }
            else {
                Categorie save = categorieService.save(categorie);
            }
            ra.addFlashAttribute("successFlash", "Enregistrement réussie.");
            return "redirect:/categorie/1";
        }
        catch (Exception e) {
            String message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            ra.addFlashAttribute("errorFlash", message);
            return "redirect:/categorie/add";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, final RedirectAttributes ra) {
        File file = new File(root.getFileName()+"/"+categorieService.get(id).getImage());
        FileSystemUtils.deleteRecursively(file);
        categorieService.delete(id);
        ra.addFlashAttribute("successFlash", "Opération réussie.");
        return "redirect:/categorie/1";

    }

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
