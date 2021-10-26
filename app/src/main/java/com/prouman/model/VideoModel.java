package com.prouman.model;

/**
 * Created by dsingh on 9/1/2016.
 */
public class VideoModel {
    @com.google.gson.annotations.SerializedName("id")
    public String id;
    @com.google.gson.annotations.SerializedName("title")
    public String title;
    @com.google.gson.annotations.SerializedName("source")
    public String source;
    @com.google.gson.annotations.SerializedName("type")
    public String type;
    @com.google.gson.annotations.SerializedName("description")
    public String description;
    @com.google.gson.annotations.SerializedName("category")
    public String category;
    @com.google.gson.annotations.SerializedName("language")
    public String language;
    @com.google.gson.annotations.SerializedName("status")
    public String status;
    @com.google.gson.annotations.SerializedName("tags")
    public String tags;
    @com.google.gson.annotations.SerializedName("private")
    public String mprivate;
    @com.google.gson.annotations.SerializedName("created_by")
    public String created_by;
    @com.google.gson.annotations.SerializedName("date_created")
    public String date_created;
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    @com.google.gson.annotations.SerializedName("thumbnail")
    public  String thumbnail;
    public VideoModel(String id, String title, String source, String type, String description, String category, String language, String status, String tags, String mprivate, String created_by, String date_created) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.type = type;
        this.description = description;
        this.category = category;
        this.language = language;
        this.status = status;
        this.tags = tags;
        this.mprivate = mprivate;
        this.created_by = created_by;
        this.date_created = date_created;
    }

    public VideoModel(String id, String title, String source, String description, String category) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.description = description;
        this.category = category;
    }

    public VideoModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMprivate() {
        return mprivate;
    }

    public void setMprivate(String mprivate) {
        this.mprivate = mprivate;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
