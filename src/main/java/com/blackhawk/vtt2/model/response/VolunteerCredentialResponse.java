package com.blackhawk.vtt2.model.response;

import com.blackhawk.vtt2.model.VolunteerCredential;

public class VolunteerCredentialResponse {

    private Integer volunteerCredentialId;
    private String username;
    private String password;
    private Integer volunteerId;

    public static VolunteerCredentialResponse from(VolunteerCredential volunteerCredential){
        VolunteerCredentialResponse response = new VolunteerCredentialResponse();
        response.setVolunteerCredentialId(volunteerCredential.getId());
        response.setUsername(volunteerCredential.getUsername());
        response.setPassword(volunteerCredential.getPassword());
        response.setVolunteerId(volunteerCredential.getVolunteer().getId());
        return response;

    }
    public Integer getVolunteerCredentialId() {
        return volunteerCredentialId;
    }
    public void setVolunteerCredentialId(Integer volunteerCredentialId) {
        this.volunteerCredentialId = volunteerCredentialId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getVolunteerId() {
        return volunteerId;
    }
    public void setVolunteerId(Integer volunteerId) {
        this.volunteerId = volunteerId;
    }
}
