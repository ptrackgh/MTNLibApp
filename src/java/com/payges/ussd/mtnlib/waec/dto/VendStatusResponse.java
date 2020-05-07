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
public class VendStatusResponse {

    private String ResponseCode;
    private String ResponseDesc;
    private String ServiceCode;
    private String Active;
    private String Cost;
    private String Currency;

    public String getResponseDesc() {
        return ResponseDesc;
    }

    public void setResponseDesc(String ResponseDesc) {
        this.ResponseDesc = ResponseDesc;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public void setServiceCode(String ServiceCode) {
        this.ServiceCode = ServiceCode;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String Active) {
        this.Active = Active;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String Cost) {
        this.Cost = Cost;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String Currency) {
        this.Currency = Currency;
    }

    @Override
    public String toString() {
        return "VendStatusResponse{" + "ResponseCode=" + ResponseCode + ", ResponseDesc=" + ResponseDesc + ", ServiceCode=" + ServiceCode + ", Active=" + Active + ", Cost=" + Cost + ", Currency=" + Currency + '}';
    }
    
}
