package com.unidadcoronaria.doctorencasa.dto;

/**
 * Created by agustin on 20/11/17.
 */

public class VideoCallDTO {

    private int videocallId;
    private Integer score;
    private String comment;

    public int getVideocallId() {
        return videocallId;
    }

    public void setVideocallId(int videocallId) {
        this.videocallId = videocallId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
