package tv.wouri.speak.apiV1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tv.wouri.speak.apiV1.models.*;
import tv.wouri.speak.config.Setting;
import tv.wouri.speak.models.*;
import tv.wouri.speak.repositories.EcouteRepository;
import tv.wouri.speak.service.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/data")
@CrossOrigin
public class DataRestController {

    @Autowired
    EnfantService enfantService;
    @Autowired
    UserService userService;
    @Autowired
    EcouteService ecouteService;
    @Autowired
    CategorieService categorieService;
    @Autowired
    AudioService audioService;
    @Autowired
    LangueService langueService;
    @Autowired
    AbonnementService abonnementService;
    @Autowired
    PaiementService paiementService;
    @Autowired
    MyAbonnementService myAbonnementService;

    @GetMapping(value = "/enfants", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> listeEnfants()  throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());
        List<Enfant> enfantList = enfantService.findByUser(user.getId());
        List<Access> accessList = new ArrayList<Access>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate localDate = LocalDate.now();
        String date = dtf.format(localDate);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String jour = formatDate.format(new Date());

        enfantList.forEach(enfant -> {
            List<MyAbonnement> myAbonnements = myAbonnementService.findByChild(enfant.getId(),jour);
            if(myAbonnements.size() != 0) {
                Access access = new Access();
                access.setEnfant(enfant);
                access.setAcces(true);
                accessList.add(access);
            }
        });

