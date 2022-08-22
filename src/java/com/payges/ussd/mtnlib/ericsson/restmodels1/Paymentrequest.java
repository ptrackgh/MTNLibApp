
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
 *         &lt;element name="accountholderid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="receivingfri" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.ericsson.com/em/emm/v1_0/common}moneydetailstype"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extension" type="{http://www.ericsson.com/em/emm/v1_0/common}extensiontype" minOccurs="0"/>
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
    "accountholderid",
    "receivingfri",
    "amount",
    "message",
    "extension"
})
@XmlRootElement(name = "paymentrequest")
public class Paymentrequest {

    protected long transactionid;
    @XmlElement(required = true)
    protected String accountholderid;
    @XmlElement(required = true)
    protected String receivingfri;
    @XmlElement(required = true)
    protected Moneydetailstype amount;
    protected String message;
    protected Extensiontype extension;
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
     * Gets the value of the accountholderid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountholderid() {
        return accountholderid;
    }

    /**
     * Sets the value of the accountholderid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountholderid(String value) {
        this.accountholderid = value;
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
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link Extensiontype }
     *     
     */
    public Extensiontype getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link Extensiontype }
     *     
     */
    public void setExtension(Extensiontype value) {
        this.extension = value;
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
