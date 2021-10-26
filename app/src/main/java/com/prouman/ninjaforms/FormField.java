package com.prouman.ninjaforms;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by om on 2/19/2017.
 */
public class FormField implements Parcelable {
    private Label label;

    public Label getLabel() { return this.label; }

    public void setLabel(Label label) { this.label = label; }

    private ArrayList<Element> elements;

    public ArrayList<Element> getElements() { return this.elements; }

    public void setElements(ArrayList<Element> elements) { this.elements = elements; }

    private Anchor anchor;

    public Anchor getAnchor() { return this.anchor; }

    public void setAnchor(Anchor anchor) { this.anchor = anchor; }

    protected FormField(Parcel in) {
        label = (Label) in.readValue(Label.class.getClassLoader());
        if (in.readByte() == 0x01) {
            elements = new ArrayList<Element>();
            in.readList(elements, Element.class.getClassLoader());
        } else {
            elements = null;
        }
        try {
            anchor = (Anchor) in.readValue(Anchor.class.getClassLoader());
        }
        catch (Exception e){}
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(label);
        if (elements == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(elements);
        }
        dest.writeValue(anchor);
    }

    @SuppressWarnings("unused")
    public static final Creator<FormField> CREATOR = new Creator<FormField>() {
        @Override
        public FormField createFromParcel(Parcel in) {
            return new FormField(in);
        }

        @Override
        public FormField[] newArray(int size) {
            return new FormField[size];
        }
    };
}