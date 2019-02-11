package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 11 - Ajouter ou supprimer un manager à un technicien

@RestController
@RequestMapping("techniciens")

public class TechnicienController {

    @Autowired
    private TechnicienRepository technicienRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @RequestMapping(value = "/{idTechnicien}/manager/{matricule}/add")
    public Technicien addTechniciens(@PathVariable("idTechnicien") Long idTechnicien, @PathVariable String matricule) {
        Technicien t = technicienRepository.findOne(idTechnicien);

        Manager m = (Manager) employeRepository.findByMatricule(matricule);

        t.setManager(m);
        t = technicienRepository.save(t);

        return t;
    }
}
