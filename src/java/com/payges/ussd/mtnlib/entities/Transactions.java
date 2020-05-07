/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ptrack
 */
@Entity
@Table(name = "transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t"),
    @NamedQuery(name = "Transactions.findById", query = "SELECT t FROM Transactions t WHERE t.id = :id"),
    @NamedQuery(name = "Transactions.findByMsisdn", query = "SELECT t FROM Transactions t WHERE t.msisdn = :msisdn"),
    @NamedQuery(name = "Transactions.findLastTransactionByMsisdn", query = "SELECT t FROM Transactions t WHERE t.msisdn = :msisdn order by t.id desc"),
    @NamedQuery(name = "Transactions.findByCurrency", query = "SELECT t FROM Transactions t WHERE t.currency = :currency"),
    @NamedQuery(name = "Transactions.findByAmount", query = "SELECT t FROM Transactions t WHERE t.amount = :amount"),
    @NamedQuery(name = "Transactions.findByStatus", query = "SELECT t FROM Transactions t WHERE t.status = :status"),
    @NamedQuery(name = "Transactions.findByRequestdate", query = "SELECT t FROM Transactions t WHERE t.requestdate = :requestdate"),
    @NamedQuery(name = "Transactions.findByTransactionid", query = "SELECT t FROM Transactions t WHERE t.status='PENDING' AND  t.transactionid = :transactionid"),
    @NamedQuery(name = "Transactions.findByLastresponsedate", query = "SELECT t FROM Transactions t WHERE t.lastresponsedate = :lastresponsedate"),
    @NamedQuery(name = "Transactions.findByExternaltransactionid", query = "SELECT t FROM Transactions t WHERE t.externaltransactionid = :externaltransactionid"),
    @NamedQuery(name = "Transactions.findByRetries", query = "SELECT t FROM Transactions t WHERE t.retries = :retries"),
    @NamedQuery(name = "Transactions.findByExchangerate", query = "SELECT t FROM Transactions t WHERE t.exchangerate = :exchangerate")})
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(max = 12)
    @Column(name = "msisdn")
    private String msisdn;
    @Size(max = 5)
    @Column(name = "currency")
    private String currency;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Size(max = 15)
    @Column(name = "status")
    private String status;
    @Size(max = 300)
    @Column(name = "statusdesc")
    private String statusdesc;
    @Size(max = 10)
    @Column(name = "momostatus")
    private String momostatus;
    @Size(max = 50)
    @Column(name = "traceid")
    private String traceid;
    @Column(name = "requestdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestdate;
    @Column(name = "transactionid")
    private String transactionid;
    @Column(name = "lastresponsedate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastresponsedate;
    @Size(max = 50)
    @Column(name = "externaltransactionid")
    private String externaltransactionid;
    @Column(name = "pintype")
    private String pintype;
    @Column(name = "retries")
    private Integer retries;
    @Column(name = "exchangerate")
    private Double exchangerate;

    public Transactions() {
    }

    public Transactions(Integer id) {
        this.id = id;
    }

    public Transactions(Integer id, Date lastresponsedate) {
        this.id = id;
        this.lastresponsedate = lastresponsedate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(Date requestdate) {
        this.requestdate = requestdate;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public Date getLastresponsedate() {
        return lastresponsedate;
    }

    public void setLastresponsedate(Date lastresponsedate) {
        this.lastresponsedate = lastresponsedate;
    }

    public String getExternaltransactionid() {
        return externaltransactionid;
    }

    public void setExternaltransactionid(String externaltransactionid) {
        this.externaltransactionid = externaltransactionid;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public Double getExchangerate() {
        return exchangerate;
    }

    public void setExchangerate(Double exchangerate) {
        this.exchangerate = exchangerate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * @return the statusdesc
     */
    public String getStatusdesc() {
        return statusdesc;
    }

    /**
     * @param statusdesc the statusdesc to set
     */
    public void setStatusdesc(String statusdesc) {
        this.statusdesc = statusdesc;
    }

    /**
     * @return the traceid
     */
    public String getTraceid() {
        return traceid;
    }

    /**
     * @param traceid the traceid to set
     */
    public void setTraceid(String traceid) {
        this.traceid = traceid;
    }

    /**
     * @return the momostatus
     */
    public String getMomostatus() {
        return momostatus;
    }

    /**
     * @param momostatus the momostatus to set
     */
    public void setMomostatus(String momostatus) {
        this.momostatus = momostatus;
    }

    public String getPintype() {
        return pintype;
    }

    public void setPintype(String pintype) {
        this.pintype = pintype;
    }

    @Override
    public String toString() {
        return "Transactions{" + "id=" + id + ", msisdn=" + msisdn + ", currency=" + currency + ", amount=" + amount + ", status=" + status + ", statusdesc=" + statusdesc + ", momostatus=" + momostatus + ", traceid=" + traceid + ", requestdate=" + requestdate + ", transactionid=" + transactionid + ", lastresponsedate=" + lastresponsedate + ", externaltransactionid=" + externaltransactionid + ", pintype=" + pintype + ", retries=" + retries + ", exchangerate=" + exchangerate + '}';
    }
}
