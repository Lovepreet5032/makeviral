package com.prouman.videonewscreen;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by aseemchoudhary on 14/02/18.
 */

public class VideoData implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("video_name")
    @Expose
    private String videoName;
    @SerializedName("embed_url")
    @Expose
    private String embedUrl;
    @SerializedName("video_img")
    @Expose
    private String videoImg;
    @SerializedName("content_url")
    @Expose
    private String videoDetails;
    @SerializedName("captions")
    @Expose
    private ArrayList<Caption> captions = null;
    @SerializedName("app_icon")
    @Expose
    private String appIcon;
    @SerializedName("share_url")
    @Expose
    private String share_url;
    @SerializedName("private")
    @Expose
    private String privateVideo;
    @SerializedName("private_password")
    @Expose
    private String private_password;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public String getVideoDetails() {
        return videoDetails;
    }

    public void setVideoDetails(String videoDetails) {
        this.videoDetails = videoDetails;
    }

    public ArrayList<Caption> getCaptions() {
        return captions;
    }

    public void setCaptions(ArrayList<Caption> captions) {
        this.captions = captions;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    protected VideoData(Parcel in) {
        id = in.readString();
        videoName = in.readString();
        embedUrl = in.readString();
        videoImg = in.readString();
        videoDetails = in.readString();
        if (in.readByte() == 0x01) {
            captions = new ArrayList<Caption>();
            in.readList(captions, Caption.class.getClassLoader());
        } else {
            captions = null;
        }
        appIcon = in.readString();
        share_url = in.readString();
        privateVideo=in.readString();
        private_password=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(videoName);
        dest.writeString(embedUrl);
        dest.writeString(videoImg);
        dest.writeString(videoDetails);
        if (captions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(captions);
        }
        dest.writeString(appIcon);
        dest.writeString(share_url);
        dest.writeString(privateVideo);
        dest.writeString(private_password);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<VideoData> CREATOR = new Parcelable.Creator<VideoData>() {
        @Override
        public VideoData createFromParcel(Parcel in) {
            return new VideoData(in);
        }

        @Override
        public VideoData[] newArray(int size) {
            return new VideoData[size];
        }
    };

    public String getPrivateVideo() {
        return privateVideo;
    }

    public void setPrivateVideo(String privateVideo) {
        this.privateVideo = privateVideo;
    }

    public String getPrivate_password() {
        return private_password;
    }

    public void setPrivate_password(String private_password) {
        this.private_password = private_password;
    }
}