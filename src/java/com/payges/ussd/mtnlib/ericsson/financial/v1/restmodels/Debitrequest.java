
package com.payges.ussd.mtnlib.ericsson.financial.v1.restmodels;

import com.payges.ussd.mtnlib.ericsson.restmodels.Moneydetailstype;
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
 *         &lt;element name="fromfri" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tofri" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.ericsson.com/em/emm/v1_0/common}moneydetailstype"/>
 *         &lt;element name="externaltransactionid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="frommessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tomessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quoteid" type="{http://www.ericsson.com/em/emm/v1_0/common}quoteid" minOccurs="0"/>
 *         &lt;element name="coupon" type="{http://www.ericsson.com/em/emm/coupons/v1_0/common}coupon" minOccurs="0"/>
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
    "fromfri",
    "tofri",
    "amount",
    "externaltransactionid",
    "frommessage",
    "tomessage",
    "referenceid",
    "quoteid",
    "coupon"
})
@XmlRootElement(name = "debitrequest")
public class Debitrequest {

    @XmlElement(required = true)
    protected String fromfri;
    @XmlElement(required = true)
    protected String tofri;
    @XmlElement(required = true)
    protected Moneydetailstype amount;
    @XmlElement(required = true)
    protected String externaltransactionid;
    protected String frommessage;
    protected String tomessage;
    protected String referenceid;
    protected String quoteid;
    protected String coupon;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Gets the value of the fromfri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromfri() {
        return fromfri;
    }

    /**
     * Sets the value of the fromfri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromfri(String value) {
        this.fromfri = value;
    }

    /**
     * Gets the value of the tofri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTofri() {
        return tofri;
    }

    /**
     * Sets the value of the tofri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTofri(String value) {
        this.tofri = value;
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
     * Gets the value of the frommessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrommessage() {
        return frommessage;
    }

    /**
     * Sets the value of the frommessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrommessage(String value) {
        this.frommessage = value;
    }

    /**
     * Gets the value of the tomessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTomessage() {
        return tomessage;
    }

    /**
     * Sets the value of the tomessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTomessage(String value) {
        this.tomessage = value;
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
     * Gets the value of the coupon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoupon() {
        return coupon;
    }

    /**
     * Sets the value of the coupon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoupon(String value) {
        this.coupon = value;
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
