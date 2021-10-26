package com.prouman.mesg_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aseemchoudhary on 08/03/19.
 */

public class AllTemplatesCampigns {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("email_templates")
    @Expose
    private EmailTemplates emailTemplates;
    @SerializedName("email_campaigns")
    @Expose
    private EmailCampaigns emailCampaigns;
    @SerializedName("sms_templates")
    @Expose
    private SmsTemplates smsTemplates;
    @SerializedName("sms_campaigns")
    @Expose
    private SmsCampaigns smsCampaigns;

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

    public EmailTemplates getEmailTemplates() {
        return emailTemplates;
    }

    public void setEmailTemplates(EmailTemplates emailTemplates) {
        this.emailTemplates = emailTemplates;
    }

    public EmailCampaigns getEmailCampaigns() {
        return emailCampaigns;
    }

    public void setEmailCampaigns(EmailCampaigns emailCampaigns) {
        this.emailCampaigns = emailCampaigns;
    }

    public SmsTemplates getSmsTemplates() {
        return smsTemplates;
    }

    public void setSmsTemplates(SmsTemplates smsTemplates) {
        this.smsTemplates = smsTemplates;
    }

    public SmsCampaigns getSmsCampaigns() {
        return smsCampaigns;
    }

    public void setSmsCampaigns(SmsCampaigns smsCampaigns) {
        this.smsCampaigns = smsCampaigns;
    }


public class Category {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

public class EmailCampaigns {

    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("templates")
    @Expose
    private List<Template> templates = null;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

}

public class EmailTemplates {

    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("templates")
    @Expose
    private List<Template> templates = null;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

}


public class SmsCampaigns {

    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("templates")
    @Expose
    private List<Template> templates = null;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

}

public class SmsTemplates {

    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("templates")
    @Expose
    private List<Template> templates = null;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

}

public class Template {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("category")
    @Expose
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
}