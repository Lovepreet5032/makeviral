package com.prouman.mesg_data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsingh on 12/19/2016.
 */

public class Category {

    public String id;
    public String name;
    public String slug;
    public String created_by;
    public String language;
    private List<MessageModelTest> prods = new ArrayList<>();

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public List<MessageModelTest> getProds() {
        return prods;
    }

    public void setProds(List<MessageModelTest> prods) {
        this.prods = prods;
    }

    public static class MessageModelTest {
        int id;
        String templateName;
        String description;
        String body;
        String htmlBody;
        int email_category;
        String lanugageCode;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTemplateName() {
            return templateName;
        }

        public void setTemplateName(String templateName) {
            this.templateName = templateName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getHtmlBody() {
            return htmlBody;
        }

        public void setHtmlBody(String htmlBody) {
            this.htmlBody = htmlBody;
        }

        public int getEmail_category() {
            return email_category;
        }

        public void setEmail_category(int email_category) {
            this.email_category = email_category;
        }

        public String getLanugageCode() {
            return lanugageCode;
        }

        public void setLanugageCode(String lanugageCode) {
            this.lanugageCode = lanugageCode;
        }
    }
}
