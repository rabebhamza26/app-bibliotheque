package com.lifeleft.appbibliotheque.controller;

import com.lifeleft.appbibliotheque.entites.Livre;
import com.lifeleft.appbibliotheque.repository.LivreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livres")
public class LivreController {

    @Autowired
    private LivreRepository livreRepository;


    @PostMapping("/add")
    public @ResponseBody  Livre ajouterLivre( @RequestBody Livre livre) {
        livreRepository.save(livre);
        return livre;
    }


    @GetMapping("/all")
    public @ResponseBody Iterable <Livre> getallLivres() {
        return  livreRepository.findAll();
    }

    @GetMapping("get/{id}")
    public Livre getLivre(@PathVariable Long id) {

        return  livreRepository.findById(id).orElse(null);
    }



    @PutMapping("update/{id}")
    public Livre modifierLivre(@PathVariable Long id,  @RequestBody Livre livre) {
        Livre ancien = new Livre();
        ancien= livreRepository.findById(id).get();
        if(ancien != null){
            ancien.setTitre(livre.getTitre());
            ancien.setIsbn(livre.getIsbn());
            ancien.setDisponible(livre.isDisponible());
            return livreRepository.save(ancien);
        } else

            return null;

    }

    @DeleteMapping("delete/{id}")
    public String supprimerLivre(@PathVariable Long id) {

        if (livreRepository.existsById(id)) {
            livreRepository.deleteById(id);
            return "livre supprimé";
        }
        return "livre introuvable";


    }

    @GetMapping("/recherche")
    public List<Livre> rechercherLivres(@RequestParam String titre) {
        return livreRepository.rechercherParTitre(titre);
    }

}