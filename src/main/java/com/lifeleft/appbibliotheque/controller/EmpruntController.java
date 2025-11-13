package com.lifeleft.appbibliotheque.controller;

import com.lifeleft.appbibliotheque.entites.Emprunt;

import com.lifeleft.appbibliotheque.entites.Livre;
import com.lifeleft.appbibliotheque.repository.EmpruntRepository;
import com.lifeleft.appbibliotheque.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprunts")
public class EmpruntController {
    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private LivreRepository livreRepository; // ✅ injection du repository

    // ➕ Ajouter un emprunt
    @PostMapping("/add")
    public @ResponseBody Emprunt ajouterEmprunt(@RequestBody Emprunt emprunt) {

        Livre livre = livreRepository.findById(emprunt.getLivre().getId())
                .orElseThrow(() -> new RuntimeException("Livre introuvable !"));

        // Vérifier la disponibilité du livre
        if (!livre.isDisponible()) {
            throw new RuntimeException("Livre non disponible pour emprunt !");
        }

        // Marquer le livre comme non disponible
        livre.setDisponible(false);
        livreRepository.save(livre);

        // Sauvegarder l’emprunt
        emprunt.setLivre(livre);
        return empruntRepository.save(emprunt);
    }

    // 🔁 Retour de livre
    @PutMapping("/retour/{id}")
    public @ResponseBody Emprunt retournerLivre(@PathVariable Long id, @RequestBody Emprunt empruntDetails) {
        Emprunt emprunt = empruntRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprunt introuvable !"));

        // Mettre à jour la date de retour
        emprunt.setDateRetour(empruntDetails.getDateRetour());

        // Rendre le livre disponible
        Livre livre = emprunt.getLivre();
        livre.setDisponible(true);
        livreRepository.save(livre);

        return empruntRepository.save(emprunt);
    }

    // 📜 Lister tous les emprunts
    @GetMapping("/all")
    public @ResponseBody Iterable<Emprunt> getAllEmprunts() {
        return empruntRepository.findAll();
    }

    // 🔍 Consulter un emprunt
    @GetMapping("/get/{id}")
    public Emprunt getEmprunt(@PathVariable Long id) {
        return empruntRepository.findById(id).orElse(null);
    }

    // ✏️ Modifier un emprunt
    @PutMapping("/update/{id}")
    public Emprunt modifierEmprunt(@PathVariable Long id, @RequestBody Emprunt emprunt) {
        Emprunt ancien = empruntRepository.findById(id).orElse(null);
        if (ancien != null) {
            ancien.setDateEmprunt(emprunt.getDateEmprunt());
            ancien.setDateRetour(emprunt.getDateRetour());
            ancien.setLivre(emprunt.getLivre());
            ancien.setUtilisateur(emprunt.getUtilisateur());
            return empruntRepository.save(ancien);
        }
        return null;
    }

    // ❌ Supprimer un emprunt
    @DeleteMapping("/delete/{id}")
    public String supprimerEmprunt(@PathVariable Long id) {
        if (empruntRepository.existsById(id)) {
            empruntRepository.deleteById(id);
            return "Emprunt supprimé";
        }
        return "Emprunt introuvable";
    }

    // 🔍 Rechercher les emprunts d’un utilisateur
    @GetMapping("/recherche/utilisateur")
    public List<Emprunt> rechercherParUtilisateur(@RequestParam Long utilisateurId) {
        return empruntRepository.findByUtilisateurId(utilisateurId);
    }

    // 🔍 Rechercher les emprunts d’un livre
    @GetMapping("/recherche/livre")
    public List<Emprunt> rechercherParLivre(@RequestParam Long livreId) {
        return empruntRepository.findByLivreId(livreId);
    }
}