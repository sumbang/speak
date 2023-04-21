package tv.wouri.speak.controllers;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import tv.wouri.speak.models.Langue;
import tv.wouri.speak.search.AudioSearch;
import tv.wouri.speak.service.AudioService;
import tv.wouri.speak.service.CategorieService;
import tv.wouri.speak.service.FilesStorageService;
import tv.wouri.speak.service.LangueService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("audio")
public class AudioController {

    @Autowired
    private AudioService audioService;
    @Autowired
    private LangueService langueService;
    @Autowired
    private CategorieService categorieService;
    @Autowired
    FilesStorageService storageService;

    @GetMapping
    public String index() {
        return "redirect:/audio/1";
    }

    private final Path root = Paths.get("uploads");

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {

        Page<Audio> page =  audioService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        List<Categorie> categorieList = categorieService.getAll();
        List<Langue> langueList = langueService.getAll();

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("langues", langueList);
        model.addAttribute("categories", categorieList);
        model.addAttribute("search", new AudioSearch());

        return "audio/list";

    }

    @GetMapping(value = "/search/{pageNumber}")
    public String search(@PathVariable Integer pageNumber, Model model, @RequestParam Optional<String> title, @RequestParam Optional<Long> langue, @RequestParam Optional<Long> categorie) throws ParseException {

        AudioSearch search = new AudioSearch();
        if(title.isPresent()) search.setTitle(title.get());
        if(langue.isPresent()) search.setLangue(langue.get());
        if(categorie.isPresent()) search.setCategorie(categorie.get());

        Pageable pageable = PageRequest.of(pageNumber - 1, Setting.PAGE_SIZE, Sort.by("id").descending());
        Page<Audio> page = audioService.searchAudio(search, pageable);
        //Page<Audio> page =  audioService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        List<Categorie> categorieList = categorieService.getAll();
        List<Langue> langueList = langueService.getAll();

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("langues", langueList);
        model.addAttribute("categories", categorieList);
        model.addAttribute("search", search);

        return "audio/search";

    }

    @GetMapping("/add")
    public String add(Model model) {

        List<Categorie> categorieList = categorieService.getAll();
        List<Langue> langueList = langueService.getAll();

        model.addAttribute("audio", new Audio());
        model.addAttribute("langues", langueList);
        model.addAttribute("categories", categorieList);
        return "audio/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        List<Categorie> categorieList = categorieService.getAll();
        List<Langue> langueList = langueService.getAll();
        model.addAttribute("audio", audioService.get(id));
        model.addAttribute("langues", langueList);
        model.addAttribute("categories", categorieList);

        String url = MvcUriComponentsBuilder
                .fromMethodName(AudioController.class, "getFile", audioService.get(id).getFilename().toString()).build().toString();

        String lien = url.replace("http://localhost:8084","https://bantou.wouri.tv");

        model.addAttribute("url", lien);

        return "audio/form1";
    }

    @PostMapping(value = "/save")
    public String save(Audio audio, @RequestParam("fichier") MultipartFile file, final RedirectAttributes ra) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = FilenameUtils.getExtension(fileName);
            if(!ext.isEmpty()) {
                File file1 = new File(root.getFileName()+"/"+audio.getFilename());
                FileSystemUtils.deleteRecursively(file1);
                String saveName = "AUD-"+Setting.randomString(7)+"."+ext;
                audio.setFilename(saveName);
                audio.setDuration("00:10");
                Audio save = audioService.save(audio);
                storageService.save(file,saveName);
            }
            else {
                audio.setDuration("00:10");
                Audio save = audioService.save(audio);
            }
            ra.addFlashAttribute("successFlash", "Enregistrement réussie.");
            return "redirect:/audio/1";
        }
        catch (Exception e) {
            String message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            ra.addFlashAttribute("errorFlash", message);
            return "redirect:/audio/add";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, final RedirectAttributes ra) {
        File file = new File(root.getFileName()+"/"+audioService.get(id).getFilename());
        FileSystemUtils.deleteRecursively(file);
        audioService.delete(id);
        ra.addFlashAttribute("successFlash", "Opération réussie.");
        return "redirect:/audio/1";

    }

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
