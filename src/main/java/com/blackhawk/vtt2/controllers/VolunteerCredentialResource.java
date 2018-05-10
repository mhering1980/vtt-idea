package com.blackhawk.vtt2.controllers;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.blackhawk.vtt2.util.FromToken;
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

    FromToken ft = new FromToken();

    //returns all volunteer credentials ***WORKS
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
    //returns a volunteer credential for specific id ***WORKS***
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
    //returns a 401 if the username exists in db and 200 if id does not***WORKS
    @GetMapping("/volunteer-credential/username/{username}")
    public ResponseEntity<Object> testUsername(@PathVariable String username){
        Iterable<VolunteerCredential> nameSearch = volunteerCredentialRepository.getByUsername(username);
        if(nameSearch.iterator().hasNext()) {
            return ResponseEntity.status(401).build(); // 401 = Username exists in the db
        }
        return ResponseEntity.ok().build(); // 200 = username can be assigned
    }
  /*  //login not needed with jwt tokenization classes
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


    //create a volunteer credential, ie username/password ***WORKS
    @PostMapping("/volunteer-credential/credential")
    public ResponseEntity<Object> createVolunteerCredential(@RequestBody VolunteerCredential volunteerCredential, @RequestParam(name="volId")Integer volId){
        volunteerCredential.setVolunteer(volunteerRepository.getVolById(volId));
        String password = volunteerCredential.getPassword();
        String hashedPassword = HashedPasswords.hashPassword(password);
        volunteerCredential.setPassword(hashedPassword);
        // find next id
        VolunteerCredential savedVolCred = volunteerCredentialRepository.save(volunteerCredential);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedVolCred.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    //update volunteer credential i.e. adminReset password and volunteer reset password *** WORKS***
    @PutMapping("/volunteer-credential/reset")
    public ResponseEntity<Object> updateVolunteerCredential(@RequestBody VolunteerCredential volunteerCredential, Principal principal){
        String username;
        if(volunteerCredential.getUsername() !=null) {
            username = volunteerCredential.getUsername();
        }else{
            Integer id = ft.getVolunteerId(principal);
            username = volunteerCredentialRepository.findByVolId(volunteerRepository.getVolById(id)).getUsername();
        }
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

}

