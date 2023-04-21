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
import tv.wouri.speak.apiV1.models.Payment;
import tv.wouri.speak.config.EmailDetails;
import tv.wouri.speak.config.Setting;
import tv.wouri.speak.models.*;
import tv.wouri.speak.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("abonnement")
public class AbonnementController {

    @Autowired
    private AbonnementService abonnementService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaiementService paiementService;
    @Autowired
    MyAbonnementService myAbonnementService;
    @Autowired
    EmailService emailService;

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

    @GetMapping("/paiement")
    public String paiement(Model model) {
        Payement paid = new Payement();
        List<Abonnement> abonnementList = abonnementService.getAll();
        model.addAttribute("paiement", paid);
        model.addAttribute("abonnements", abonnementList);
        return "abonnement/form1";
    }

    @PostMapping(value = "/save1")
    public String save1(Payement payment, final RedirectAttributes ra) throws ParseException {

        Abonnement abonnement = abonnementService.get(payment.getAbonnement());
        User user = userService.findByLogin(payment.getEmail());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate localDate = LocalDate.now();
        String date = dtf.format(localDate);
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        String reference = "PAY-"+ Setting.randomString(12);

        Paiement paiement = new Paiement();
        paiement.setOwnerPaiement(user);
        paiement.setMontantPaiement(abonnement.getPrice());
        paiement.setPayeur(user);
        paiement.setAbonnement(abonnement);
        paiement.setRefInPaiement(reference);
        paiement.setDatePaiement(date1);
        String input = "{\"payeur\":"+user.getNom().toString()+",\"abonnement\":"+abonnement.getTitle().toString()+",\"montant\":"+abonnement.getPrice()+",\"reference\":\""+reference+"\",\"date\":\""+date+"\"}";
        paiement.setInputPaiement(input);
        paiement.setModePaiement(payment.getModep());
        paiement.setRefOutPaiement(payment.getRefOut());
        paiement.setStatus(1);

        paiementService.save(paiement);

        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDateTime localDate1 = LocalDateTime.now().plusDays(abonnement.getDuration());
        String dat = dtf1.format(localDate1);
        Date dat1 = new SimpleDateFormat("yyyy-MM-dd").parse(dat);

        MyAbonnement myAbonnement = new MyAbonnement();
        myAbonnement.setDebut(date1);
        myAbonnement.setFin(dat1);
        myAbonnement.setAbonnement(abonnement);
        myAbonnement.setPayeur(paiement.getPayeur());
        myAbonnementService.save(myAbonnement);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(paiement.getPayeur().getLogin());
        emailDetails.setSubject("Paiement de votre abonnement");
        emailDetails.setMsgBody("Bonjour "+paiement.getPayeur().getNom()+" "+paiement.getPayeur().getPrenom()+",\n\rVous venez de faire un paiement pour l'abonnement <b>"+ abonnement.getTitle() +"</b> d'une valeur de "+ abonnement.getPrice() +" Euros pour votre abonnement.\n\rL'abonnement est déjà disponible dans votre compte, votre référence de paiement est : "+paiement.getRefOutPaiement()+" \n\rCordialement");

        emailService.sendSimpleMail(emailDetails);

        ra.addFlashAttribute("successFlash", "Enregistrement réussie.");
        return "redirect:/abonnement/1";

    }
}
