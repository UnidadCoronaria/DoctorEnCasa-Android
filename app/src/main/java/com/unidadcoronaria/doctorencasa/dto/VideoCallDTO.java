package com.unidadcoronaria.doctorencasa.dto;

/**
 * Created by agustin on 20/11/17.
 */

public class VideoCallDTO {

    private int videocallId;
    private Integer ranking;
    private String comment;

    public int getVideocallId() {
        return videocallId;
    }

    public void setVideocallId(int videocallId) {
        this.videocallId = videocallId;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
