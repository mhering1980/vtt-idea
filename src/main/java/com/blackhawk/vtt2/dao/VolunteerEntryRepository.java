package com.blackhawk.vtt2.dao;

import java.util.ArrayList;
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
//sent 4/30 from ethan, needs to be tested
    //returns total time volunteered for a single volunteer
    @Query(value = "SELECT SUM(ve.TimeSpent) as all_time FROM VolunteerEntry ve WHERE ve.Volunteerid = :volunteerid", nativeQuery = true)
    public Double totalTime(@Param("volunteerid") Integer volunteerid);

    //returns time volunteered for a single volunteer on date specified
    @Query(value = "SELECT SUM(ve.TimeSpent) as all_time FROM VolunteerEntry ve WHERE ve.Volunteerid = :volunteerid AND ve.entryDate = :date1", nativeQuery = true)
    public Double totalTimeDate(@Param("volunteerid") Integer volunteerid, @Param("date1") Date date1);

    //returns total time for a volunteer in a date range
    @Query(value = "SELECT SUM(ve.TimeSpent) as all_time FROM VolunteerEntry ve WHERE ve.VolunteerID = :volunteerid AND ve.entryDate BETWEEN :date1 AND :date2", nativeQuery = true)
    public Double totalTimeDates(@Param("volunteerid") Integer volunteerid, @Param("date1") Date date1, @Param("date2") Date date2);

    //QUERY TO RETURN CONCAT OF FIRST NAME LAST NAME AND TOTAL HOURS FOR ALL VOLUNTEERS
    @Query(value = "SELECT concat(v.firstName,' ',v.lastName,' Total Hours: ', SUM(ve.TimeSpent)) as all_time FROM Volunteer v INNER JOIN VolunteerEntry ve ON v.VolunteerID = ve.VolunteerID GROUP BY v.VolunteerID", nativeQuery = true)
    public List<ArrayList> allTotalTime();

    //QUERY TO RETURN TIME VOLUNTEERED FOR ALL VOLUNTEERS, FOR A SINGLE DATE (1 DATE GETS PASSED)
    @Query(value =  "SELECT concat(v.firstName,' ',v.lastName,' Total Hours: ', SUM(ve.TimeSpent),' on ',ve.EntryDate) as all_time FROM Volunteer v INNER JOIN VolunteerEntry ve ON v.VolunteerID = ve.VolunteerID WHERE ve.EntryDate = :date1 GROUP BY v.VolunteerID", nativeQuery = true)
    public List<ArrayList> allTotalTimeDate(@Param("date1") Date date1);

    //QUERY TO RETURN TIME VOLUNTEERED FOR ALL VOLUNTEERS, FOR A DATE RANGE INCLUSIVE (2 DATES GETS PASSED)  , ' from ', :date1, ' to ', :date2
    @Query(value =  "SELECT concat(v.firstName, ' ', v.lastName, ' Total Hours: ', SUM(ve.TimeSpent)) as all_time FROM Volunteer v INNER JOIN VolunteerEntry ve ON v.VolunteerID = ve.VolunteerID WHERE ve.EntryDate BETWEEN :date1 AND :date2 GROUP BY v.VolunteerID", nativeQuery = true)
    public List<ArrayList> allTotalTimeDates(@Param("date1") Date date1, @Param("date2") Date date2);

}
