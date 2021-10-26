package com.prouman.ninjaforms;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by om on 2/19/2017.
 */
public class Element implements Parcelable {
    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private String id;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    private String required;

    public String getRequired() { return this.required; }

    public void setRequired(String required) { this.required = required; }

    private String type;

    public String getType() { return this.type; }

    public void setType(String type) { this.type = type; }

    private String value;

    public String getValue() { return this.value; }

    public void setValue(String value) { this.value = value; }

    private ArrayList<String> options;
    private Map<String,String> options_obj=new HashMap<>();
   // private String  option_str=null;
    public ArrayList<String> getOptions() { return this.options; }

    public void setOptions(ArrayList<String> options) { this.options = options; }

   /* private Style2 style;

    public Style2 getStyle() { return this.style; }

    public void setStyle(Style2 style) { this.style = style; }*/

    protected Element(Parcel in) {
        name = in.readString();
        id = in.readString();
        required = in.readString();
        type = in.readString();
        value = in.readString();
//        if (in.readByte() == 0x01) {
//            options_obj = new HashMap<String, String>();
        options_obj = in.readHashMap(Map.class.getClassLoader());
//        }else{options_obj=null;}
            //option_str=in.readString();
        if (in.readByte() == 0x01) {
            options = new ArrayList<String>();
            in.readList(options, String.class.getClassLoader());
        } else {
            options = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(required);
        dest.writeString(type);
        dest.writeString(value);
//        if (options_obj != null) {
//            option_str=options_obj.toString();
//        }
//        dest.writeString(option_str);
        dest.writeMap(options_obj);
        if (options == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(options);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Element> CREATOR = new Creator<Element>() {
        @Override
        public Element createFromParcel(Parcel in) {
            return new Element(in);
        }

        @Override
        public Element[] newArray(int size) {
            return new Element[size];
        }
    };

//    public JSONObject getOptions_obj() {
//        option_str=options_obj.toString();
//        return options_obj;
//    }
    public Map<String, String> getoption_str()
    {
//        if(options_obj!=null) {
            return options_obj;
       // }
    //    else return null;
    }

    public void setOptions_obj(Map<String, String> options_obj) {
        this.options_obj = options_obj;
    }
}