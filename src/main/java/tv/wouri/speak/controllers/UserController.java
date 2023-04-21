package tv.wouri.speak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tv.wouri.speak.config.Setting;
import tv.wouri.speak.models.Categorie;
import tv.wouri.speak.models.Role;
import tv.wouri.speak.models.User;
import tv.wouri.speak.search.UserSearch;
import tv.wouri.speak.service.CategorieService;
import tv.wouri.speak.service.RoleService;
import tv.wouri.speak.service.UserService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String index() {
        return "redirect:/user/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {

        Page<User> page = userService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        List<Role> roles = roleService.getAll();

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("search", new UserSearch());
        model.addAttribute("currentIndex", current);
        model.addAttribute("roles", roles);

        return "user/list";

    }


    @GetMapping(value = "/search/{pageNumber}")
    public String search(@PathVariable Integer pageNumber, Model model, @RequestParam Optional<String> login, @RequestParam Optional<String> nom, @RequestParam Optional<Long> role, @RequestParam Optional<String> dateadded) throws ParseException {

        UserSearch search = new UserSearch();
        if(login.isPresent()) search.setLogin(login.get());
        if(nom.isPresent()) search.setNom(nom.get());
        if(role.isPresent()) search.setRole(role.get());
        if(dateadded.isPresent()) search.setDateadded(dateadded.get());

        Pageable pageable = PageRequest.of(pageNumber - 1, Setting.PAGE_SIZE, Sort.by("id").descending());
        Page<User> page = userService.searchUser(search, pageable);
        //Page<User> page = userService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());


        List<Role> roles = roleService.getAll();
        if(search.getNom() == null) search.setNom("");
        if(search.getLogin() == null) search.setLogin("");

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("search", search);
        model.addAttribute("roles", roles);

        return "user/search";

    }

    @GetMapping("/add")
    public String add(Model model) {
        List<Role> roleList = roleService.getAll();
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleList);
        model.addAttribute("roles", roleList);
        return "user/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        List<Role> roleList = roleService.getAll();
        model.addAttribute("user", userService.get(id));
        model.addAttribute("roles", roleList);
        return "user/form";

    }

    @PostMapping(value = "/save")
    public String save(User user, final RedirectAttributes ra) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userService.save(user);
        ra.addFlashAttribute("successFlash", "Enregistrement réussie.");
        return "redirect:/user/1";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, final RedirectAttributes ra) {

        userService.delete(id);
        ra.addFlashAttribute("successFlash", "Opération réussie.");
        return "redirect:/user/1";

    }

    @GetMapping("/profil")
    public String profil(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.findByLogin(userDetails.getUsername());

        model.addAttribute("user", user);

        return "user/profil";

    }

    @PostMapping(value = "/save1")
    public String save1(User user, final RedirectAttributes ra) {
        User save = userService.save(user);
        ra.addFlashAttribute("successFlash", "Enregistrement réussie.");
        return "redirect:/user/profil";
    }
}
