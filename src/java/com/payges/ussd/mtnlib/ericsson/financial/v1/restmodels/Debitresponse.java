
package com.payges.ussd.mtnlib.ericsson.financial.v1.restmodels;

import com.payges.ussd.mtnlib.ericsson.restmodels.Financialtransactionstatus;
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
 *         &lt;element name="transactionid" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="status" type="{http://www.ericsson.com/em/emm/v1_0/common}financialtransactionstatus"/>
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
    "transactionid",
    "status"
})
@XmlRootElement(name = "debitresponse")
public class Debitresponse {

    protected long transactionid;
    @XmlElement(required = true)
    protected Financialtransactionstatus status;

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

}
