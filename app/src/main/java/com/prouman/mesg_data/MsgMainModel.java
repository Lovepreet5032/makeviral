package com.prouman.mesg_data;

import java.util.List;


/**
 * Created by dsingh on 12/19/2016.
 */

public class MsgMainModel {

    public List<Category> category;


    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

  /*  public List<Templates> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Templates> templates) {
        this.templates = templates;
    }
*/
    public static class Category {
        public String id;
        public String name;
        public String slug;
        public String created_by;
        public String language;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getId() {
            return id;
        }

        public String getLanguage() {
            return language;
        }

        public String getName() {
            return name;
        }
    }

        public String id;
        public String template_name;
        public String unique_name;
        public String description;
        public String subject;
        public String html_body;
        public String text_body;
        public String followup_day;
        public String available;
        public String created_by;
        public String header;
        public String body;
        public String footer;
        public String send_sms;
        public String sms_template;
        public String sms_message;
        public String email_category;
        public String reminder_email;
        public String reminder_year;
        public String reminder_month;
        public String reminder_day;
        public String reminder_time;
        public String reminder_message;
        public String include_me;
        public String reminder_exclude;
        public String add_to_campaign;
        public String sms_select;
        public String language;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail_category() {
            return email_category;
        }

        public void setEmail_category(String email_category) {
            this.email_category = email_category;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getHtml_body() {
            return html_body;
        }

        public void setHtml_body(String html_body) {
            this.html_body = html_body;
        }

        public String getText_body() {
            return text_body;
        }

        public void setText_body(String text_body) {
            this.text_body = text_body;
        }

        public String getSms_template() {
            return sms_template;
        }

        public void setSms_template(String sms_template) {
            this.sms_template = sms_template;
        }

        public String getSms_message() {
            return sms_message;
        }

        public void setSms_message(String sms_message) {
            this.sms_message = sms_message;
        }

        public String getSend_sms() {
            return send_sms;
        }

        public void setSend_sms(String send_sms) {
            this.send_sms = send_sms;
        }

}
