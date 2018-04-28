package com.blackhawk.vtt2.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Date;
import com.blackhawk.vtt2.model.Volunteer;
import com.blackhawk.vtt2.model.VolunteerCredential;

@Repository
public interface VolunteerRepository extends CrudRepository<Volunteer, Integer>{

    @Query("SELECT v FROM Volunteer v WHERE v.id = :id")
    public Volunteer getVolById(@Param("id")Integer id);

    @Query("SELECT v FROM Volunteer v WHERE v.firstName = :firstname AND v.lastName = :lastname")
    public Iterable<Volunteer> getByFirstLast(@Param("firstname") String firstName, @Param("lastname") String lastName);

    @Query("SELECT v FROM Volunteer v WHERE v.firstName = :firstname AND v.lastName = :lastname AND v.birthDate = :birthdate")
    public Iterable<Volunteer> getByFirstLastDob(@Param("firstname") String firstName, @Param("lastname") String lastName, @Param("birthdate") Date birthDate);

    @Query("SELECT v FROM Volunteer v WHERE v.volunteerCredential = :volunteerCredential")
    public Volunteer getByVolCred(@Param("volunteerCredential")VolunteerCredential volunteerCredential);

}

