package com.prouman.model;

import java.util.List;

/**
 * Created by jcs on 12/16/2016.
 */
public class MessageCatModel {
    public List<Category> category;
    public List<Templates> templates;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Templates> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Templates> templates) {
        this.templates = templates;
    }

    public static class Category {
        public String id;
        public String name;
        public String slug;
        public String created_by;
        public String language;

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

        public String getCreated_by() {
            return created_by;
        }

        public void setCreated_by(String created_by) {
            this.created_by = created_by;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }

    public static class Templates {
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

        public String getTemplate_name() {
            return template_name;
        }

        public void setTemplate_name(String template_name) {
            this.template_name = template_name;
        }

        public String getUnique_name() {
            return unique_name;
        }

        public void setUnique_name(String unique_name) {
            this.unique_name = unique_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getHtml_body() {
            return html_body;
        }

        public void setHtml_body(String html_body) {
            this.html_body = html_body;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }
    }
}