        return new ResponseEntity<>(accessList, HttpStatus.OK);
    }
    @PostMapping(value = "/enfant", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addEnfant(@RequestBody Child child)  throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        if(child.getNom().isEmpty() || child.getIcone().isEmpty() || (child.getDatnaiss() == null) ) {
            return new ResponseEntity<>(new MessageResponse("Veuillez renseigner le nom, l'icone ainsi que la date de naissance"), HttpStatus.NOT_FOUND);
        }

        else {
            Langue langue = langueService.get(child.getLangue());
            Enfant enfant = new Enfant();
            enfant.setNom(child.getNom());
            enfant.setPrenom(child.getPrenom());
            enfant.setSexe(child.getSexe());
            enfant.setVisible(true);
            enfant.setIcone(child.getIcone());
            enfant.setDatnaiss(child.getDatnaiss());
            enfant.setUser(user);
            enfant.setLangue(langue);
            enfantService.save(enfant);

            return new ResponseEntity<>(new MessageResponse("Opération réussie"), HttpStatus.OK);
        }
    }
    @PutMapping(value = "/enfant/{id}", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> editEnfant(@PathVariable("id") Long id,@RequestBody Child child)  throws Exception  {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        Enfant enfant = enfantService.get(id);

        if(enfant == null)  {
            return new ResponseEntity<>(new MessageResponse("Aucune correspondance pour cet enfant"), HttpStatus.NOT_FOUND);
        }

        else if(enfant.getUser() != user) {
            return new ResponseEntity<>(new MessageResponse("Cet enfant n'est pas associé à votre compte"), HttpStatus.NOT_FOUND);
        }

        else {
            Langue langue = langueService.get(child.getLangue());
            enfant.setNom(child.getNom());
            enfant.setPrenom(child.getPrenom());
            enfant.setSexe(child.getSexe());
            enfant.setIcone(child.getIcone());
            enfant.setDatnaiss(child.getDatnaiss());
            enfant.setUser(user);
            enfant.setLangue(langue);
            enfantService.save(enfant);

            return new ResponseEntity<>(new MessageResponse("Opération réussie"), HttpStatus.OK);
        }

    }
    @DeleteMapping(value = "/enfant/{id}", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteEnfant(@PathVariable("id") Long id) throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.findByLogin(userDetails.getUsername());

        Enfant enfant = enfantService.get(id);

        if(enfant == null)  {
            return new ResponseEntity<>(new MessageResponse("Aucune correspondance pour cet enfant"), HttpStatus.NOT_FOUND);
        }

        else if(enfant.getUser() != user) {
            return new ResponseEntity<>(new MessageResponse("Cet enfant n'est pas associé à votre compte"), HttpStatus.NOT_FOUND);
        }

        else {

            enfant.setVisible(false);
            enfantService.save(enfant);
            return new ResponseEntity<>(new MessageResponse("Opération réussie"), HttpStatus.OK);

        }

    }

    @GetMapping(value = "/categories", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> listeCategorie()  throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());
        List<Categorie> categories = categorieService.getAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(value = "/audios-categorie/{id}", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> listeAudio(@RequestHeader("child") Long child,@PathVariable("id") Long id)  throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        Enfant enfant = enfantService.get(child);

        if(enfant == null)  {
            return new ResponseEntity<>(new MessageResponse("Aucune correspondance pour cet enfant"), HttpStatus.NOT_FOUND);
        }

        else if(enfant.getUser() != user) {
            return new ResponseEntity<>(new MessageResponse("Cet enfant n'est pas associé à votre compte"), HttpStatus.NOT_FOUND);
        }

        else {

            List<Audio> audioList = audioService.findByCategorieLangue(id,enfant.getLangue().getId());
            List<Formation> formations = new ArrayList<Formation>();

            audioList.forEach(audio -> {
                List<Ecoute> ecoutes = ecouteService.findByAudioChild(audio.getId(),enfant.getId());
                Boolean listen = (ecoutes.size() == 0) ? false : true;
                Formation formation  = new Formation();
                formation.setAudio(audio);
                formation.setEnfant(enfant);
                formation.setListened(listen);
                formations.add(formation);
            });

            return new ResponseEntity<>(formations, HttpStatus.OK);
        }

    }

    @PutMapping(value = "/audio-lecture/{id}", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> lectureAudio(@RequestHeader("child") Long child,@PathVariable("id") Long id)  throws Exception{

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        Enfant enfant = enfantService.get(child);
        Audio audio = audioService.get(id);

        if(enfant == null)  {
            return new ResponseEntity<>(new MessageResponse("Aucune correspondance pour cet enfant"), HttpStatus.NOT_FOUND);
        }

        else if(audio == null)  {
            return new ResponseEntity<>(new MessageResponse("Aucune correspondance pour cet audio"), HttpStatus.NOT_FOUND);
        }

        else if(enfant.getUser() != user) {
            return new ResponseEntity<>(new MessageResponse("Cet enfant n'est pas associé à votre compte"), HttpStatus.NOT_FOUND);
        }

        else {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
            LocalDateTime localDate = LocalDateTime.now();
            String date = dtf.format(localDate);
            Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);

            Ecoute ecoute = new Ecoute();
            ecoute.setAudio(audio);
            ecoute.setEnfant(enfant);
            ecoute.setStatut(true);
            ecoute.setDateEcoute(date1);
            ecouteService.save(ecoute);

            return new ResponseEntity<>(new MessageResponse("Opération réussie"), HttpStatus.OK);

        }
    }

    @GetMapping(value = "/abonnements", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> listeAbonnements()  throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());
        List<Abonnement> abonnements = abonnementService.getAll();
        return new ResponseEntity<>(abonnements, HttpStatus.OK);
    }

    @PostMapping(value = "/init-paiement", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> initPaiement(@RequestBody InitPaiement initPaiementRequest) throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.findByLogin(userDetails.getUsername());

        Abonnement abonnement = abonnementService.get(initPaiementRequest.getAbonnement());

        Enfant enfant = enfantService.get(initPaiementRequest.getEnfant());

        if(abonnement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Impossible de trouver ce paiement."));
        }

        else if(enfant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("mpossible de trouver cet enfant."));
        }

        else {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            LocalDate localDate = LocalDate.now();
            String date = dtf.format(localDate);
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
            String reference = Setting.randomToken();

            Paiement paiement = new Paiement();
            paiement.setEnfant(enfant);
            paiement.setOwnerPaiement(user);
            paiement.setMontantPaiement(abonnement.getPrice());
            paiement.setPayeur(user);
            paiement.setAbonnement(abonnement);
            paiement.setRefInPaiement(reference);
            paiement.setDatePaiement(date1);
            String input = "{\"enfant\":"+enfant.getNom().toString()+" "+enfant.getPrenom().toString()+",\"abonnement\":"+abonnement.getTitle().toString()+",\"montant\":"+abonnement.getPrice()+",\"reference\":\""+reference+"\",\"date\":\""+date+"\"}";
            paiement.setInputPaiement(input);

            paiementService.save(paiement);

            PaiementResponse paiementResponse = new PaiementResponse();
            paiementResponse.setDatePaiement(paiement.getDatePaiement());
            paiementResponse.setModePaiement(paiement.getModePaiement());
            paiementResponse.setMontantPaiement(paiement.getMontantPaiement());
            paiementResponse.setRecuPaiement(paiement.getRecuPaiement());
            paiementResponse.setRefInPaiement(paiement.getRefInPaiement());
            paiementResponse.setRefOutPaiement(paiement.getRefOutPaiement());
            paiementResponse.setEnfant(paiement.getEnfant());
            paiementResponse.setAbonnement(paiement.getAbonnement());
            paiementResponse.setPayeur(paiement.getPayeur());
            paiementResponse.setComment("");

            return ResponseEntity.status(HttpStatus.OK).body(paiementResponse);

        }

    }

    @PostMapping(value = "/update-paiement", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updatePaiement(@RequestBody TokenPaiement tokenPaiement) throws Exception {
        Paiement paiement = paiementService.findByRefin(tokenPaiement.getRefIn());
        if(paiement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Impossible de trouver ce paiement."));
        }
        else{
            paiement.setDetailPaiement(tokenPaiement.getToken());
            paiementService.update(paiement);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Paiement en attente de finalisation"));
        }
    }

}
