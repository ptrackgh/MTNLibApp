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
public class VendPinRequest {

    private String HASH;
    private String DateTime;
    private String RequestID;
    private String ServiceCode;
    private String ClientID;

    public String getHASH() {
        return HASH;
    }

    public void setHASH(String HASH) {
        this.HASH = HASH;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String DateTime) {
        this.DateTime = DateTime;
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

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    @Override
    public String toString() {
        return "VendPinRequest [HASH = " + HASH + ", DateTime = " + DateTime + ", RequestID = " + RequestID + ", ServiceCode = " + ServiceCode + ", ClientID = " + ClientID + "]";
    }
}
