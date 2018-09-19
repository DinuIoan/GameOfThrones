package com.got.bestapps.gameofthrones.model;

public class AppInfo {
    private Long id;
    private long lastTimePlayed;
    private int remaining;


    public AppInfo(Long id, long lastTimePlayed, int remaining) {
        this.id = id;
        this.lastTimePlayed = lastTimePlayed;
        this.remaining = remaining;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public long getLastTimePlayed() {
        return lastTimePlayed;
    }

    public void setLastTimePlayed(long lastTimePlayed) {
        this.lastTimePlayed = lastTimePlayed;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
}
