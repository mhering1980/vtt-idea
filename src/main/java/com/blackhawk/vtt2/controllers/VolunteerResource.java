package com.blackhawk.vtt2.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blackhawk.vtt2.model.Volunteer;
import com.blackhawk.vtt2.model.response.VolunteerResponse;
import com.blackhawk.vtt2.dao.VolunteerRepository;

@RestController
public class VolunteerResource {

    @Autowired
    private VolunteerRepository volunteerRepository;


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
    //need to fix the whole return everything thing
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
    //bdate is subtracting 1 from the day when inserting into database.
    @PostMapping("/volunteer/")
    public ResponseEntity<Object> createVolunteer(@RequestBody Volunteer volunteer){
        Date date = volunteer.getBirthDate();
        volunteer.setStartDate(new Date());
        Volunteer savedVol = volunteerRepository.save(volunteer);
        // find next id
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedVol.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
//birthDate is entered as one day earlier than what was given
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

