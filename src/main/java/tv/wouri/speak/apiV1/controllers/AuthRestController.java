package tv.wouri.speak.apiV1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tv.wouri.speak.apiV1.config.JwtUtils;
import tv.wouri.speak.apiV1.models.*;
import tv.wouri.speak.config.EmailDetails;
import tv.wouri.speak.config.Setting;
import tv.wouri.speak.models.*;
import tv.wouri.speak.security.LoginService;
import tv.wouri.speak.service.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthRestController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    LoginService loginService;
    @Autowired
    EmailService emailService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PaiementService paiementService;
    @Autowired
    MyAbonnementService myAbonnementService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    AbonnementService abonnementService;

    @PostMapping(value = "/signup", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> create(@RequestBody SignupRequest signupRequest) throws Exception {
        User exist = userService.findByLogin(signupRequest.getLogin());
        if (exist != null) {
            return new ResponseEntity<>(new MessageResponse("Un utilisateur existe déjà avec ce login"), HttpStatus.BAD_REQUEST);
        }
        else {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
            LocalDateTime localDate = LocalDateTime.now().plusHours(Setting.TOKEN_ACTIVATION_TIME);
            String date = dtf.format(localDate);
            Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);

            String token = Setting.randomString(8);

            Role role = roleService.get(Long.parseLong(signupRequest.getRole()));
            User user = new User();
            user.setLogin(signupRequest.getLogin());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setNom(signupRequest.getNom());
            user.setPrenom(signupRequest.getPrenom());
            user.setStatus(signupRequest.getStatus());
            user.setRole(role);
            user.setCountry(signupRequest.getCountry());
            user.setResetToken(null);
            user.setActiveToken(token);
            user.setActivated(false);
            user.setStatus(true);
            user.setActiveTokenExpirationDate(date1);
            userService.save(user);

            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(user.getLogin());
            emailDetails.setSubject("Activation de votre compte");
            emailDetails.setMsgBody("Bonjour "+user.getNom()+" "+user.getPrenom()+",\n\rVous venez de créer un compte sur <b>"+ Setting.appName +"</b>.\n\rAfin de le rendre actif, veuillez renseigner ce code à votre première connexion : "+token+" \n\rCordialement");

             String retour = emailService.sendSimpleMail(emailDetails);

            if(retour.equals("Error while Sending Mail")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Impossible d'envoyer le mail d'activation"));
            }

            else { return new ResponseEntity<>(new MessageResponse("Utilisateur crée avec succès"), HttpStatus.OK); }

        }
    }

    @PostMapping( value = "/signin", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(@RequestBody AuthentificationRequest request) throws Exception {

        User user = userService.findByLogin(request.getLogin());

        if(user == null) {
            return new ResponseEntity<>(new MessageResponse("Aucun compte avec cet identifiant"), HttpStatus.NOT_FOUND);
        }

        else if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(new MessageResponse("Identifiant ou mot de passe incorrect "), HttpStatus.BAD_REQUEST);
        }

        else if(user.getRole().getId() != 1) {

            return new ResponseEntity<>(new MessageResponse("Votre profil n'est aps autorisé pour cette connexion"), HttpStatus.UNAUTHORIZED);
        }

        else {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = loginService.loadUserByUsername(request.getLogin());

            final String token = jwtUtils.generateJwtToken(userDetails);

            user.setAuthToken(token); userService.update(user);

            return ResponseEntity.status(HttpStatus.OK).body(new AuthentificationResponse(token,user.getRole(),user.getNom()+" "+user.getPrenom(),user.getStatus(), user.getActivated()));

        }

    }

    @GetMapping(value = "/signout", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> logoutUser() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.findByLogin(userDetails.getUsername());

        user.setAuthToken(null);

        userService.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping(value = "/reset-pwd", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> resetPassword(@RequestBody ResetRequest request) {
        User user = userService.findByLogin(request.getUsername());
        if(user == null) {
            return new ResponseEntity<>(new MessageResponse("Aucun compte trouvé avec ce login "), HttpStatus.NOT_FOUND);
        }
        else {

            if(user.getRole().getId() != 1) {
                return new ResponseEntity<>(new MessageResponse("Vous n'êtes pas autorisé à utiliser cette interface"), HttpStatus.BAD_REQUEST);
            }

            else {

                String token = Setting.randomString(8);
                user.setResetToken(token);
                userService.update(user);

                // send email/sms with resetToken
                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient(user.getLogin());
                emailDetails.setSubject("Réinitialisation du mot de passe");
                emailDetails.setMsgBody("Bonjour "+user.getNom()+",\n\rVous avez initié une demande de réinitialisation de votre mot de passe.\n\rVeuillez saisir le code de réinialisation suivant pour terminer : "+token+" \n\rCordialement");

                String retour = emailService.sendSimpleMail(emailDetails);

                if(retour.equals("Error while Sending Mail")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Impossible d'envoyer le mail de réinitialisation"));
                }

                else return ResponseEntity.status(HttpStatus.OK).body(new TokenReset("Mail de reinitialisation envoyée", token));
            }

        }
    }

    @PostMapping(value = "/change-pwd", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> resetPasswordRequest(@RequestBody TokenResetRequest tokenResetRequest) {
        User user = userService.findByResetToken(tokenResetRequest.getToken());
        if(user == null) {
            return new ResponseEntity<>(new MessageResponse("Aucune correspondance pour ce code de réinitialisation"), HttpStatus.NOT_FOUND);
        }
        else {

            if(user.getRole().getId() != 1) {
                return new ResponseEntity<>(new MessageResponse("Vous n'êtes pas autorisé à utiliser cette interface"), HttpStatus.BAD_REQUEST);
            }

            else {

                user.setPassword(passwordEncoder.encode(tokenResetRequest.getPassword()));
                user.setResetToken(null);
                userService.update(user);

                // email notification for password change
                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient(user.getLogin());
                emailDetails.setSubject("Changement de mot de passe");
                emailDetails.setMsgBody("Bonjour "+user.getNom()+",\n\rVous venez de changer avec succès votre mot de passe. \n\rSi cette opération ne venait pas de vous, veuillez immédiatement nous contacter. \n\rCordialement");

                String retour = emailService.sendSimpleMail(emailDetails);

                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Mot de passe modifié avec succès "));
            }

        }
    }

    @PostMapping (value = "/check-token", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> tokenIsValid(@RequestBody TokenExist tokenExist) {
        User user = userService.findByAuthToken(tokenExist.getToken());
        TokenExistResponse tokenExistResponse = new TokenExistResponse();
        if(user  == null) {
            tokenExistResponse.setResult(false);
            return new ResponseEntity<>(tokenExistResponse, HttpStatus.NOT_FOUND);
        }
        else {
            tokenExistResponse.setResult(true);
            return new ResponseEntity<>(tokenExistResponse, HttpStatus.OK);
        }

    }

    @PostMapping(value = "/end-paiement", produces = Setting.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> endPaiement(@RequestBody EndPaiement endPaiement) throws Exception{
        Paiement paiement = paiementService.findByRefOut(endPaiement.getTransactionRef());
        if(paiement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Impossible de trouver ce paiement."));
        }
        else {

            paiement.setModePaiement(endPaiement.getModePaiement());
            paiement.setOutPutPaiement(endPaiement.getJsonRetour());
            paiement.setRefOutPaiement(endPaiement.getOperatorRef());
            paiement.setStatus(endPaiement.getStatus());
            paiementService.update(paiement);

            Abonnement abonnement = abonnementService.get(paiement.getAbonnement().getId());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            LocalDateTime localDate = LocalDateTime.now();
            String date = dtf.format(localDate);
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

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

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Opération terminée."));
        }
    }

}
