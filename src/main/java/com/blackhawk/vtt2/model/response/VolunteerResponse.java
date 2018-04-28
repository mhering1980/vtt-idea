package com.blackhawk.vtt2.model.response;

import java.util.Date;

import com.blackhawk.vtt2.model.Volunteer;

public class VolunteerResponse {

    private Integer volunteerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private Boolean isAdmin;
    private Date startDate;
    private Boolean isActive;
    private String organization;
    private Date birthDate;
    private Integer volunteerCredentialId;

    public static VolunteerResponse from(Volunteer volunteer) {
        VolunteerResponse response = new VolunteerResponse();
        response.setVolunteerId(volunteer.getId());
        response.setFirstName(volunteer.getFirstName());
        response.setLastName(volunteer.getLastName());
        response.setBirthDate(volunteer.getBirthDate());
        response.setStartDate(volunteer.getStartDate());
        response.setPhoneNumber(volunteer.getPhoneNumber());
        response.setEmail(volunteer.getEmail());
        response.setStreet(volunteer.getStreet());
        response.setCity(volunteer.getCity());
        response.setState(volunteer.getState());
        response.setZipCode(volunteer.getZip());
        response.setIsAdmin(volunteer.getIsAdmin());
        response.setIsActive(volunteer.getIsActive());
        //response.setVolunteerCredentialId(volunteer.getVolunteerCredential().getId());
        return response;
    }

    public Integer getVolunteerId() {
        return volunteerId;
    }
    public void setVolunteerId(Integer volunteerId) {
        this.volunteerId = volunteerId;
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
    public String getStreet() {return street; }
    public void setStreet(String street) {this.street = street; }
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
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public Integer getVolunteerCredentialId() {
        return volunteerCredentialId;
    }
    public void setVolunteerCredentialId(Integer volunteerCredentialId) {
        this.volunteerCredentialId = volunteerCredentialId;
    }

}