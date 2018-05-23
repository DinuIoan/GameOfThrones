package com.got.bestapps.gameofthrones.model;

public class AppInfo {
    private Long id;
    private long timeStarted;
    private long timeEnded;
    private int firstOpen;

    public AppInfo(Long id, long timeStarted, long timeEnded, int firstOpen) {
        this.id = id;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.firstOpen = firstOpen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(long timeStarted) {
        this.timeStarted = timeStarted;
    }

    public long getTimeEnded() {
        return timeEnded;
    }

    public void setTimeEnded(long timeEnded) {
        this.timeEnded = timeEnded;
    }

    public int getFirstOpen() {
        return firstOpen;
    }

    public void setFirstOpen(int firstOpen) {
        this.firstOpen = firstOpen;
    }
}
