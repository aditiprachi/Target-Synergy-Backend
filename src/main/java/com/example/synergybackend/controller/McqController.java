package com.example.synergybackend.controller;


import com.example.synergybackend.model.Choice;
import com.example.synergybackend.model.Mcq;
import com.example.synergybackend.repository.MCQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "https://targetsynergy.herokuapp.com/")
@RestController
public class McqController {
    @Autowired
    private MCQRepository mcqRepo;

    @GetMapping("/MCQ")
    public List<Mcq> getAllMcq(){
        return mcqRepo.findAll();
    }
    @GetMapping("/MCQ/{id}")
    public Mcq getMcqById(@PathVariable("id") String id){
        return mcqRepo.findById(id).get();
    }

    @PostMapping("/MCQ")
    public String saveMcq(@RequestBody Mcq quest) {
        System.out.println(quest.getChoices());
        Mcq mcq=new Mcq();
        mcq.setQuestion(String.valueOf(quest.getQuestion()));
        mcq.setChoices( quest.getChoices());
        mcq.setGoogleId(quest.getGoogleId());
//        System.out.println(mcq.getChoices());
        Mcq savedPoll = mcqRepo.save(mcq);
        String url = "/MCQ/" + savedPoll.getId();
        return url;
    }
    @PutMapping("/MCQ/{id}")
    public Mcq updateMcq(@PathVariable("id") String id,@RequestBody Mcq quest) {
        Mcq mcq=mcqRepo.findById(id).get();
//        System.out.println(mcq.getChoices());
        mcq.setChoices(quest.getChoices());
        System.out.println(mcq.getChoices());
        Mcq savedPoll = mcqRepo.save(mcq);
        return savedPoll;
    }

}
