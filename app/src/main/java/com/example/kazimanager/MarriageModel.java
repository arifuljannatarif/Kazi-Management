package com.example.kazimanager;

import java.io.Serializable;

public class MarriageModel implements Serializable {
    String SeriaNo,BookNo,GroomName,BrideName,Address,Reference,MobileNo,IdNo,Notes;
    long Date;

    public MarriageModel(String seriaNo, String bookNo, String groomName, String brideName, String address, String reference, String mobileNo, String notes, long date) {
        SeriaNo = seriaNo;
        BookNo = bookNo;
        GroomName = groomName;
        BrideName = brideName;
        Address = address;
        Reference = reference;
        MobileNo = mobileNo;
        Notes = notes;
        Date = date;
    }

    public MarriageModel(String seriaNo, String bookNo, String groomName, String brideName, String address, String reference, String mobileNo, String idNo, String notes, long date) {
        SeriaNo = seriaNo;
        BookNo = bookNo;
        GroomName = groomName;
        BrideName = brideName;
        Address = address;
        Reference = reference;
        MobileNo = mobileNo;
        IdNo = idNo;
        Notes = notes;
        Date = date;
    }

    public MarriageModel() {

    }

    public String getSeriaNo() {
        return SeriaNo;
    }

    public void setSeriaNo(String seriaNo) {
        SeriaNo = seriaNo;
    }

    public String getBookNo() {
        return BookNo;
    }

    public void setBookNo(String bookNo) {
        BookNo = bookNo;
    }

    public String getGroomName() {
        return GroomName;
    }

    public void setGroomName(String groomName) {
        GroomName = groomName;
    }

    public String getBrideName() {
        return BrideName;
    }

    public void setBrideName(String brideName) {
        BrideName = brideName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getIdNo() {
        return IdNo;
    }

    public void setIdNo(String idNo) {
        IdNo = idNo;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }
}
