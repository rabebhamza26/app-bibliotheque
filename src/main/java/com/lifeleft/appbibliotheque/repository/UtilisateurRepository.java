package com.lifeleft.appbibliotheque.repository;

import com.lifeleft.appbibliotheque.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    // Recherche par nom ou email (insensible à la casse)
    List<Utilisateur> findByNomIgnoreCaseContaining(String nom);
    List<Utilisateur> findByEmailIgnoreCaseContaining(String email);
}
