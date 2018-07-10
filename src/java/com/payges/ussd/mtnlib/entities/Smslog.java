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
@Table(name = "smslog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Smslog.findAll", query = "SELECT s FROM Smslog s"),
    @NamedQuery(name = "Smslog.findById", query = "SELECT s FROM Smslog s WHERE s.id = :id"),
    @NamedQuery(name = "Smslog.findByMsisdn", query = "SELECT s FROM Smslog s WHERE s.msisdn = :msisdn"),
    @NamedQuery(name = "Smslog.findByTransactionid", query = "SELECT s FROM Smslog s WHERE s.transactionid = :transactionid"),
    @NamedQuery(name = "Smslog.findByMessage", query = "SELECT s FROM Smslog s WHERE s.message = :message"),
    @NamedQuery(name = "Smslog.findByRequestdate", query = "SELECT s FROM Smslog s WHERE s.requestdate = :requestdate"),
    @NamedQuery(name = "Smslog.findByReason", query = "SELECT s FROM Smslog s WHERE s.reason = :reason")})
public class Smslog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(max = 15)
    @Column(name = "msisdn")
    private String msisdn;
    @Column(name = "transactionid")
    private Integer transactionid;
    @Size(max = 500)
    @Column(name = "message")
    private String message;
    @Column(name = "requestdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestdate;
    @Size(max = 500)
    @Column(name = "reason")
    private String reason;

    public Smslog() {
    }

    public Smslog(Integer id) {
        this.id = id;
    }

    public Smslog(Integer id, Date requestdate) {
        this.id = id;
        this.requestdate = requestdate;
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

    public Integer getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(Integer transactionid) {
        this.transactionid = transactionid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(Date requestdate) {
        this.requestdate = requestdate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        if (!(object instanceof Smslog)) {
            return false;
        }
        Smslog other = (Smslog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payges.ussd.mtnlib.entities.Smslog[ id=" + id + " ]";
    }
    
}
