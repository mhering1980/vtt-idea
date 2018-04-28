package com.blackhawk.vtt2.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blackhawk.vtt2.model.Volunteer;
import com.blackhawk.vtt2.model.VolunteerActivity;
import com.blackhawk.vtt2.model.VolunteerEntry;

@Repository
public interface VolunteerEntryRepository extends CrudRepository<VolunteerEntry, Integer> {
    //REturns entry for a volunteer based on the entry id
    @Query("SELECT ve FROM VolunteerEntry ve WHERE ve.volunteerEntryId = :volunteerEntryId")
    public VolunteerEntry getEntryByEntryId(@Param("volunteerEntryId") Integer volunteerEntryId);

    //Returns all entries based on a volunteer id
    @Query("SELECT ve FROM VolunteerEntry ve WHERE ve.volunteer = :volunteer")
    public Iterable<VolunteerEntry> getEntryByVolunteer(@Param("volunteer") Volunteer volunteer);

    //Returns entries based on activity type
    @Query("SELECT ve FROM VolunteerEntry ve WHERE ve.volunteerActivity =:volunteerActivity and ve.volunteer = :volunteer")
    public Iterable<VolunteerEntry> getEntryByActivity(@Param("volunteerActivity") VolunteerActivity volunteerActivity, @Param("volunteer") Volunteer volunteer);

    //Returns entries based on single date
    @Query("SELECT ve FROM VolunteerEntry ve WHERE ve.entryDate = :date and ve.volunteer = :volunteer")
    public Iterable<VolunteerEntry> getEntryByDate(@Param("date") Date date, @Param("volunteer") Volunteer volunteer);

    //Returns entries based on a single date and activity type
    @Query("SELECT ve FROM VolunteerEntry ve WHERE ve.entryDate = :date AND ve.volunteerActivity = :volunteerActivity AND ve.volunteer = :volunteer")
    public Iterable<VolunteerEntry> getEntryByDateActivity(@Param("date") Date date, @Param("volunteer") Volunteer volunteer, @Param("volunteerActivity") VolunteerActivity volunteerActivity);

    //returns entries based on a date range
    @Query("SELECT ve FROM VolunteerEntry ve WHERE ve.volunteer = :volunteer AND ve.entryDate BETWEEN :date AND :date2")
    public Iterable<VolunteerEntry> getEntryByDateRange(@Param("date") Date date, @Param("date2") Date date2, @Param("volunteer") Volunteer volunteer);

    //returns entries based on date range and activity type
    @Query("SELECT ve FROM VolunteerEntry ve WHERE ve.volunteer = :volunteer AND ve.volunteerActivity = :volunteerActivity AND ve.entryDate BETWEEN :date AND :date2")
    public Iterable<VolunteerEntry> getEntryByParameters(@Param("date") Date date, @Param("date2") Date date2, @Param("volunteer") Volunteer volunteer, @Param("volunteerActivity") VolunteerActivity volunteerActivity);

}
