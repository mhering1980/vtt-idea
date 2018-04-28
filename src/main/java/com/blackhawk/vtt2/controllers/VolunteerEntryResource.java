package com.blackhawk.vtt2.controllers;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.blackhawk.vtt2.dao.VolunteerActivityRepository;
import com.blackhawk.vtt2.dao.VolunteerEntryRepository;
import com.blackhawk.vtt2.dao.VolunteerRepository;
import com.blackhawk.vtt2.model.Volunteer;
import com.blackhawk.vtt2.model.VolunteerActivity;
import com.blackhawk.vtt2.model.VolunteerEntry;
import com.blackhawk.vtt2.model.response.VolunteerEntryResponse;
import com.blackhawk.vtt2.model.response.VolunteerResponse;

@RestController
public class VolunteerEntryResource {

    @Autowired
    private VolunteerEntryRepository volunteerEntryRepository;

    @Autowired
    private VolunteerActivityRepository volunteerActivityRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    //returns all volunteer entries
    @GetMapping("/volunteer-entry/")
    public List<VolunteerEntryResponse> retrieveAllVolunteerEntries(){
        Iterable<VolunteerEntry> vol =volunteerEntryRepository.findAll();
        List<VolunteerEntryResponse> entries = new ArrayList();
        Iterator<VolunteerEntry> iter = vol.iterator();
        while (iter.hasNext()){
            entries.add(VolunteerEntryResponse.from(iter.next()));
        }
        return entries;
    }

    //updates the db with an volunteer-entry edit****errors****
    @PutMapping("/volunteer-entry/{volunteerActivityId},{volunteerId}")
    public ResponseEntity<Object> editEntry(@RequestBody VolunteerEntry volEnt, @PathVariable(name="volunteerAcitivityId")Integer volunteerActivityId, @PathVariable(name="volunteerId") Integer volunteerId){
        VolunteerActivity volAct = volunteerActivityRepository.getActivity(volunteerActivityId);
        Volunteer vol = volunteerRepository.getVolById(volunteerId);
        if(volEnt == null){
            return ResponseEntity.status(401).build();
        }else{
            volEnt.setVolunteerActivity(volAct);
            volEnt.setVolunteer(vol);
            volunteerEntryRepository.save(volEnt);
            return ResponseEntity.status(200).build();
        }
    }
    //returns all entries for a given volunteerid
    @GetMapping("/volunteer-entry/by-volunteer/{volunteerId}")
    public List<VolunteerEntryResponse> getEntriesByVolunteerId(@PathVariable(name="volunteerId") Integer volunteerId ){
        Volunteer v = volunteerRepository.findById(volunteerId).get();
        Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByVolunteer(v);
        List<VolunteerEntryResponse> entries = new ArrayList();
        Iterator<VolunteerEntry> iter = vol.iterator();
        while (iter.hasNext()){
            entries.add(VolunteerEntryResponse.from(iter.next()));
        }
        return entries;
    }

    //Returns results of search parameters for a volunteer's entry search
    @GetMapping("/volunteer-entry/search")
    public List<VolunteerEntryResponse> searchEntries(@RequestParam(name="date", required=false) String date,@RequestParam(name="date2", required=false) String date2, @RequestParam(name="volunteerid", required=false) Integer volunteerid, @RequestParam(name="volunteeractivityid", required=false) Integer volunteeractivityid) throws ParseException{
        Date date1 = null;
        Date date2A=null;
        Integer actid = null;
        VolunteerActivity activity = null;
        if(date != null){
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }
        if(date2 != null){
            date2A = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
        }
        Volunteer volunteer = volunteerRepository.getVolById(volunteerid);
        if(volunteeractivityid !=null){
            activity = volunteerActivityRepository.findById(volunteeractivityid).get();
        }
        if(date != null && date2 != null & activity != null){
            Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByParameters(date1, date2A, volunteer, activity);
            List<VolunteerEntryResponse> entries = new ArrayList();
            Iterator<VolunteerEntry> iter = vol.iterator();
            while (iter.hasNext()){
                entries.add(VolunteerEntryResponse.from(iter.next()));
            }
            return entries;
        }else if(date != null && date2 != null){
            Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByDateRange(date1, date2A, volunteer);
            List<VolunteerEntryResponse> entries = new ArrayList();
            Iterator<VolunteerEntry> iter = vol.iterator();
            while (iter.hasNext()){
                entries.add(VolunteerEntryResponse.from(iter.next()));
            }
            return entries;
        }else if(date != null && actid != null){
            Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByDateActivity(date1, volunteer, activity);
            List<VolunteerEntryResponse> entries = new ArrayList();
            Iterator<VolunteerEntry> iter = vol.iterator();
            while (iter.hasNext()){
                entries.add(VolunteerEntryResponse.from(iter.next()));
            }
            return entries;
        }else if(date != null){
            Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByDate(date1, volunteer);
            List<VolunteerEntryResponse> entries = new ArrayList();
            Iterator<VolunteerEntry> iter = vol.iterator();
            while (iter.hasNext()){
                entries.add(VolunteerEntryResponse.from(iter.next()));
            }
            return entries;
        }else {
            Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByActivity(activity, volunteer);
            List<VolunteerEntryResponse> entries = new ArrayList();
            Iterator<VolunteerEntry> iter = vol.iterator();
            while (iter.hasNext()){
                entries.add(VolunteerEntryResponse.from(iter.next()));
            }
            return entries;
        }
    }
    //add an Entry ******HAving difficulty pulling a volunteerActivityId from the volunteerEntry*******
    @PostMapping("/volunteer-entry/add-entry/{volunteerId}")
    public ResponseEntity<Object> createVolunteerEntry(@RequestBody VolunteerEntry volunteerEntry, @PathVariable("volunteerId")Integer volunteerId){
        volunteerEntry.setVolunteer(volunteerRepository.getVolById(volunteerId));
        VolunteerActivity volAct = volunteerActivityRepository.getActId(volunteerEntry.getVolunteerActivity().getVolunteerActivityId());
        volunteerEntry.setVolunteerActivity(volAct);
        //volunteerEntry.setVolunteerActivity(volunteerActivityRepository.getActId(activity));
        // find next id
       // VolunteerEntry savedVolEnt = volunteerEntryRepository.save(volunteerEntry);
        //URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{volunteerEntryId}")
          //      .buildAndExpand((savedVolEnt).getVolunteerEntryId()).toUri();
        VolunteerEntry savedVolEnt = volunteerEntryRepository.save(volunteerEntry);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{volunteerEntryId}")
                .buildAndExpand(savedVolEnt.getVolunteerEntryId()).toUri();

        return ResponseEntity.created(location).build();
    }

}

