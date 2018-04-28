package com.blackhawk.vtt2.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.blackhawk.vtt2.model.Volunteer;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="volunteercredential")
public class VolunteerCredential implements Serializable {
    private static final long serialVersionUID = -6477193473950231620L;

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vcredentialid")
    private Integer id;

    @Column(name="vusername")
    private String username;

    @Column(name="vpassword")
    private String password;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "volunteerid")
    private Volunteer volunteer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }
}
