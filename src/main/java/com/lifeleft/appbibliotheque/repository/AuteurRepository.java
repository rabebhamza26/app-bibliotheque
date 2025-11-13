package com.lifeleft.appbibliotheque.repository;

import com.lifeleft.appbibliotheque.entites.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuteurRepository extends JpaRepository<Auteur, Long> {
    // Recherche par nom ou prénom (insensible à la casse)
    List<Auteur> findByNomIgnoreCaseContaining(String nom);
    List<Auteur> findByPrenomIgnoreCaseContaining(String prenom);
}
