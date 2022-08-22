/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.ericsson.restmodels1;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transactionid" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="externaltransactionid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="receiverinfo" type="{http://www.ericsson.com/em/emm/v1_0/common}receiverinfo"/>
 *         &lt;element name="status" type="{http://www.ericsson.com/em/emm/v1_0/common}financialtransactionstatus"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "transactionid",
    "externaltransactionid",
    "receiverinfo",
    "status"
})
@XmlRootElement(name = "debitcompletedrequest")
public class Debitcompletedrequest {

    protected long transactionid;
    @XmlElement(required = true)
    protected String externaltransactionid;
    @XmlElement(required = true)
    protected Receiverinfo receiverinfo;
    @XmlElement(required = true)
    protected Financialtransactionstatus status;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Gets the value of the transactionid property.
     * 
     */
    public long getTransactionid() {
        return transactionid;
    }

    /**
     * Sets the value of the transactionid property.
     * 
     */
    public void setTransactionid(long value) {
        this.transactionid = value;
    }

    /**
     * Gets the value of the externaltransactionid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternaltransactionid() {
        return externaltransactionid;
    }

    /**
     * Sets the value of the externaltransactionid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternaltransactionid(String value) {
        this.externaltransactionid = value;
    }

    /**
     * Gets the value of the receiverinfo property.
     * 
     * @return
     *     possible object is
     *     {@link Receiverinfo }
     *     
     */
    public Receiverinfo getReceiverinfo() {
        return receiverinfo;
    }

    /**
     * Sets the value of the receiverinfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Receiverinfo }
     *     
     */
    public void setReceiverinfo(Receiverinfo value) {
        this.receiverinfo = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Financialtransactionstatus }
     *     
     */
    public Financialtransactionstatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Financialtransactionstatus }
     *     
     */
    public void setStatus(Financialtransactionstatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
