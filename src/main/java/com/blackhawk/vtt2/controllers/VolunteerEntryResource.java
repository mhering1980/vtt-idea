package com.blackhawk.vtt2.controllers;

import java.net.URI;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    FromToken ft = new FromToken();

    //returns all volunteer entries *** WORKS
    @GetMapping("/volunteer-entry")
    public List<VolunteerEntryResponse> retrieveAllVolunteerEntries(){
        Iterable<VolunteerEntry> vol =volunteerEntryRepository.findAll();
        List<VolunteerEntryResponse> entries = new ArrayList();
        Iterator<VolunteerEntry> iter = vol.iterator();
        while (iter.hasNext()){
            entries.add(VolunteerEntryResponse.from(iter.next()));
        }
        return entries;
    }

    //updates the db with an volunteer-entry edit--user/admin****WORKS
    @PutMapping("/volunteer-entry")
    public ResponseEntity<Object> editEntry(@RequestBody VolunteerEntry volEnt, @RequestParam(name="actId")Integer actId, @RequestParam(name="volId", required = false)Integer volId,Principal principal){
        Volunteer vol;
        if(volId==null){
            Integer volunteerId = ft.getVolunteerId(principal);
           vol = volunteerRepository.getVolById(volunteerId);
        }else{
            vol = volunteerRepository.getVolById(volId);
        }
        VolunteerActivity volAct = volunteerActivityRepository.getActivity(actId);

        if(volEnt == null){
            return ResponseEntity.status(401).build();
        }else{
            volEnt.setVolunteerActivity(volAct);
            volEnt.setVolunteer(vol);
            volunteerEntryRepository.save(volEnt);
            return ResponseEntity.status(200).build();
        }
    }
    //returns all entries for a given volunteerid admin ****Works
    @GetMapping("/volunteer-entry/by-vol/{volId}")
    public List<VolunteerEntryResponse> getEntriesByVolunteerId(@PathVariable(name="volId") Integer volId ){
        Volunteer v = volunteerRepository.findById(volId).get();
        Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByVolunteer(v);
        List<VolunteerEntryResponse> entries = new ArrayList();
        Iterator<VolunteerEntry> iter = vol.iterator();
        while (iter.hasNext()){
            entries.add(VolunteerEntryResponse.from(iter.next()));
        }
        return entries;
    }

    //Returns results of search parameters for a volunteer's entry search***WORKS
    @GetMapping("/volunteer-entry/search")
    public List<VolunteerEntryResponse> searchEntries(@RequestParam(name="date", required=false) String date,@RequestParam(name="date2", required=false) String date2, Principal principal, @RequestParam(name="actid", required=false) Integer actid) throws ParseException {
        Date date1 = null;
        Date date2A = null;
        VolunteerActivity activity = null;
        if (date != null) {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }
        if (date2 != null) {
            date2A = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
        }
        Integer volunteerId = ft.getVolunteerId(principal);
        Volunteer volunteer = volunteerRepository.getVolById(volunteerId);
        if (actid != null) {
            activity = volunteerActivityRepository.findById(actid).get();
        }
        if (date != null && date2 != null & activity != null) {
            Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByParameters(date1, date2A, volunteer, activity);
            List<VolunteerEntryResponse> entries = new ArrayList();
            Iterator<VolunteerEntry> iter = vol.iterator();
            while (iter.hasNext()) {
                entries.add(VolunteerEntryResponse.from(iter.next()));
            }
            return entries;
        } else if (date != null && date2 != null) {
            Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByDateRange(date1, date2A, volunteer);
            List<VolunteerEntryResponse> entries = new ArrayList();
            Iterator<VolunteerEntry> iter = vol.iterator();
            while (iter.hasNext()) {
                entries.add(VolunteerEntryResponse.from(iter.next()));
            }
            return entries;
        } else if (date != null && activity != null) {
            Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByDateActivity(date1, volunteer, activity);
            List<VolunteerEntryResponse> entries = new ArrayList();
            Iterator<VolunteerEntry> iter = vol.iterator();
            while (iter.hasNext()) {
                entries.add(VolunteerEntryResponse.from(iter.next()));
            }
            return entries;
        } else if (date != null) {
            Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByDate(date1, volunteer);
            List<VolunteerEntryResponse> entries = new ArrayList();
            Iterator<VolunteerEntry> iter = vol.iterator();
            while (iter.hasNext()) {
                entries.add(VolunteerEntryResponse.from(iter.next()));
            }
            return entries;
        } else if (activity != null) {
            Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByActivity(activity, volunteer);
            List<VolunteerEntryResponse> entries = new ArrayList();
            Iterator<VolunteerEntry> iter = vol.iterator();
            while (iter.hasNext()) {
                entries.add(VolunteerEntryResponse.from(iter.next()));
            }
            return entries;
        } else {
            Iterable<VolunteerEntry> vol = volunteerEntryRepository.getEntryByVolunteer(volunteer);
            List<VolunteerEntryResponse> entries = new ArrayList();
            Iterator<VolunteerEntry> iter = vol.iterator();
            while (iter.hasNext()) {
                entries.add(VolunteerEntryResponse.from(iter.next()));
            }
            return entries;
        }
    }
    //add an Entry volunteer/admin******WORKS
    @PostMapping("/volunteer-entry/add-entry/")
    public ResponseEntity<Object> createVolunteerEntryAdmin(@RequestBody VolunteerEntry volunteerEntry, @RequestParam(name="volId",required=false) Integer volId, @RequestParam(name="actId") Integer actId, Principal principal){
        if(volId==null){
            volId=ft.getVolunteerId(principal);
        }
        VolunteerActivity volAct = volunteerActivityRepository.getActivity(actId);
        Volunteer vol = volunteerRepository.getVolById(volId);
        volunteerEntry.setVolunteerActivity(volAct);
        volunteerEntry.setVolunteer(vol);
        VolunteerEntry savedVolEnt = volunteerEntryRepository.save(volunteerEntry);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{volunteerEntryId}")
                .buildAndExpand(savedVolEnt.getVolunteerEntryId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //returns hours for 1 volunteer.  If no id is passed, returns the logged in volunteer's, else returns volunteer based on id.***works
    @GetMapping("/volunteer-entry/total-hours")
    public Double totalHours(@RequestParam(name="vID", required = false) Integer id, Principal principal, @RequestParam(name="strdate1", required = false) String strDate1,
                         @RequestParam(name="strdate2", required = false) String strDate2) throws ParseException {
        Date date1 = null, date2 = null;
        if(id == null){
            id = ft.getVolunteerId(principal);
        }
        if(strDate1 != null) { //IF THERE IS AT LEAST 1 DATE
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(strDate1);

        if(strDate2 != null) { //IF THERE IS 2 DATES
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(strDate2);
            return volunteerEntryRepository.totalTimeDates(id,date1,date2); //RETURNS TOTAL TIME WITHIN DATE RANGE

            } else { return volunteerEntryRepository.totalTimeDate(id,date1); } //RETURNS TOTAL TIME ON ONE DATE

        } else { return volunteerEntryRepository.totalTime(id); } //RETURNS TOTAL TIME
    }

    @GetMapping("/volunteer-entry/total-hours-admin-all/")
    public ResponseEntity<Object> retrieveUsersHours(@RequestParam(name="strdate1", required = false) String strDate1,
                                              @RequestParam(name="strdate2", required = false) String strDate2, Principal principal) throws ParseException {
        if(ft.getAdminStatus(principal)==true) {
            Date date1 = null, date2 = null;List list;

            if (strDate1 != null) { //IF THERE IS AT LEAST 1 DATE
                date1 = new SimpleDateFormat("yyyy-MM-dd").parse(strDate1);

                if (strDate2 != null) { //IF THERE IS 2 DATES
                    date2 = new SimpleDateFormat("yyyy-MM-dd").parse(strDate2);
                    list = volunteerEntryRepository.allTotalTimeDates(date1, date2); //RETURNS TOTAL TIME WITHIN DATE RANGE
                    return  ResponseEntity.ok(list);

                } else {
                    list = volunteerEntryRepository.allTotalTimeDate(date1);
                    return  ResponseEntity.ok(list);
                } //RETURNS TOTAL TIME ON ONE DATE

            } else {
                list = volunteerEntryRepository.allTotalTime();
                return ResponseEntity.ok(list);
            } //RETURNS TOTAL TIME
        }   else
            return ResponseEntity.status(401).build();
    }

}

