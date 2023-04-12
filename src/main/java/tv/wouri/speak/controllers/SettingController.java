package tv.wouri.speak.controllers;



import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tv.wouri.speak.apiV1.models.MessageResponse;
import tv.wouri.speak.apiV1.models.Payment;
import tv.wouri.speak.apiV1.models.ResetRequest;
import tv.wouri.speak.config.EmailDetails;
import tv.wouri.speak.config.Setting;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Paiement;
import tv.wouri.speak.models.User;
import tv.wouri.speak.apiV1.models.TokenResetRequest;
import tv.wouri.speak.service.AbonnementService;
import tv.wouri.speak.service.EmailService;
import tv.wouri.speak.service.PaiementService;
import tv.wouri.speak.service.UserService;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Controller
@RequestMapping("setting")
public class SettingController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AbonnementService abonnementService;
    @Autowired
    private PaiementService paiementService;

    @GetMapping("/reset")
    public String reset(Model model) {
        ResetRequest reset = new ResetRequest();
        model.addAttribute("reset", reset);
        return "setting/reset";
    }

    @GetMapping("/paiement")
    public String paiement(Model model) {
        Payment paid = new Payment();
        List<Abonnement> abonnementList = abonnementService.getAll();
        model.addAttribute("paiement", paid);
        model.addAttribute("abonnements", abonnementList);
        return "setting/paiement";
    }

    @PostMapping(value = "/payment")
    public String payer(Payment payment, final RedirectAttributes ra) throws Exception,JSONException {

        User user = userService.findByLogin(payment.getEmail());
        Abonnement abonnement = abonnementService.get(payment.getAbonnement());

        if(user == null) {
            ra.addFlashAttribute("errorFlash", "Aucun compte trouvé avec ce login");
            return "redirect:/setting/paiement";
        }

        else  if(abonnement == null) {
            ra.addFlashAttribute("errorFlash", "Abonnement invalide");
            return "redirect:/setting/paiement";
             }

        else {

            if(user.getRole().getId() != 1) {
                ra.addFlashAttribute("errorFlash", "Vous n'êtes pas autorisé à utiliser cette interface");
                return "redirect:/setting/paiement";
            }

            else {


                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
                LocalDate localDate = LocalDate.now();
                String date = dtf.format(localDate);
                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
                String reference = "PAY-"+Setting.randomString(12);

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

                String succesUrl = Setting.APPS_URL +"/setting/success";
                String faildUrl = Setting.APPS_URL +"/setting/faild";

                if(payment.getModep().equals("PAYPAL")) {

                    UUID uuid = UUID.randomUUID();
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

                    OkHttpClient client = new OkHttpClient().newBuilder().build();

                    RequestBody body1 = RequestBody.create(mediaType,"grant_type=client_credentials");

                    Request request1 = new Request.Builder()
                            .url("https://api-m.paypal.com/v1/oauth2/token")
                            .post(body1)
                            .addHeader("Authorization", "Basic QVV3YVZUbVRYb0VIR1hvTW1XVzd5eTljNjNEX19GNkRJRmVRSGY1SmdtTEZTX2ZzRldKNHQ5ZDRWbnVMQzBocnNjY3hLcmhsS1ZVdVJ2TWs6RUhYR0twbERoYTdjaERwRWx1WDZ6ZUVQYWplNVZoaTlkZk5jalN1RWhucGoyUlUxMHMycjVUcjVZQjhRWXk4cW5JQm4xa29USzNwSnlpZS0=")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .build();

                    Response response1 = client.newCall(request1).execute();
                    String token = "";
                    String retour1 = response1.body().string();
                    System.out.println("retour : "+retour1);
                    JSONObject jsonObject = new JSONObject(retour1);
                    token = jsonObject.getString("access_token");
                    System.out.println("token : "+token);

                    MediaType mediaType1 = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType1, "{\n  \"intent\": \"CAPTURE\",\n  \"purchase_units\": [\n    {\n      \"reference_id\": \""+reference+"\",\n      \"amount\": {\n        \"currency_code\": \"EUR\",\n        \"value\": \""+abonnement.getPrice()+"\"\n      }\n    }\n  ],\n  \"payment_source\": {\n    \"paypal\": {\n      \"experience_context\": {\n        \"payment_method_preference\": \"IMMEDIATE_PAYMENT_REQUIRED\",\n        \"payment_method_selected\": \"PAYPAL\",\n        \"brand_name\": \"WOURI TV\",\n        \"locale\": \"en-US\",\n        \"landing_page\": \"LOGIN\",\n        \"shipping_preference\": \"SET_PROVIDED_ADDRESS\",\n        \"user_action\": \"PAY_NOW\",\n        \"return_url\": \""+succesUrl+"\",\n        \"cancel_url\": \""+faildUrl+"\"\n      }\n    }\n  }\n}");

                    Request request = new Request.Builder()
                            .url("https://api-m.paypal.com/v2/checkout/orders")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .addHeader("PayPal-Request-Id", uuid.toString())
                            .addHeader("Authorization", "Bearer "+token+"")
                            .build();

                    Response response = client.newCall(request).execute();
                    String retour2 = response.body().string();
                    System.out.println("retour complet : "+retour2);
                    JSONObject jsonObject1 = new JSONObject(retour2);

                    String refOut = uuid.toString()+"@@"+jsonObject1.getString("id");
                    paiement.setModePaiement("PAYPAL");
                    paiement.setStatus(0);
                    paiement.setRefOutPaiement(refOut);
                    paiementService.save(paiement);
                    String url = "";

                    for(Object o: jsonObject1.getJSONArray("links")){
                        if ( o instanceof JSONObject ) {
                            if(((JSONObject) o).getString("rel").equals("payer-action")) {
                             url =   ((JSONObject) o).getString("href");
                            }
                        }
                    }


                    if(!url.isEmpty()) return "redirect:"+url+"";

                    else return "redirect:"+faildUrl+"";
                }

                else {

                    return "redirect:/setting/token";
                }

            }
        }
    }


    @PostMapping(value = "/ask")
    public String ask(ResetRequest reset, final RedirectAttributes ra) {

        User user = userService.findByLogin(reset.getUsername());

        if(user == null) {
            ra.addFlashAttribute("errorFlash", "Aucun compte trouvé avec ce login");
            return "redirect:/setting/reset";
        }

        else {

            if(user.getRole().getId() == 1) {
                ra.addFlashAttribute("errorFlash", "Vous n'êtes pas autorisé à utiliser cette interface");
                return "redirect:/setting/reset";
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
                    ra.addFlashAttribute("errorFlash", "Impossible d'envoyer le mail de réinitialisation");
                    return "redirect:/setting/reset";
                }

                else {
                    ra.addFlashAttribute("successFlash", "Mail de reinitialisation envoyée.");
                    return "redirect:/setting/token";
                }

            }
        }
    }

    @GetMapping("/token")
    public String token(Model model) {
        TokenResetRequest token1 = new TokenResetRequest();
        model.addAttribute("token1", token1);
        return "setting/change";
    }

    @GetMapping("/success")
    public String success(Model model) {
        return "setting/succes";
    }

    @GetMapping("/faild")
    public String failed(Model model) {
        return "setting/failed";
    }

    @PostMapping(value = "/change")
    public String change(TokenResetRequest token, final RedirectAttributes ra) {

        User user = userService.findByResetToken(token.getToken());

        if (user == null) {
            ra.addFlashAttribute("errorFlash", "Aucune correspondance pour ce code de réinitialisatio");
            return "redirect:/setting/change";

        } else {

            if(user.getRole().getId() == 1) {
                ra.addFlashAttribute("errorFlash", "Vous n'êtes pas autorisé à utiliser cette interface");
                return "redirect:/setting/change";
            }
            else {

                user.setPassword(passwordEncoder.encode(token.getPassword()));
                user.setResetToken(null);
                userService.update(user);

                // email notification for password change
                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient(user.getLogin());
                emailDetails.setSubject("Changement de mot de passe");
                emailDetails.setMsgBody("Bonjour " + user.getNom() + ",\n\rVous venez de changer avec succès votre mot de passe. \n\rSi cette opération ne venait pas de vous, veuillez immédiatement nous contacter. \n\rCordialement");

                String retour = emailService.sendSimpleMail(emailDetails);

                if (retour.equals("Error while Sending Mail")) {
                    ra.addFlashAttribute("errorFlash", "Impossible d'envoyer le mail de réinitialisation");
                    return "redirect:/setting/change";
                } else {
                    ra.addFlashAttribute("successFlash", "Enregistrement réussie.");
                    return "redirect:/login";
                }
            }
        }

    }
}
