package com.lifeleft.appbibliotheque.repository;

import com.lifeleft.appbibliotheque.entites.Emprunt;
import com.lifeleft.appbibliotheque.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    // Recherche par utilisateur ou livre
    List<Emprunt> findByUtilisateurId(Long utilisateurId);
    List<Emprunt> findByLivreId(Long livreId);
}