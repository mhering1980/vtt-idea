package com.blackhawk.vtt2.dao;

import java.util.List;
import java.util.Optional;

import com.blackhawk.vtt2.model.Volunteer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blackhawk.vtt2.model.VolunteerCredential;

@Repository
public interface VolunteerCredentialRepository extends CrudRepository<VolunteerCredential, Integer> {

    @Query("SELECT vc FROM VolunteerCredential vc WHERE vc.username = :username AND vc.password = :password")
    public Iterable<VolunteerCredential> getByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("SELECT vc FROM VolunteerCredential vc WHERE vc.username = :username")
    public Iterable<VolunteerCredential> getByUsername(@Param("username") String username);

    @Query("SELECT vc FROM VolunteerCredential vc WHERE vc.id = :id")
    public Iterable<VolunteerCredential> getById(@Param("id")Integer id);

    @Query("SELECT vc FROM VolunteerCredential vc WHERE vc.username = :username")
    public VolunteerCredential findByUsername(@Param("username") String username);

    @Query("SELECT vc FROM VolunteerCredential vc WHERE vc.volunteer = :volunteer")
    public VolunteerCredential findByVolId(@Param("volunteer") Volunteer volunteer);

   /* @Query("SELECT vc FROM VolunteerCredential vc WHERE vc.username = :volunteer")
    public VolunteerCredential getVolByCred(@Param("username") String username);

    @Query("SELECT vc.volunteerId FROM VolunteerCredential vc WHERE vc.username = :username")
    public Integer getVolId(@Param("username") String username);*/

   /* @Query("SELECT vc FROM VolunteerCredential vc WHERE vc.username = :username")
    public Iterable<VolunteerCredential> checkUsername(@Param("username") String username);*/
}
