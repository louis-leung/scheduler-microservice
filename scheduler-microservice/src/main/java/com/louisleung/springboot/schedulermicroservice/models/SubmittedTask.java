package com.louisleung.springboot.schedulermicroservice.models;

public class SubmittedTask {
    private Integer readableId;
    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;
    private int seconds;
    private long duration;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public SubmittedTask(Integer readableId, int month, int day, int year, int hour, int minute, int seconds) {
        this.readableId = readableId;
        this.month = month;
        this.day = day;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.seconds = seconds;
    }

    public SubmittedTask() {

    }

    public Integer getReadableId() {
        return readableId;
    }

    public void setReadableId(Integer readableId) {
        this.readableId = readableId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
