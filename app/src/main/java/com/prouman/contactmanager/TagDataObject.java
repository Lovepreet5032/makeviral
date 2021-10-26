package com.prouman.contactmanager;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aseemchoudhary on 10/05/18.
 */

public class TagDataObject {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private Data data;
    private static TagDataObject single_instance = null;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Fields {

        @SerializedName("standard")
        @Expose
        private Standard standard;
        @SerializedName("custom")
        @Expose
        private Custom custom;

        public Standard getStandard() {
            return standard;
        }

        public void setStandard(Standard standard) {
            this.standard = standard;
        }

        public Custom getCustom() {
            return custom;
        }

        public void setCustom(Custom custom) {
            this.custom = custom;
        }

    }
    private TagDataObject()
    {

    }

    // static method to create instance of Singleton class
    public static TagDataObject getInstance()
    {
        if (single_instance == null)
            single_instance = new TagDataObject();

        return single_instance;
    }
    public class Campaigns {

        @SerializedName("email")
        @Expose
        private ArrayList<Email> email = null;
        @SerializedName("sms")
        @Expose
        private ArrayList<Email> sms = null;
        @SerializedName("push")
        @Expose
        private ArrayList<Email> push = null;

        public ArrayList<Email> getEmail() {
            return email;
        }

        public void setEmail(ArrayList<Email> email) {
            this.email = email;
        }

        public ArrayList<Email> getSms() {
            return sms;
        }

        public void setSms(ArrayList<Email> sms) {
            this.sms = sms;
        }

        public ArrayList<Email> getPush() {
            return push;
        }

        public void setPush(ArrayList<Email> push) {
            this.push = push;
        }

    }


 class Standard {

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
 class Email implements Parcelable {

     @SerializedName("id")
     @Expose
     private String id;
     @SerializedName("campaign_name")
     @Expose
     private String campaignName;
     @SerializedName("msgs")
     @Expose
     private String msgs;

     public String getId() {
         return id;
     }

     public void setId(String id) {
         this.id = id;
     }

     public String getCampaignName() {
         return campaignName;
     }

     public void setCampaignName(String campaignName) {
         this.campaignName = campaignName;
     }

     public String getMsgs() {
         return msgs;
     }

     public void setMsgs(String msgs) {
         this.msgs = msgs;
     }


     protected Email(Parcel in) {
         id = in.readString();
         campaignName = in.readString();
         msgs = in.readString();
     }

     @Override
     public int describeContents() {
         return 0;
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
         dest.writeString(id);
         dest.writeString(campaignName);
         dest.writeString(msgs);
     }

     @SuppressWarnings("unused")
     public  final Parcelable.Creator<Email> CREATOR = new Parcelable.Creator<Email>() {
         @Override
         public Email createFromParcel(Parcel in) {
             return new Email(in);
         }

         @Override
         public Email[] newArray(int size) {
             return new Email[size];
         }
     };
 }
 class Custom {

    @SerializedName("138")
    @Expose
    private String _138;
    @SerializedName("139")
    @Expose
    private String _139;

    public String get138() {
        return _138;
    }

    public void set138(String _138) {
        this._138 = _138;
    }

    public String get139() {
        return _139;
    }

    public void set139(String _139) {
        this._139 = _139;
    }

}

public class Data {

    @SerializedName("fields")
    @Expose
    private TagDataObject.Fields fields;
    @SerializedName("tags")
    @Expose
    private List<Groups> tags = null;
    @SerializedName("groups")
    @Expose
    private ArrayList<Groups> groups;
    @SerializedName("campaigns")
    @Expose
    private Campaigns campaigns;
    @SerializedName("broadcasts")
    @Expose
    private ArrayList<Broadcast> broadcasts = null;
    @SerializedName("Sources")
    @Expose
    private HashMap<String,String> sources;
    @SerializedName("Genders")
    @Expose
    private HashMap<String,String> gender;
    @SerializedName("age_creterias")
    @Expose
    private HashMap<String,String> agecateria;
    @SerializedName("category")
    @Expose
    private ArrayList<Groups> category;
//    @SerializedName("Sources")
//    @Expose
//    private HashMap<String,String> mapSources = null;
//    @SerializedName("Genders")
//    @Expose
//    private HashMap<String,String> mapGenders = null;
//    @SerializedName("age_creterias")
//    @Expose
//    private HashMap<String,String> mapAgeCateries = null;
    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public List<Groups> getTags() {
        return tags;
    }

    public void setTags(List<Groups> tags) {
        this.tags = tags;
    }

    public ArrayList<Groups> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Groups> groups) {
        this.groups = groups;
    }

    public Campaigns getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Campaigns campaigns) {
        this.campaigns = campaigns;
    }

    public ArrayList<Broadcast> getBroadcasts() {
        return broadcasts;
    }

    public void setBroadcasts(ArrayList<Broadcast> broadcasts) {
        this.broadcasts = broadcasts;
    }

    public HashMap<String,String> getSources() {
        return sources;
    }

    public void setSources(HashMap<String,String> sources) {
        this.sources = sources;
    }

    public HashMap<String,String> getGender() {
        return gender;
    }

    public void setGender(HashMap<String,String> gender) {
        this.gender = gender;
    }

    public HashMap<String,String> getAgecateria() {
        return agecateria;
    }

    public void setAgecateria(HashMap<String,String> agecateria) {
        this.agecateria = agecateria;
    }

    public ArrayList<Groups> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Groups> category) {
        this.category = category;
    }

//    public HashMap<String, String> getMapSources() {
//        return mapSources;
//    }
//
//    public void setMapSources(HashMap<String, String> mapSources) {
//        this.mapSources = mapSources;
//    }
//
//    public HashMap<String, String> getMapGenders() {
//        return mapGenders;
//    }
//
//    public void setMapGenders(HashMap<String, String> mapGenders) {
//        this.mapGenders = mapGenders;
//    }
//
//    public HashMap<String, String> getMapAgeCateries() {
//        return mapAgeCateries;
//    }
//
//    public void setMapAgeCateries(HashMap<String, String> mapAgeCateries) {
//        this.mapAgeCateries = mapAgeCateries;
//    }
}

    public class Groups {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
//        @SerializedName("msgs")
//        @Expose
//        private String msgs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGroupName() {
            return name;
        }

        public void setGroupName(String campaignName) {
            this.name = campaignName;
        }

//        public String getMsgs() {
//            return msgs;
//        }
//
//        public void setMsgs(String msgs) {
//            this.msgs = msgs;
//        }

    }
    public class Broadcast {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("list_name")
        @Expose
        private String listName;
        @SerializedName("msg_name")
        @Expose
        private String msgName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getListName() {
            return listName;
        }

        public void setListName(String listName) {
            this.listName = listName;
        }

        public String getMsgName() {
            return msgName;
        }

        public void setMsgName(String msgName) {
            this.msgName = msgName;
        }

    }
}
