/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.ericsson.restmodels1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for financialtransactionstatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="financialtransactionstatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SUCCESSFUL"/>
 *     &lt;enumeration value="PENDING"/>
 *     &lt;enumeration value="FAILED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "financialtransactionstatus")
@XmlEnum
public enum Financialtransactionstatus {

    SUCCESSFUL,
    PENDING,
    FAILED;

    public String value() {
        return name();
    }

    public static Financialtransactionstatus fromValue(String v) {
        return valueOf(v);
    }

}
