package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

// 1 - Compter le nombre d'employés

@RestController
@RequestMapping("/employes")

public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    @RequestMapping(method = RequestMethod.GET, value = "count")
    public Long countEmployes(){

        return employeRepository.count();
    }


// 2 - Afficher un employé
// Prèmiere manière de faire une exception dans le delete employé

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Employe findById (@PathVariable("id") Long id) throws EntityExistsException {
        Employe e = employeRepository.findOne(id);
        if (e == null){
            throw new EntityNotFoundException("L'employé d'identifiant " + id + " n'a pas été trouvé");
        }
        return e;
    }

// 3 - Recherche par matricule

    @RequestMapping(value = "", params = "matricule")
   public Employe findByMatricule(@RequestParam("matricule") String matricule){
        return employeRepository.findByMatricule(matricule);
    }

//  4 - Liste des employés

    @RequestMapping("")
    public Page<Employe> findAllPaging(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortProperty") String sortProperty,
            @RequestParam("sortDirection") String sortDirection){
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        return employeRepository.findAll(pageRequest);
    }

// 5 - Création d'un Commercial

    @RequestMapping(
            method = RequestMethod.POST, //Méthode HTTP : GET/POST/PATCH/PUT/DELETE
            consumes = "application/json", //Type MIME des données passées avec la requête : JSON, XML, Texte...
            produces = "application/json" //Type MIME des données fournies dans la réponse
    )
    public Employe createEmploye(@RequestBody Employe employe) {
        return employeRepository.save(employe);
    }

// 6 - Modification d'un Commercial

    @RequestMapping(
            method = RequestMethod.PUT, //Méthode HTTP : GET/POST/PATCH/PUT/DELETE
            consumes = "application/json", //Type MIME des données passées avec la requête : JSON, XML, Texte...
            produces = "application/json", //Type MIME des données fournies dans la réponse
            value = "/{id}"
    )
    public Employe modifyEmploye(
            @PathVariable("id") Long id,
            @RequestBody Employe employe) {
        return employeRepository.save(employe);
    }

// 7 - Suppression d'un Commercial
// Dèuxime manière de faire une exception dans le delete employé

    @RequestMapping(
            method = RequestMethod.DELETE, //Méthode HTTP : GET/POST/PATCH/PUT/DELETE
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmploye(@PathVariable("id") Long id) {
        if (!employeRepository.exists(id)){
            throw new EntityNotFoundException("L'employé d'identifiant " + id + " n'a pas été trouvé");
        }
        employeRepository.delete(id);
    }

    /*Troisième maniére de faire l'exception
        try {
            employeRepository.delete(id);
        }catch (Exception e) {
            throw new EntityNotFoundException("L'employé d'identifiant " + id + " n'a pas été trouvé");
        }

    */

}
