package com.unidadcoronaria.doctorencasa.network.rest;

import com.unidadcoronaria.doctorencasa.domain.Queue;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.dto.VideoCallDTO;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * @author agustin.bala
 * @since 0.0.1
 */
public interface VideoCallService {

    @POST("videocall")
    Single<VideoCall> create();

    @POST("videocall/finish")
    Single<VideoCall> hangup(@Body VideoCallDTO videoCallDTO);

    @POST("videocall/start")
    Single<VideoCall> start(@Body VideoCallDTO videoCallDTO);

    @POST("videocall/ranking")
    Single<VideoCall> rank(@Body VideoCallDTO videoCallDTO);

    @GET("queue/status")
    Single<Queue> getQueue();

    @GET("videocall")
    Single<VideoCall> getLastCall();

    @GET("videocall/{videocallId}")
    Single<VideoCall> getVideocall(@Path("videocallId") int mVideocallId);

    @POST("videocall/cancel")
    Single<VideoCall> cancel(@Body VideoCallDTO videoCallDTO);
}


