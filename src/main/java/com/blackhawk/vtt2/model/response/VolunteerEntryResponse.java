package com.blackhawk.vtt2.model.response;

import java.util.Date;

import com.blackhawk.vtt2.model.Volunteer;
import com.blackhawk.vtt2.model.VolunteerEntry;

public class VolunteerEntryResponse {

    private Integer volunteerEntryId;
    private Date entryDate;
    private Double timeSpent;
    private String notes;
    private Integer volunteerActivityId;
    private Integer volunteerId;


    public static VolunteerEntryResponse from(VolunteerEntry volunteerEntry) {
        VolunteerEntryResponse response = new VolunteerEntryResponse();
        response.setVolunteerEntryId(volunteerEntry.getVolunteerEntryId());
        response.setVolunteerActivityId(volunteerEntry.getVolunteerActivity().getVolunteerActivityId());
        response.setEntryDate(volunteerEntry.getEntryDate());
        response.setTimeSpent(volunteerEntry.getTimeSpent());
        response.setNotes(volunteerEntry.getEntryNotes());
        response.setVolunteerId(volunteerEntry.getVolunteer().getId());

        return response;
    }


    public Integer getVolunteerEntryId() {
        return volunteerEntryId;
    }
    public void setVolunteerEntryId(Integer volunteerEntryId) {
        this.volunteerEntryId = volunteerEntryId;
    }
    public Date getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    public Double getTimeSpent() {
        return timeSpent;
    }
    public void setTimeSpent(Double timeSpent) {
        this.timeSpent = timeSpent;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public Integer getVolunteerActivityId() {
        return volunteerActivityId;
    }
    public void setVolunteerActivityId(Integer volunteerActivityId) {
        this.volunteerActivityId = volunteerActivityId;
    }
    public Integer getVolunteerId() {
        return volunteerId;
    }
    public void setVolunteerId(Integer volunteerId) {
        this.volunteerId = volunteerId;
    }

}

