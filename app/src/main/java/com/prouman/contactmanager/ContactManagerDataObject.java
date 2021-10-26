package com.prouman.contactmanager;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aseemchoudhary on 09/05/18.
 */

public class ContactManagerDataObject {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<ContactManagerListData> data = null;
    @SerializedName("paging")
    @Expose
    private Paging paging;
    @SerializedName("fields")
    @Expose
    private Fields fields;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("groups")
    @Expose
    private Groups groups;

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

    public List<ContactManagerListData> getData() {
        return data;
    }

    public void setData(List<ContactManagerListData> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }


 class Fields {

    @SerializedName("standard")
    @Expose
    private Standard standard;
    @SerializedName("jsf")
    @Expose
    private Jsf jsf;

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Jsf getJsf() {
        return jsf;
    }

    public void setJsf(Jsf jsf) {
        this.jsf = jsf;
    }

}
 class Groups {

    @SerializedName("92")
    @Expose
    private String _92;
    @SerializedName("765")
    @Expose
    private String _765;

    public String get92() {
        return _92;
    }

    public void set92(String _92) {
        this._92 = _92;
    }

    public String get765() {
        return _765;
    }

    public void set765(String _765) {
        this._765 = _765;
    }

}
 class Jsf {

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
     public class Paging {

         @SerializedName("page")
         @Expose
         private Integer page;
         @SerializedName("total_page")
         @Expose
         private Integer totalPage;
         @SerializedName("record_from")
         @Expose
         private Integer recordFrom;
         @SerializedName("record_to")
         @Expose
         private Integer recordTo;
         @SerializedName("total_record")
         @Expose
         private Integer totalRecord;

         public Integer getPage() {
             return page;
         }

         public void setPage(Integer page) {
             this.page = page;
         }

         public Integer getTotalPage() {
             return totalPage;
         }

         public void setTotalPage(Integer totalPage) {
             this.totalPage = totalPage;
         }

         public Integer getRecordFrom() {
             return recordFrom;
         }

         public void setRecordFrom(Integer recordFrom) {
             this.recordFrom = recordFrom;
         }

         public Integer getRecordTo() {
             return recordTo;
         }

         public void setRecordTo(Integer recordTo) {
             this.recordTo = recordTo;
         }

         public Integer getTotalRecord() {
             return totalRecord;
         }

         public void setTotalRecord(Integer totalRecord) {
             this.totalRecord = totalRecord;
         }

     }
     public class Standard {

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

    static class ContactManagerListData implements Parcelable {
        @SerializedName("pro_id")
        @Expose
        private String proId;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("date_created")
        @Expose
        private String dateCreated;
        @SerializedName("picture")
        @Expose
        private String picture;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
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
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("device")
        @Expose
        private String device;
        @SerializedName("browser")
        @Expose
        private String browser;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("source_type")
        @Expose
        private String sourceType;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("age")
        @Expose
        private String age;
        @SerializedName("other_fields")
        @Expose
        private String otherFields;
        private boolean isSelected;
        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getEmail() {
            if(null ==email || ""==email){email="N/A";
            }
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

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

        public String getPhone() {
            if(null == phone || ""==phone)
            {phone="N/A";}
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getBrowser() {
            return browser;
        }

        public void setBrowser(String browser) {
            this.browser = browser;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSourceType() {
            return sourceType;
        }

        public void setSourceType(String sourceType) {
            this.sourceType = sourceType;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getOtherFields() {
            return otherFields;
        }

        public void setOtherFields(String otherFields) {
            this.otherFields = otherFields;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;}

        protected ContactManagerListData(Parcel in) {
            proId = in.readString();
            createdBy = in.readString();
            status = in.readString();
            dateCreated = in.readString();
            picture = in.readString();
            email = in.readString();
            firstName = in.readString();
            lastName = in.readString();
            phone = in.readString();
            address = in.readString();
            city = in.readString();
            state = in.readString();
            zip = in.readString();
            country = in.readString();
            location = in.readString();
            device = in.readString();
            browser = in.readString();
            source = in.readString();
            sourceType = in.readString();
            dob = in.readString();
            age = in.readString();
            otherFields = in.readString();
            isSelected = in.readByte() != 0x00;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(proId);
            dest.writeString(createdBy);
            dest.writeString(status);
            dest.writeString(dateCreated);
            dest.writeString(picture);
            dest.writeString(email);
            dest.writeString(firstName);
            dest.writeString(lastName);
            dest.writeString(phone);
            dest.writeString(address);
            dest.writeString(city);
            dest.writeString(state);
            dest.writeString(zip);
            dest.writeString(country);
            dest.writeString(location);
            dest.writeString(device);
            dest.writeString(browser);
            dest.writeString(source);
            dest.writeString(sourceType);
            dest.writeString(dob);
            dest.writeString(age);
            dest.writeString(otherFields);
            dest.writeByte((byte) (isSelected ? 0x01 : 0x00));
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<ContactManagerListData> CREATOR = new Parcelable.Creator<ContactManagerListData>() {
            @Override
            public ContactManagerListData createFromParcel(Parcel in) {
                return new ContactManagerListData(in);
            }

            @Override
            public ContactManagerListData[] newArray(int size) {
                return new ContactManagerListData[size];
            }
        };

         public String getName(){
             if((null == firstName || ""==firstName) && (null == lastName && ""==lastName ))
             {
                return "N/A";
             }
             else return firstName+" "+lastName;
         }
     }
}
