package tv.wouri.speak.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.wouri.speak.models.Paiement;

import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    @Query(value = "select * from paiement where detail_paiement = ?1", nativeQuery = true)
    Paiement findByRefOut(String refOut);

    @Query(value = "select * from paiement where ref_in_paiement = ?1", nativeQuery = true)
    Paiement findByRefIn(String refIn);

    @Query(value = "select * from paiement where status = ?1", nativeQuery = true)
    List<Paiement> findByStatus(int status);

    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and ref_out_paiement =?3 and montant_paiement = ?4 and mode_paiement = ?5 and owner_paiement_id_user  = ?6", nativeQuery = true)
    Page<Paiement> searchUser(String date, String refin, String refout, Double montant, String mode, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and ref_out_paiement =?3 and montant_paiement = ?4 and mode_paiement = ?5 ", nativeQuery = true)
    Page<Paiement> searchUserNoPayeur(String date, String refin, String refout, Double montant, String mode, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and ref_out_paiement =?3 and montant_paiement = ?4 and owner_paiement_id_user  = ?5", nativeQuery = true)
    Page<Paiement> searchUserNoMode(String date, String refin, String refout, Double montant, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and ref_out_paiement =?3 and mode_paiement = ?4 and owner_paiement_id_user  = ?5", nativeQuery = true)
    Page<Paiement> searchUserNoMontant(String date, String refin, String refout,String mode, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and montant_paiement = ?3 and mode_paiement = ?4 and owner_paiement_id_user  = ?5", nativeQuery = true)
    Page<Paiement> searchUserNoOut(String date, String refin, Double montant, String mode, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_out_paiement =?2 and montant_paiement = ?3 and mode_paiement = ?4 and owner_paiement_id_user  = ?5", nativeQuery = true)
    Page<Paiement> searchUserNoIn(String date, String refout, Double montant, String mode, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and ref_out_paiement =?2 and montant_paiement = ?3 and mode_paiement = ?4 and owner_paiement_id_user  = ?5", nativeQuery = true)
    Page<Paiement> searchUserNoDate(String refin, String refout, Double montant, String mode, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and ref_out_paiement =?3 and montant_paiement = ?4 ", nativeQuery = true)
    Page<Paiement> searchUserNoModePayeur(String date, String refin, String refout, Double montant, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and ref_out_paiement =?3 and mode_paiement = ?4", nativeQuery = true)
    Page<Paiement> searchUserNoMontantPayeur(String date, String refin, String refout, String mode,Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and montant_paiement = ?3 and mode_paiement = ?4 ", nativeQuery = true)
    Page<Paiement> searchUserNoOutPayeur(String date, String refin, Double montant, String mode, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_out_paiement =?2 and montant_paiement = ?3 and mode_paiement = ?4 ", nativeQuery = true)
    Page<Paiement> searchUserNoInPayeur(String date,  String refout, Double montant, String mode,  Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and ref_out_paiement =?2 and montant_paiement = ?3 and mode_paiement = ?4 ", nativeQuery = true)
    Page<Paiement> searchUserNoDatePayeur(String refin, String refout, Double montant, String mode, Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and ref_out_paiement =?2 and mode_paiement = ?3 and owner_paiement_id_user  = ?4", nativeQuery = true)
    Page<Paiement> searchUserNoDateMontant( String refin, String refout, String mode, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and montant_paiement = ?2 and mode_paiement = ?3 and owner_paiement_id_user  = ?4", nativeQuery = true)
    Page<Paiement> searchUserNoDateOut(String refin, Double montant, String mode, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where ref_out_paiement =?1 and montant_paiement = ?2 and mode_paiement = ?3 and owner_paiement_id_user  = ?4", nativeQuery = true)
    Page<Paiement> searchUserNoDateIn(String refout, Double montant, String mode, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and ref_out_paiement =?3 ", nativeQuery = true)
    Page<Paiement> searchUserDateInOut(String date, String refin, String refout, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and montant_paiement = ?3 ", nativeQuery = true)
    Page<Paiement> searchUserDateInMontant(String date, String refin, Double montant, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and mode_paiement = ?3", nativeQuery = true)
    Page<Paiement> searchUserDateInMode(String date, String refin,String mode, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2 and owner_paiement_id_user  = ?3", nativeQuery = true)
    Page<Paiement> searchUserDateInPayeur(String date, String refin, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_out_paiement =?2 and montant_paiement = ?3 ", nativeQuery = true)
    Page<Paiement> searchUserDateOutMontant(String date, String refout, Double montant, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_out_paiement =?2 and and mode_paiement = ?3 ", nativeQuery = true)
    Page<Paiement> searchUserDateOutMode(String date, String refout,String mode, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_out_paiement =?2  and owner_paiement_id_user  = ?3", nativeQuery = true)
    Page<Paiement> searchUserDateOutPayeur(String date, String refout, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and ref_out_paiement =?2 and montant_paiement = ?3", nativeQuery = true)
    Page<Paiement> searchUserInOutMontant(String refin, String refout, Double montant, Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and ref_out_paiement =?2 and mode_paiement = ?3 ", nativeQuery = true)
    Page<Paiement> searchUserInOutMode( String refin, String refout,  String mode, Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and ref_out_paiement =?2 and owner_paiement_id_user  = ?3", nativeQuery = true)
    Page<Paiement> searchUserInOutPayeur(String refin, String refout, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and montant_paiement = ?2 and mode_paiement = ?3", nativeQuery = true)
    Page<Paiement> searchUserInMontantMode( String refin, Double montant, String mode,  Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and montant_paiement = ?2 and owner_paiement_id_user  = ?3", nativeQuery = true)
    Page<Paiement> searchUserInMontantPayeur( String refin, Double montant, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where ref_out_paiement =?1 and montant_paiement = ?2 and mode_paiement = ?3 ", nativeQuery = true)
    Page<Paiement> searchUserOutMontantMode(String refout, Double montant, String mode, Pageable pageable);
    @Query(value = "select * from paiement where ref_out_paiement =?1 and montant_paiement = ?2 and owner_paiement_id_user  = ?3", nativeQuery = true)
    Page<Paiement> searchUserOutMontantPayeur(String refout, Double montant,Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where  montant_paiement = ?1 and mode_paiement = ?2 and owner_paiement_id_user  = ?3", nativeQuery = true)
    Page<Paiement> searchUserMontantModePayeur(Double montant, String mode, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_in_paiement = ?2", nativeQuery = true)
    Page<Paiement> searchUserDateIn(String date, String refin, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and ref_out_paiement =?2", nativeQuery = true)
    Page<Paiement> searchUserDateOut(String date, String refout,  Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and montant_paiement = ?2 ", nativeQuery = true)
    Page<Paiement> searchUserDateMontant(String date, Double montant, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1  and mode_paiement = ?2 ", nativeQuery = true)
    Page<Paiement> searchUserDateMode(String date, String mode, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 and owner_paiement_id_user = ?2", nativeQuery = true)
    Page<Paiement> searchUserDatePayeur(String date, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and ref_out_paiement =?2 ", nativeQuery = true)
    Page<Paiement> searchUserInOut(String refin, String refout,Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and montant_paiement = ?2 ", nativeQuery = true)
    Page<Paiement> searchUserInMontant(String refin, Double montant, Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and mode_paiement = ?2 ", nativeQuery = true)
    Page<Paiement> searchUserInMode(String refin, String mode,Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 and owner_paiement_id_user  = ?2", nativeQuery = true)
    Page<Paiement> searchUserInPayeur(String refin, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where ref_out_paiement = ?1 and montant_paiement = ?2 ", nativeQuery = true)
    Page<Paiement> searchUserOutMontant(String refin, Double montant, Pageable pageable);
    @Query(value = "select * from paiement where ref_out_paiement = ?1 and mode_paiement = ?2 ", nativeQuery = true)
    Page<Paiement> searchUserOutMode(String refin, String mode,Pageable pageable);
    @Query(value = "select * from paiement where ref_out_paiement = ?1 and owner_paiement_id_user  = ?2", nativeQuery = true)
    Page<Paiement> searchUserOutPayeur(String refin, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where montant_paiement = ?1 and mode_paiement = ?2 ", nativeQuery = true)
    Page<Paiement> searchUserMontantMode( Double montant, String mode, Pageable pageable);
    @Query(value = "select * from paiement where  montant_paiement = ?1 owner_paiement_id_user  = ?2", nativeQuery = true)
    Page<Paiement> searchUserMontantPayeur( Double montant,  Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where  mode_paiement = ?1 and owner_paiement_id_user  = ?2", nativeQuery = true)
    Page<Paiement> searchUserModePayeur(String mode, Long payeur, Pageable pageable);
    @Query(value = "select * from paiement where date_paiement = ?1 ", nativeQuery = true)
    Page<Paiement> searchUserDate(String date, Pageable pageable);
    @Query(value = "select * from paiement where ref_in_paiement = ?1 ", nativeQuery = true)
    Page<Paiement> searchUserIn(String refin, Pageable pageable);
    @Query(value = "select * from paiement where ref_out_paiement =?1 ", nativeQuery = true)
    Page<Paiement> searchUserOut(String refout, Pageable pageable);
    @Query(value = "select * from paiement where montant_paiement = ?1 ", nativeQuery = true)
    Page<Paiement> searchUserMontant(Double montant, Pageable pageable);
    @Query(value = "select * from paiement where mode_paiement = ?1 ", nativeQuery = true)
    Page<Paiement> searchUserMode(String mode,  Pageable pageable);
    @Query(value = "select * from paiement where owner_paiement_id_user  = ?1", nativeQuery = true)
    Page<Paiement> searchUserPayeur(Long payeur, Pageable pageable);
    @Query(value = "select * from paiement ", nativeQuery = true)
    Page<Paiement> searchAll(Pageable pageable);
}
