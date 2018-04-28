package com.blackhawk.vtt2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name="volunteerentry")
public class VolunteerEntry {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name= "volunteerentryid")
    private Integer volunteerEntryId;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Chicago")
    @Column (name="entrydate")
    private Date entryDate;

    @Column (name="timespent")
    private Double timeSpent;

    @Column (name="entrynotes")
    private String entryNotes;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name="volunteerid")//, nullable = false
    private Volunteer volunteer;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn (name="volunteeractivityid")//, nullable = false
    private VolunteerActivity volunteerActivity;

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

    public String getEntryNotes() {
        return entryNotes;
    }

    public void setEntryNotes(String entryNotes) {
        this.entryNotes = entryNotes;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public VolunteerActivity getVolunteerActivity() {
        return volunteerActivity;
    }

    public void setVolunteerActivity(VolunteerActivity volunteerActivity) {
        this.volunteerActivity = volunteerActivity;
    }

}
