package com.blackhawk.vtt2.model;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="volunteeractivity")
public class VolunteerActivity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="volunteeractivityid")
    private Integer volunteerActivityId;

    @Column(name="activitytype")
    private String activityType;

    @Column (name="isactive")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="volunteerActivity")
    private Set<VolunteerEntry> volunteerEntry = new HashSet<>();

    public Integer getVolunteerActivityId() {
        return volunteerActivityId;
    }

    public void setVolunteerActivityId(Integer volunteerActivityId) {
        this.volunteerActivityId = volunteerActivityId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<VolunteerEntry> getVolunteerEntry() {
        return volunteerEntry;
    }

    public void setVolunteerEntry(Set<VolunteerEntry> volunteerEntry) {
        this.volunteerEntry = volunteerEntry;
    }
}

