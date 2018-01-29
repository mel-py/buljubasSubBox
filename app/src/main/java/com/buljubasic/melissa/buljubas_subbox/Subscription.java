package com.buljubasic.melissa.buljubas_subbox;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Melissa on 2018-01-17.
 */

public class Subscription {
    private int mData;
    private String name;
    private String date;
    private String charge;
    private String comment;

    Subscription(String name, String date, String charge) {
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = "";
    }

    Subscription(String name, String date, String charge, String comment) throws CommentTooLongException {
        this.name = name;
        this.date = date;
        this.charge = charge;
        if (comment.length() < 30) {
            this.comment = comment;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(Date newDate) {
        this.date = date;
    }

    public String getCharge() {
        return this.charge;
    }

    public void setCharge(String newCharge) {
        this.charge = newCharge;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String newComment) throws CommentTooLongException {
        if (newComment.length() < 30) {
            this.comment = newComment;
        }
    }
}