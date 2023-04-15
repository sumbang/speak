package tv.wouri.speak.apiV1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tv.wouri.speak.apiV1.models.*;
import tv.wouri.speak.config.EmailDetails;
import tv.wouri.speak.config.Setting;
import tv.wouri.speak.models.*;
import tv.wouri.speak.repositories.EcouteRepository;
import tv.wouri.speak.service.*;

import java.text.DateFormat;
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
    @Autowired
    EmailService emailService;

    @PutMapping(value = "/activate-account", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> activateCompte(@RequestBody TokenActive tokenActive)  throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        int result = -1;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.now();
        String date = dtf.format(localDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        result = date1.compareTo(user.getActiveTokenExpirationDate());

        if (!user.getActiveToken().equals(tokenActive.getCode())) {
            return new ResponseEntity<>(new MessageResponse("Code d'activation incorrect"), HttpStatus.BAD_REQUEST);
        }

       /* else if(result > 0) {

            String ret = "Votre code d'activation est expiré, un nouveau code viens de vous être envoyé par mail. "+dateFormat.format(date1)+ " et "+dateFormat1.format(user.getActiveTokenExpirationDate());

            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
            LocalDateTime localDate1 = LocalDateTime.now().plusHours(Setting.TOKEN_ACTIVATION_TIME);
            String dat = dtf.format(localDate);
            Date dat2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dat);

            String token = Setting.randomString(8);
            user.setActiveToken(token);
            user.setActiveTokenExpirationDate(date1);
            userService.save(user);

            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(user.getLogin());
            emailDetails.setSubject("Activation de votre compte");
            emailDetails.setMsgBody("Bonjour "+user.getNom()+" "+user.getPrenom()+",\n\rVous venez de créer un compte sur <b>"+ Setting.appName +"</b>.\n\rAfin de le rendre actif, veuillez renseigner ce code à votre première connexion : "+token+" \n\rCordialement");

            String retour = emailService.sendSimpleMail(emailDetails);

            return new ResponseEntity<>(new MessageResponse(ret), HttpStatus.BAD_REQUEST);

        } */

        else {
            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
            LocalDateTime localDate1 = LocalDateTime.now();
            String dat = dtf.format(localDate);
            Date dat2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dat);

            user.setActiveToken(null);
            user.setActivedDate(dat2);
            user.setActivated(true);
            userService.save(user);

            return new ResponseEntity<>(new MessageResponse("Opération réussie"), HttpStatus.OK);
        }

    }
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
           List<MyAbonnement> myAbonnements = myAbonnementService.findByChild(user.getId(),jour);
            if(myAbonnements.size() != 0) {
                Access access = new Access();
                access.setEnfant(enfant);
                access.setAcces(true);
                accessList.add(access);
            } else {
                Access access = new Access();
                access.setEnfant(enfant);
                access.setAcces(false);
                accessList.add(access);
            }
        });

        return new ResponseEntity<>(accessList, HttpStatus.OK);
    }
    @GetMapping(value = "/langues", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> listeLangues()  throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());
        List<Langue> langueList = langueService.getAll();
        return new ResponseEntity<>(langueList, HttpStatus.OK);
    }
    @GetMapping(value = "/sexes", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> listeSexes()  throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());
        List<Sexe> sexeList = new ArrayList<Sexe>();
        sexeList.add(new Sexe("F","Fille"));
        sexeList.add(new Sexe("M","Garçon"));
        return new ResponseEntity<>(sexeList, HttpStatus.OK);
    }
    @PostMapping(value = "/enfant", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addEnfant(@RequestBody Child child)  throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        if(child.getPrenom().isEmpty() || child.getIcone().isEmpty() || (child.getDatnaiss() == null) || child.getLangue().isEmpty()  || child.getSexe().isEmpty() ) {
            return new ResponseEntity<>(new MessageResponse("Veuillez renseigner tous les champs"), HttpStatus.NOT_FOUND);
        }

        else {

            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(child.getDatnaiss());
            Langue langue = langueService.get(Long.parseLong(child.getLangue()));
            Enfant enfant = new Enfant();
            enfant.setNom(child.getNom());
            enfant.setPrenom(child.getPrenom());
            enfant.setSexe(child.getSexe());
            enfant.setVisible(true);
            enfant.setIcone(child.getIcone());
            enfant.setDatnaiss(date1);
            enfant.setUser(user);
            enfant.setLangue(langue);
            enfantService.save(enfant);

            return new ResponseEntity<>(new MessageResponse("Opération réussie"), HttpStatus.OK);
        }
    }
    @PutMapping(value = "/enfant/{id}", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> editEnfant(@PathVariable("id") String id,@RequestBody Child child)  throws Exception  {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        Enfant enfant = enfantService.get(Long.parseLong(id));

        if(enfant == null)  {
            return new ResponseEntity<>(new MessageResponse("Aucune correspondance pour cet enfant"), HttpStatus.NOT_FOUND);
        }

        else if(enfant.getUser() != user) {
            return new ResponseEntity<>(new MessageResponse("Cet enfant n'est pas associé à votre compte"), HttpStatus.NOT_FOUND);
        }

        else {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(child.getDatnaiss());
            Langue langue = langueService.get(Long.parseLong(child.getLangue()));
            enfant.setNom(child.getNom());
            enfant.setPrenom(child.getPrenom());
            enfant.setSexe(child.getSexe());
            enfant.setIcone(child.getIcone());
            enfant.setDatnaiss(date1);
            enfant.setUser(user);
            enfant.setLangue(langue);
            enfantService.save(enfant);

            return new ResponseEntity<>(new MessageResponse("Opération réussie"), HttpStatus.OK);
        }

    }
    @DeleteMapping(value = "/enfant/{id}", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteEnfant(@PathVariable("id") String id) throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.findByLogin(userDetails.getUsername());

        Enfant enfant = enfantService.get(Long.parseLong(id));

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
    public ResponseEntity<?> listeAudio(@RequestHeader("child") String child,@PathVariable("id") Long id)  throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        Enfant enfant = enfantService.get(Long.parseLong(child));

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
    public ResponseEntity<?> lectureAudio(@RequestHeader("child") String child,@PathVariable("id") Long id)  throws Exception{

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        Enfant enfant = enfantService.get(Long.parseLong(child));
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

        if(abonnement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Impossible de trouver ce paiement."));
        }

        else {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            LocalDate localDate = LocalDate.now();
            String date = dtf.format(localDate);
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
            String reference = Setting.randomToken();

            Paiement paiement = new Paiement();
            paiement.setOwnerPaiement(user);
            paiement.setMontantPaiement(abonnement.getPrice());
            paiement.setPayeur(user);
            paiement.setAbonnement(abonnement);
            paiement.setRefInPaiement(reference);
            paiement.setDatePaiement(date1);
            String input = "{\"payeur\":"+user.getNom().toString()+",\"abonnement\":"+abonnement.getTitle().toString()+",\"montant\":"+abonnement.getPrice()+",\"reference\":\""+reference+"\",\"date\":\""+date+"\"}";
            paiement.setInputPaiement(input);

            paiementService.save(paiement);

            PaiementResponse paiementResponse = new PaiementResponse();
            paiementResponse.setDatePaiement(paiement.getDatePaiement());
            paiementResponse.setModePaiement(paiement.getModePaiement());
            paiementResponse.setMontantPaiement(paiement.getMontantPaiement());
            paiementResponse.setRecuPaiement(paiement.getRecuPaiement());
            paiementResponse.setRefInPaiement(paiement.getRefInPaiement());
            paiementResponse.setRefOutPaiement(paiement.getRefOutPaiement());
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
    @GetMapping(value = "/account", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> myaccount()  throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PutMapping(value = "/account", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateAccount(@RequestBody Compte compte)  throws Exception{

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        user.setNom(compte.getNom());
        user.setPrenom(compte.getPrenom());

        userService.save(user);

        return new ResponseEntity<>(new MessageResponse("Opération réussie"), HttpStatus.OK);

    }

    @PostMapping(value = "/ecouter", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addEcoute(@RequestBody Ecouter ecouter)  throws Exception{

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate localDate = LocalDate.now();
        String date = dtf.format(localDate);
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);

        Enfant enfant = enfantService.get(Long.parseLong(ecouter.getEnfant()));
        Audio audio = audioService.get(Long.parseLong(ecouter.getAudio()));

        Ecoute ecoute = new Ecoute();
        ecoute.setEnfant(enfant);
        ecoute.setAudio(audio);
        ecoute.setStatut(true);
        ecoute.setDateEcoute(date1);
        ecouteService.save(ecoute);

        return new ResponseEntity<>(new MessageResponse("Opération réussie"), HttpStatus.OK);

    }



}
