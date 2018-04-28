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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blackhawk.vtt2.model.Volunteer;
import com.blackhawk.vtt2.model.VolunteerActivity;
import com.blackhawk.vtt2.model.VolunteerCredential;
import com.blackhawk.vtt2.model.response.VolunteerActivityResponse;
import com.blackhawk.vtt2.model.response.VolunteerResponse;
import com.blackhawk.vtt2.dao.VolunteerActivityRepository;

@RestController
public class VolunteerActivityResource {

    @Autowired
    private VolunteerActivityRepository volunteerActivityRepository;

    @GetMapping("/volunteer-activity")
    public List<VolunteerActivityResponse> retrieveAllActivities(){
        Iterable<VolunteerActivity> vol =volunteerActivityRepository.findAll();
        List<VolunteerActivityResponse> activities = new ArrayList();
        Iterator<VolunteerActivity> iter = vol.iterator();
        while (iter.hasNext()){
            activities.add(VolunteerActivityResponse.from(iter.next()));
        }
        return activities;
    }
    //returns all active activities
    @GetMapping("/volunteer-activity/active")
    public List<VolunteerActivityResponse> retrieveActiveActivities(){
        Iterable<VolunteerActivity> activity = volunteerActivityRepository.getActiveActivities(null);
        List<VolunteerActivityResponse> activities = new ArrayList();
        Iterator<VolunteerActivity> iter = activity.iterator();
        while(iter.hasNext()){
            activities.add(VolunteerActivityResponse.from(iter.next()));
        }
        return activities;
    }

    @PostMapping("/volunteer-activity/add")
    public ResponseEntity<Object> createActivity(@RequestBody VolunteerActivity volunteerActivity){

        VolunteerActivity savedActivity = volunteerActivityRepository.save(volunteerActivity);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedActivity.getVolunteerActivityId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/volunteer-activity/edit")
    public ResponseEntity<Object> editActivity(@RequestBody VolunteerActivity volunteerActivity){
        Integer activityId = volunteerActivity.getVolunteerActivityId();
        Iterable<VolunteerActivity> volAct = volunteerActivityRepository.getActivityById(activityId);
        if (!volAct.iterator().hasNext()){
            return ResponseEntity.notFound().build();
        }
        VolunteerActivity activity = volAct.iterator().next();
        activity.setActivityType(volunteerActivity.getActivityType());
        activity.setIsActive(volunteerActivity.getIsActive());
        volunteerActivityRepository.save(activity);
        return ResponseEntity.ok().build();

    }



}

