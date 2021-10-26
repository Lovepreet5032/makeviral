package com.prouman.omspages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aseemchoudhary on 24/04/18.
 */

public class PagesListDataobject {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("pages")
    @Expose
    private List<OMSPage> pages = null;
    @SerializedName("data")
    @Expose
    private Data data;
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OMSPage> getPages() {
        return pages;
    }

    public void setPages(List<OMSPage> pages) {
        this.pages = pages;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class OMSPage {

        @SerializedName("page_type")
        @Expose
        private String pageType;
        @SerializedName("ID")
        @Expose
        private String iD;
        @SerializedName("post_title")
        @Expose
        private String postTitle;
        @SerializedName("post_url")
        @Expose
        private String postUrl;
        @SerializedName("post_date")
        @Expose
        private String postDate;
        @SerializedName("post_content")
        @Expose
        private String postContent;
        @SerializedName("markups")
        @Expose
        private String markups;
        @SerializedName("post_type")
        @Expose
        private String postType;
        @SerializedName("stats")
        @Expose
        private Stats stats;
        @SerializedName("image")
        @Expose
        private String image;

        public String getPageType() {
            return pageType;
        }

        public void setPageType(String pageType) {
            this.pageType = pageType;
        }

        public String getID() {
            return iD;
        }

        public void setID(String iD) {
            this.iD = iD;
        }

        public String getPostTitle() {
            if(null != postTitle)
            return postTitle;
            else return "";
        }

        public void setPostTitle(String postTitle) {
            this.postTitle = postTitle;
        }

        public String getPostUrl() {
            if(null != postUrl)
            return postUrl;
            else
                return "";
        }

        public void setPostUrl(String postUrl) {
            this.postUrl = postUrl;
        }

        public String getPostDate() {
            if(null != postDate) {
                return postDate;
            }else return "";
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public String getPostContent() {
            if(null != postContent)
            return postContent;
            else return "";
        }

        public void setPostContent(String postContent) {
            this.postContent = postContent;
        }

        public String getMarkups() {
            return markups;
        }

        public void setMarkups(String markups) {
            this.markups = markups;
        }

        public String getPostType() {
            return postType;
        }

        public void setPostType(String postType) {
            this.postType = postType;
        }

        public Stats getStats() {
            return stats;
        }

        public void setStats(Stats stats) {
            this.stats = stats;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
    public static class Data{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("master_id")
        @Expose
        private String masterId;
        @SerializedName("duplicate_type")
        @Expose
        private String duplicateType;
        @SerializedName("duplicate_per")
        @Expose
        private String duplicatePer;
        @SerializedName("share_status")
        @Expose
        private String shareStatus;
        @SerializedName("section_name")
        @Expose
        private String sectionName;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("stats_days")
        @Expose
        private Integer statsDays;
   /*     @SerializedName("cats")
        @Expose
        private List<Object> cats = null;*/
        @SerializedName("wp_connect_url")
        @Expose
        private String wpConnectUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMasterId() {
            return masterId;
        }

        public void setMasterId(String masterId) {
            this.masterId = masterId;
        }

        public String getDuplicateType() {
            return duplicateType;
        }

        public void setDuplicateType(String duplicateType) {
            this.duplicateType = duplicateType;
        }

        public String getDuplicatePer() {
            return duplicatePer;
        }

        public void setDuplicatePer(String duplicatePer) {
            this.duplicatePer = duplicatePer;
        }

        public String getShareStatus() {
            return shareStatus;
        }

        public void setShareStatus(String shareStatus) {
            this.shareStatus = shareStatus;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Integer getStatsDays() {
            return statsDays;
        }

        public void setStatsDays(Integer statsDays) {
            this.statsDays = statsDays;
        }

      /*  public List<Object> getCats() {
            return cats;
        }

        public void setCats(List<Object> cats) {
            this.cats = cats;
        }
*/
        public String getWpConnectUrl() {
            return wpConnectUrl;
        }

        public void setWpConnectUrl(String wpConnectUrl) {
            this.wpConnectUrl = wpConnectUrl;
        }
    }
    public static class Stats {

        @SerializedName("page_type")
        @Expose
        private String pageType;
        @SerializedName("page_id")
        @Expose
        private String pageId;
        @SerializedName("hits")
        @Expose
        private String hits;
        @SerializedName("total_time")
        @Expose
        private String totalTime;
        @SerializedName("avg_time")
        @Expose
        private String avgTime;
        @SerializedName("ping_back")
        @Expose
        private String pingBack;
        @SerializedName("avg_ping_back")
        @Expose
        private String avgPingBack;
        @SerializedName("prospects")
        @Expose
        private String prospects;
        @SerializedName("form_clicks")
        @Expose
        private String formClicks;
        @SerializedName("form_submits")
        @Expose
        private String formSubmits;
        @SerializedName("form_hits")
        @Expose
        private String formHits;
        @SerializedName("video_total_views")
        @Expose
        private String videoTotalViews;
        @SerializedName("video_middle_views")
        @Expose
        private String videoMiddleViews;

        public String getPageType() {
            return pageType;
        }

        public void setPageType(String pageType) {
            this.pageType = pageType;
        }

        public String getPageId() {
            return pageId;
        }

        public void setPageId(String pageId) {
            this.pageId = pageId;
        }

        public String getHits() {
            if(null != hits) {
                return hits;
            }else return "";
        }

        public void setHits(String hits) {
            this.hits = hits;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }

        public String getAvgTime() {
            return avgTime;
        }

        public void setAvgTime(String avgTime) {
            this.avgTime = avgTime;
        }

        public String getPingBack() {
            return pingBack;
        }

        public void setPingBack(String pingBack) {
            this.pingBack = pingBack;
        }

        public String getAvgPingBack() {
            return avgPingBack;
        }

        public void setAvgPingBack(String avgPingBack) {
            this.avgPingBack = avgPingBack;
        }

        public String getProspects() {
            return prospects;
        }

        public void setProspects(String prospects) {
            this.prospects = prospects;
        }

        public String getFormClicks() {
            if(null == formClicks){
                return "0";
            }
            else {
                return formClicks;
            }
        }

        public void setFormClicks(String formClicks) {
            this.formClicks = formClicks;
        }

        public String getFormSubmits() {
            if(null == formSubmits){
                return "0";
            }
            else {
                return formSubmits;
            }
        }

        public void setFormSubmits(String formSubmits) {
            this.formSubmits = formSubmits;
        }

        public String getFormHits() {
            if(null == formHits){
                return "0";
            }
            else {
                return formHits;
            }
        }

        public void setFormHits(String formHits) {
            this.formHits = formHits;
        }

        public String getVideoTotalViews() {
            if(null == videoTotalViews){
                return "0";
            }
            else {
                return videoTotalViews;
            }
        }

        public void setVideoTotalViews(String videoTotalViews) {
            this.videoTotalViews = videoTotalViews;
        }

        public String getVideoMiddleViews() {

            if(null == videoMiddleViews){
                return "0";
            }
            else {
                return videoMiddleViews;
            }
        }

        public void setVideoMiddleViews(String videoMiddleViews) {
            this.videoMiddleViews = videoMiddleViews;
        }
    }
}
