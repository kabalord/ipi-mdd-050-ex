package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;


// 10 - Ajouter ou supprimer un technicien dans l'équipe d'un manager

@RestController
@RequestMapping("/manager")

public class ManagerController {

    @Autowired
    private TechnicienRepository technicienRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @RequestMapping(value = "/{idManager}/equipe/{idTechnicien}/remove", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTechnicienFromEquipe(@PathVariable("idManager") Long idManager,
    @PathVariable("idTechnicien") Long idTechnicien){
        //Récuperer le technicien
        Technicien t = technicienRepository.findOne(idTechnicien);
        if(t == null){
            throw new EntityNotFoundException("Le technicen d'identifiant " + idTechnicien + "n'existe pas");
        }
        if (t.getManager() == null && !t.getManager().getId().equals(idManager)){
            throw new IllegalArgumentException("Le technicen d'identifiant " + idTechnicien + "n'a pas de manager ou n'a pas pour manager l'identifiant " + idManager + ".");
        }
        t.setManager(null);
        technicienRepository.save(t);


        //Récuperer le manager
        /*Manager m = managerRepository.findOneWithEquipeById(idManager);
        m.getEquipe().remove(t);
        managerRepository.save(m);*/
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{idManager}/equipe/{matricule}/add")
    public Technicien addTechniciens(@PathVariable Long idManager, @PathVariable String matricule) {
        Technicien t = technicienRepository.findByMatricule(matricule);
        Manager m = managerRepository.findOne(idManager);
        t.setManager(m);
        t = technicienRepository.save(t);

        return t;
    }
}
