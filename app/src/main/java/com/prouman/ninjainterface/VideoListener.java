package com.prouman.ninjainterface;

import com.prouman.model.VideoModel;

import java.util.ArrayList;

/**
 * Created by dsingh on 9/1/2016.
 */
public interface VideoListener {
    void addVideo(VideoModel videoModel);

    ArrayList<VideoModel> getAllVideo();

    int getVideoCount();
}
