package com.blackhawk.vtt2.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blackhawk.vtt2.model.VolunteerActivity;
import com.blackhawk.vtt2.model.VolunteerEntry;

@Repository
public interface VolunteerActivityRepository extends CrudRepository<VolunteerActivity, Integer>{

    @Query("SELECT va FROM VolunteerActivity va WHERE va.isActive = true")
    public Iterable<VolunteerActivity> getActiveActivities(VolunteerActivity volunteerActivity);

    @Query("SELECT va FROM VolunteerActivity va WHERE va.volunteerActivityId = :volunteerActivityId")
    public Iterable<VolunteerActivity> getActivityById(@Param("volunteerActivityId")Integer volunteerActivityId);

    @Query("SELECT va FROM VolunteerActivity va WHERE va.volunteerActivityId = :volunteerActivityId")
    public VolunteerActivity getActivity(@Param("volunteerActivityId")Integer volunteerActivityId);

    /*@Query("SELECT va FROM VolunteerActivity va WHERE va.volunteerActivityId = :volunteerActivityId")
    public VolunteerActivity getActId(@Param("volunteerActivityId") Integer volunteerActivityId);*/
}
