package com.lifeleft.appbibliotheque.controller;

import com.lifeleft.appbibliotheque.entites.Auteur;

import com.lifeleft.appbibliotheque.repository.AuteurRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



    @RestController
    @RequestMapping("/auteurs")
    public class AuteurController {

        @Autowired
        private AuteurRepository auteurRepository;

        @PostMapping("/add")
        public @ResponseBody Auteur ajouterAuteur(@RequestBody Auteur auteur) {
            auteurRepository.save(auteur);
            return auteur;
        }

        @GetMapping("/all")
        public @ResponseBody Iterable<Auteur> getAllAuteurs() {
            return auteurRepository.findAll();
        }

        @GetMapping("get/{id}")
        public Auteur getAuteur(@PathVariable Long id) {
            return auteurRepository.findById(id).orElse(null);
        }

        @PutMapping("update/{id}")
        public Auteur modifierAuteur(@PathVariable Long id, @RequestBody Auteur auteur) {
            Auteur ancien = auteurRepository.findById(id).orElse(null);
            if (ancien != null) {
                ancien.setNom(auteur.getNom());
                ancien.setPrenom(auteur.getPrenom());
                return auteurRepository.save(ancien);
            }
            return null;
        }

        @DeleteMapping("delete/{id}")
        public String supprimerAuteur(@PathVariable Long id) {
            if (auteurRepository.existsById(id)) {
                auteurRepository.deleteById(id);
                return "Auteur supprimé";
            }
            return "Auteur introuvable";
        }

        @GetMapping("/recherche")
        public List<Auteur> rechercherAuteurs(@RequestParam String nom) {
            return auteurRepository.findByNomIgnoreCaseContaining(nom);
        }
    }

