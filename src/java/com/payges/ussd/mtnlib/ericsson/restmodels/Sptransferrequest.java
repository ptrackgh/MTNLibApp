/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.ericsson.restmodels;

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
 *         &lt;element name="sendingfri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receivingfri" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.ericsson.com/em/emm/v1_0/common}moneydetailstype"/>
 *         &lt;element name="providertransactionid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.ericsson.com/em/emm/serviceprovider/v1_0/common}name" minOccurs="0"/>
 *         &lt;element name="sendernote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receivermessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quoteid" type="{http://www.ericsson.com/em/emm/v1_0/common}quoteid" minOccurs="0"/>
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
    "sendingfri",
    "receivingfri",
    "amount",
    "providertransactionid",
    "name",
    "sendernote",
    "receivermessage",
    "referenceid",
    "quoteid"
})
@XmlRootElement(name = "sptransferrequest")
public class Sptransferrequest {

    protected String sendingfri;
    @XmlElement(required = true)
    protected String receivingfri;
    @XmlElement(required = true)
    protected Moneydetailstype amount;
    @XmlElement(required = true)
    protected String providertransactionid;
    protected Name name;
    protected String sendernote;
    protected String receivermessage;
    protected String referenceid;
    protected String quoteid;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Gets the value of the sendingfri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendingfri() {
        return sendingfri;
    }

    /**
     * Sets the value of the sendingfri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendingfri(String value) {
        this.sendingfri = value;
    }

    /**
     * Gets the value of the receivingfri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceivingfri() {
        return receivingfri;
    }

    /**
     * Sets the value of the receivingfri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceivingfri(String value) {
        this.receivingfri = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link Moneydetailstype }
     *     
     */
    public Moneydetailstype getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Moneydetailstype }
     *     
     */
    public void setAmount(Moneydetailstype value) {
        this.amount = value;
    }

    /**
     * Gets the value of the providertransactionid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvidertransactionid() {
        return providertransactionid;
    }

    /**
     * Sets the value of the providertransactionid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvidertransactionid(String value) {
        this.providertransactionid = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Name }
     *     
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Name }
     *     
     */
    public void setName(Name value) {
        this.name = value;
    }

    /**
     * Gets the value of the sendernote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendernote() {
        return sendernote;
    }

    /**
     * Sets the value of the sendernote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendernote(String value) {
        this.sendernote = value;
    }

    /**
     * Gets the value of the receivermessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceivermessage() {
        return receivermessage;
    }

    /**
     * Sets the value of the receivermessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceivermessage(String value) {
        this.receivermessage = value;
    }

    /**
     * Gets the value of the referenceid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceid() {
        return referenceid;
    }

    /**
     * Sets the value of the referenceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceid(String value) {
        this.referenceid = value;
    }

    /**
     * Gets the value of the quoteid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuoteid() {
        return quoteid;
    }

    /**
     * Sets the value of the quoteid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuoteid(String value) {
        this.quoteid = value;
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

