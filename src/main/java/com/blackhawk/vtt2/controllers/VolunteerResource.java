package com.blackhawk.vtt2.controllers;

import java.net.URI;
import java.security.Principal;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blackhawk.vtt2.model.Volunteer;
import com.blackhawk.vtt2.model.response.VolunteerResponse;
import com.blackhawk.vtt2.dao.VolunteerRepository;
import com.blackhawk.vtt2.util.FromToken;


@RestController
public class VolunteerResource {

    @Autowired
    private VolunteerRepository volunteerRepository;

    FromToken ft = new FromToken();

    //returns the volunteer information for the person logged in.  *******WORKS
    @GetMapping("/volunteer/me")
    public List<VolunteerResponse> retrieveUser(Principal principal){
        return retrieveUser(ft.getVolunteerId(principal));
    }
    //returns volunteer information based on a volunteerId  *****WORKS******
    @GetMapping("/volunteer/{id}")
    public List<VolunteerResponse> retrieveUser(@PathVariable Integer id){
        Optional<Volunteer> vol = volunteerRepository.findById(id);
        List<VolunteerResponse> volunteer = new ArrayList();
        if(!vol.isPresent()){
            return null;
        }
        volunteer.add(VolunteerResponse.from(vol.get()));
        return volunteer;
    }
    //returns all volunteers ****WORKS
    @GetMapping("/volunteer")
    public List<VolunteerResponse> retrieveAllVolunteers(){
        Iterable<Volunteer> vol =volunteerRepository.findAll();
        List<VolunteerResponse> volunteers = new ArrayList();
        Iterator<Volunteer> iter = vol.iterator();
        while (iter.hasNext()){
            volunteers.add(VolunteerResponse.from(iter.next()));
        }
        return volunteers;
    }
    //****works
    //admin method returns a volunteer based on first and last name required, dob optional
    @GetMapping("/volunteer/search-name")
    public List<VolunteerResponse> searchNameDate(@RequestParam("firstName")String firstName, @RequestParam("lastName" ) String lastName,
                                              @RequestParam(name="birthDate", required = false) String birthDate)throws ParseException{
        Date bdate = null;
        if(birthDate != null) {
           bdate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
            Iterable<Volunteer> vol = volunteerRepository.getByFirstLastDob(firstName,lastName,bdate);
            List<VolunteerResponse> volunteer = new ArrayList();
            Iterator<Volunteer> iter = vol.iterator();
            while(iter.hasNext()){
                volunteer.add(VolunteerResponse.from(iter.next()));
            }
            return volunteer;
        } else {
            Iterable<Volunteer> vol = volunteerRepository.getByFirstLast(firstName,lastName);
            List<VolunteerResponse> volunteer = new ArrayList();
            Iterator<Volunteer> iter = vol.iterator();
            while(iter.hasNext()){
                volunteer.add(VolunteerResponse.from(iter.next()));
            }
            return volunteer;
        }
    }
    //adds a new volunteer to the database.  ****WORKS***
    @PostMapping("/volunteer")
    public ResponseEntity<Object> createVolunteer(@RequestBody Volunteer volunteer){
        Date date = volunteer.getBirthDate();
        volunteer.setStartDate(new Date());
        Volunteer savedVol = volunteerRepository.save(volunteer);
        // find next id
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedVol.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
    //admin method updates a volunteer in the database ***** WORKS
    @PutMapping("/volunteer/{id}")
    public ResponseEntity<Object> updateVolunteer(@RequestBody Volunteer volunteer, @PathVariable Integer id){
        Optional<Volunteer> vol = volunteerRepository.findById(id);

        if (!vol.isPresent()){
            return ResponseEntity.notFound().build();
        }
        volunteer.setId(id);
        volunteerRepository.save(volunteer);
        return ResponseEntity.ok().build();
    }

}

