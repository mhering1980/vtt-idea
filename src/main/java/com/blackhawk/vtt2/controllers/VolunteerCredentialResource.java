package com.blackhawk.vtt2.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blackhawk.vtt2.util.HashedPasswords;
import com.blackhawk.vtt2.model.Volunteer;
import com.blackhawk.vtt2.model.VolunteerCredential;
import com.blackhawk.vtt2.model.VolunteerEntry;
import com.blackhawk.vtt2.model.response.VolunteerCredentialResponse;
import com.blackhawk.vtt2.model.response.VolunteerEntryResponse;
import com.blackhawk.vtt2.dao.VolunteerCredentialRepository;
import com.blackhawk.vtt2.dao.VolunteerRepository;

@RestController
public class VolunteerCredentialResource {

    @Autowired
    private VolunteerCredentialRepository volunteerCredentialRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    //returns all volunteer credentials
    @GetMapping("/volunteer-credential")
    public List<VolunteerCredentialResponse> retrieveAllVolunteerCredentials(){
        Iterable<VolunteerCredential> vol = volunteerCredentialRepository.findAll();
        List<VolunteerCredentialResponse> entries = new ArrayList();
        Iterator<VolunteerCredential> iter = vol.iterator();
        while (iter.hasNext()){
            entries.add(VolunteerCredentialResponse.from(iter.next()));
        }
        return entries;
    }
    //returns a volunteer credential for specific id
    @GetMapping("/volunteer-credential/{id}")
    public List<VolunteerCredentialResponse> retrieveUser(@PathVariable Integer id){
        Iterable<VolunteerCredential> volCred = volunteerCredentialRepository.getById(id);
        if(!volCred.iterator().hasNext()){
            return null;
        }
        List<VolunteerCredentialResponse> entries = new ArrayList();
        Iterator<VolunteerCredential> iter = volCred.iterator();
        entries.add(VolunteerCredentialResponse.from(iter.next()));

        return entries;
    }
    @GetMapping("/volunteer-credential/username")
    public ResponseEntity<Object> testUsername(@RequestParam(name="username") String username){
        Iterable<VolunteerCredential> nameSearch = volunteerCredentialRepository.getByUsername(username);
        if(nameSearch.iterator().hasNext()) {
            return ResponseEntity.status(401).build(); // 401 = Username exists in the db
        }
        return ResponseEntity.ok().build(); // 200 = username can be assigned
    }
  /*  //login
    @PostMapping("/volunteer-credential/login")
    public ResponseEntity<Object>login(@RequestBody VolunteerCredential volunteerCredential){
        String password = volunteerCredential.getPassword();
        String username = volunteerCredential.getUsername();
        String hashedPassword = HashedPasswords.hashPassword(password);
        Iterable<VolunteerCredential> credentials = volunteerCredentialRepository.getByUsernameAndPassword(username, hashedPassword);
        if(credentials.iterator().hasNext()){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(401).build();
    }*/


    //create a volunteer credential, ie username/password
    @PostMapping("/volunteer-credential/credential")
    public ResponseEntity<Object> createVolunteerCredential(@RequestBody VolunteerCredential volunteerCredential, @RequestParam(name="volunteerId")Integer volunteerId){
        volunteerCredential.setVolunteer(volunteerRepository.getVolById(volunteerId));
        String password = volunteerCredential.getPassword();
        String hashedPassword = HashedPasswords.hashPassword(password);
        volunteerCredential.setPassword(hashedPassword);
        // find next id
        VolunteerCredential savedVolCred = volunteerCredentialRepository.save(volunteerCredential);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedVolCred.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    //update volunteer credential i.e. adminReset password
    @PutMapping("/volunteer-credential/reset")
    public ResponseEntity<Object> updateVolunteerCredential(@RequestBody VolunteerCredential volunteerCredential){
        String username = volunteerCredential.getUsername();
        Iterable<VolunteerCredential> volCred = volunteerCredentialRepository.getByUsername(username);
        if (!volCred.iterator().hasNext()){
            return ResponseEntity.notFound().build();
        }
        String password = volunteerCredential.getPassword();
        VolunteerCredential vc = volCred.iterator().next();
        String hashedPassword = HashedPasswords.hashPassword(password);
        vc.setPassword(hashedPassword);
        volunteerCredentialRepository.save(vc);
        return ResponseEntity.ok().build();
    }
    //can I send 2 passwords in the same requestbody?
    //reset for volunteer page
    @PutMapping("/volunteer-credential/vol-reset")
    public ResponseEntity<Object> updateVolunteerPassword(@RequestBody VolunteerCredential volunteerCredential){
        String username = volunteerCredential.getUsername();
        String password = volunteerCredential.getPassword();
        String hashedPassword = HashedPasswords.hashPassword(password);
        Iterable<VolunteerCredential> credentials = volunteerCredentialRepository.getByUsernameAndPassword(username, hashedPassword);
        if(!credentials.iterator().hasNext()){
            return ResponseEntity.status(401).build();
        }else{
            VolunteerCredential vc = credentials.iterator().next();
            return ResponseEntity.status(200).build();
        }
    }
}

