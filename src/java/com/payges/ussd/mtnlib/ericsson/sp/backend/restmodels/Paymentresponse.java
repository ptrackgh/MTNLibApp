
package com.payges.ussd.mtnlib.ericsson.sp.backend.restmodels;

import com.payges.ussd.mtnlib.ericsson.restmodels.Extensiontype;
import com.payges.ussd.mtnlib.ericsson.restmodels.Moneydetailstype;
import com.payges.ussd.mtnlib.ericsson.restmodels.Paymentstatus;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="providertransactionid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="scheduledtransactionid" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="newbalance" type="{http://www.ericsson.com/em/emm/v1_0/common}moneydetailstype" minOccurs="0"/>
 *         &lt;element name="paymenttoken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.ericsson.com/em/emm/serviceprovider/v1_0/common}paymentstatus"/>
 *         &lt;element name="extension" type="{http://www.ericsson.com/em/emm/v1_0/common}extensiontype" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "providertransactionid",
    "scheduledtransactionid",
    "newbalance",
    "paymenttoken",
    "message",
    "status",
    "extension"
})
@XmlRootElement(name = "paymentresponse")
public class Paymentresponse {

    @XmlElement(required = true)
    protected String providertransactionid;
    protected Long scheduledtransactionid;
    protected Moneydetailstype newbalance;
    protected String paymenttoken;
    protected String message;
    @XmlElement(required = true)
    protected Paymentstatus status;
    protected Extensiontype extension;

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
     * Gets the value of the scheduledtransactionid property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getScheduledtransactionid() {
        return scheduledtransactionid;
    }

    /**
     * Sets the value of the scheduledtransactionid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setScheduledtransactionid(Long value) {
        this.scheduledtransactionid = value;
    }

    /**
     * Gets the value of the newbalance property.
     * 
     * @return
     *     possible object is
     *     {@link Moneydetailstype }
     *     
     */
    public Moneydetailstype getNewbalance() {
        return newbalance;
    }

    /**
     * Sets the value of the newbalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Moneydetailstype }
     *     
     */
    public void setNewbalance(Moneydetailstype value) {
        this.newbalance = value;
    }

    /**
     * Gets the value of the paymenttoken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymenttoken() {
        return paymenttoken;
    }

    /**
     * Sets the value of the paymenttoken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymenttoken(String value) {
        this.paymenttoken = value;
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Paymentstatus }
     *     
     */
    public Paymentstatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Paymentstatus }
     *     
     */
    public void setStatus(Paymentstatus value) {
        this.status = value;
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

}
