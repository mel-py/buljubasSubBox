package com.buljubasic.melissa.buljubas_subbox;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Melissa on 2018-01-17.
 */

/*
 * Subscription object
 * Stores the name, date, charge, and comment
 * Throws exceptions if the comment is too long, name is too long, or charge is negative
 * Does not check if the date is in the proper format
 */
public class Subscription {
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

    Subscription(String name, String date, String charge, String comment) throws
            CommentTooLongException, NameTooLongException {
        if (name.length() < 20) {
            this.name = name;
        }
        this.date = date;
        this.charge = charge;
        if (comment.length() < 30) {
            this.comment = comment;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName)throws NameTooLongException {
        if (newName.length() < 20) {
            this.name = newName;
        }
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String newDate) {
        this.date = newDate;
    }

    public String getCharge() {
        return this.charge;
    }

    public void setCharge(String newCharge) { this.charge = newCharge; }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String newComment) throws CommentTooLongException {
        if (newComment.length() < 30) {
            this.comment = newComment;
        }
    }
}