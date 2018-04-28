package com.blackhawk.vtt2.model.response;

import com.blackhawk.vtt2.model.VolunteerActivity;

public class VolunteerActivityResponse {

    private Integer volunteerActivityId;
    private String activityType;
    private Boolean isActive;

    public static VolunteerActivityResponse from(VolunteerActivity volunteerActivity){
        VolunteerActivityResponse response = new VolunteerActivityResponse();
        response.setVolunteerActivityId(volunteerActivity.getVolunteerActivityId());
        response.setActivityType(volunteerActivity.getActivityType());
        response.setIsActive(volunteerActivity.getIsActive());

        return response;
    }
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

}
