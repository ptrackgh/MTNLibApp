/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.waec.dto;

/**
 *
 * @author ptrack
 */
public class VendPinResponse {

    private String TransID;
    private String Description;
    private String Serial;
    private String ResponseDesc;
    private String RequestID;
    private String ServiceCode;
    private String PIN;
    private String ResponseCode;

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String TransID) {
        this.TransID = TransID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String Serial) {
        this.Serial = Serial;
    }

    public String getResponseDesc() {
        return ResponseDesc;
    }

    public void setResponseDesc(String ResponseDesc) {
        this.ResponseDesc = ResponseDesc;
    }

    public String getRequestID() {
        return RequestID;
    }

    public void setRequestID(String RequestID) {
        this.RequestID = RequestID;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public void setServiceCode(String ServiceCode) {
        this.ServiceCode = ServiceCode;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    @Override
    public String toString() {
        return "VendPinResponse [TransID = " + TransID + ", Description = " + Description + ", Serial = " + Serial + ", ResponseDesc = " + ResponseDesc + ", RequestID = " + RequestID + ", ServiceCode = " + ServiceCode + ", PIN = " + PIN + ", ResponseCode = " + ResponseCode + "]";
    }
}
