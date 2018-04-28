package com.blackhawk.vtt2.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="Volunteer")
public class Volunteer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="volunteerid")
    private Integer id;

    @Column(name="firstname")
    private String firstName;

    @Column (name="lastname")
    private String lastName;

    @Column (name="phonenumber")
    private String phoneNumber;

    @Column (name="email")
    private String email;

    @Column (name="street")
    private String street;

    @Column (name="city")
    private String city;

    @Column (name="state")
    private String state;

    @Column (name="zip")
    private String zip;

    @Column (name="isadmin")
    private Boolean isAdmin;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Chicago")
    @Column (name="birthdate")
    private Date birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (name="startdate")
    private Date startDate;

    @Column (name="isactive")
    private Boolean isActive;

    @Column (name="organization")
    private String organization;

    @OneToOne(mappedBy = "volunteer", fetch = FetchType.EAGER)
    private VolunteerCredential volunteerCredential;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="volunteer")
    private Set<VolunteerEntry> volunteerEntry = new HashSet<>();


    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public String getOrganization() {
        return organization;
    }
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public Boolean getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public VolunteerCredential getVolunteerCredential() {
        return volunteerCredential;
    }
    public void setVolunteerCredential(VolunteerCredential volunteerCredential) {
        this.volunteerCredential = volunteerCredential;
    }
    public Set<VolunteerEntry> getVolunteerEntry() {
        return volunteerEntry;
    }
    public void setVolunteerEntry(Set<VolunteerEntry> volunteerEntry) {
        this.volunteerEntry = volunteerEntry;
    }

}

