package com.prouman.ninjaforms;

import java.util.ArrayList;

/**
 * Created by om on 2/19/2017.
 */
public class FormDataObject {

        private String success;

        public String getSuccess() { return this.success; }

        public void setSuccess(String success) { this.success = success; }

        private String status;

        public String getStatus() { return this.status; }

        public void setStatus(String status) { this.status = status; }

        private ArrayList<Form> forms;

        public ArrayList<Form> getForms() { return this.forms; }

        public void setForms(ArrayList<Form> forms) { this.forms = forms; }

}
