package com.lifeleft.appbibliotheque.controller;

import com.lifeleft.appbibliotheque.entites.Utilisateur;

import com.lifeleft.appbibliotheque.repository.UtilisateurRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/add")
    public @ResponseBody Utilisateur ajouterUtilisateur(@RequestBody Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
        return utilisateur;
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @GetMapping("get/{id}")
    public Utilisateur getUtilisateur(@PathVariable Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    @PutMapping("update/{id}")
    public Utilisateur modifierUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        Utilisateur ancien = utilisateurRepository.findById(id).orElse(null);
        if (ancien != null) {
            ancien.setNom(utilisateur.getNom());
            ancien.setEmail(utilisateur.getEmail());
            return utilisateurRepository.save(ancien);
        }
        return null;
    }

    @DeleteMapping("delete/{id}")
    public String supprimerUtilisateur(@PathVariable Long id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return "Utilisateur supprimé";
        }
        return "Utilisateur introuvable";
    }

    @GetMapping("/recherche")
    public List<Utilisateur> rechercherUtilisateurs(@RequestParam String nom) {
        return utilisateurRepository.findByNomIgnoreCaseContaining(nom);
    }
}