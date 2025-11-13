package com.lifeleft.appbibliotheque.repository;

import com.lifeleft.appbibliotheque.entites.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    // Méthode de recherche par titre (ignore la casse et cherche une partie du titre)
    @Query("SELECT l FROM Livre l WHERE LOWER(l.titre) LIKE LOWER(CONCAT('%', :titre, '%'))")
    List<Livre> rechercherParTitre(@Param("titre") String titre);

}