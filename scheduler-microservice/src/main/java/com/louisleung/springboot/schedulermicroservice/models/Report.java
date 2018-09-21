package com.louisleung.springboot.schedulermicroservice.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document
public class Report {
    public enum ReportStatus {
        ABLE_TO_COMPLETE,
        UNABLE_TO_COMPLETE_GIVEN_CURRENT_STATE;
    }

    private ReportStatus status;
    private Map<TaskConsumer,List<Task>> schedule;

    public Report() {
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public Map<String, List<Task>> getSchedule() {
        /* Convert task consumers to their ID number. */
        Map<String, List<Task>> scheduleReadable = new HashMap<>();
        for (TaskConsumer tc : schedule.keySet()) {
            scheduleReadable.put(tc.getId(),schedule.get(tc));
        }
        return scheduleReadable;
    }

    public void setSchedule(Map<TaskConsumer, List<Task>> schedule) {
        this.schedule = schedule;
    }

    public Report(Map<TaskConsumer,List<Task>> schedule) {
        this.schedule = schedule;
        this.status = ReportStatus.ABLE_TO_COMPLETE;
    }

    public Report(ReportStatus status) {
        this.schedule = new HashMap<>();
        this.status = status;
    }
}
