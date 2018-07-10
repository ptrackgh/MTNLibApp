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
@Table(name = "requests")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Requests.findAll", query = "SELECT r FROM Requests r"),
    @NamedQuery(name = "Requests.findByRequestId", query = "SELECT r FROM Requests r WHERE r.requestId = :requestId"),
    @NamedQuery(name = "Requests.findBySequenceNo", query = "SELECT r FROM Requests r WHERE r.sequenceNo = :sequenceNo"),
    @NamedQuery(name = "Requests.findByMsisdn", query = "SELECT r FROM Requests r WHERE r.msisdn = :msisdn"),
    @NamedQuery(name = "Requests.findByShortCode", query = "SELECT r FROM Requests r WHERE r.shortCode = :shortCode"),
    @NamedQuery(name = "Requests.findByMessage", query = "SELECT r FROM Requests r WHERE r.message = :message"),
    @NamedQuery(name = "Requests.findByMessageId", query = "SELECT r FROM Requests r WHERE r.messageId = :messageId"),
    @NamedQuery(name = "Requests.findByAccount", query = "SELECT r FROM Requests r WHERE r.account = :account"),
    @NamedQuery(name = "Requests.findByStatus", query = "SELECT r FROM Requests r WHERE r.status = :status"),
    @NamedQuery(name = "Requests.findByReceivedTime", query = "SELECT r FROM Requests r WHERE r.receivedTime = :receivedTime"),
    @NamedQuery(name = "Requests.findByProcessedTime", query = "SELECT r FROM Requests r WHERE r.processedTime = :processedTime"),
    @NamedQuery(name = "Requests.findByRead", query = "SELECT r FROM Requests r WHERE r.read = :read")})
public class Requests implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestId")
    private Integer requestId;
    @Column(name = "sequenceNo")
    private Integer sequenceNo;
    @Size(max = 15)
    @Column(name = "msisdn")
    private String msisdn;
    @Size(max = 10)
    @Column(name = "shortCode")
    private String shortCode;
    @Size(max = 255)
    @Column(name = "message")
    private String message;
    @Size(max = 20)
    @Column(name = "messageId")
    private String messageId;
    @Size(max = 20)
    @Column(name = "account")
    private String account;
    @Column(name = "status")
    private Integer status;
    @Column(name = "receivedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedTime;
    @Column(name = "processedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedTime;
    @Size(min = 1, max = 2)
    @Column(name = "read")
    private String read;

    public Requests() {
    }

    public Requests(Integer requestId) {
        this.requestId = requestId;
    }

    public Requests(Integer requestId, Date receivedTime, Date processedTime, String read) {
        this.requestId = requestId;
        this.receivedTime = receivedTime;
        this.processedTime = processedTime;
        this.read = read;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Date getProcessedTime() {
        return processedTime;
    }

    public void setProcessedTime(Date processedTime) {
        this.processedTime = processedTime;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestId != null ? requestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Requests)) {
            return false;
        }
        Requests other = (Requests) object;
        if ((this.requestId == null && other.requestId != null) || (this.requestId != null && !this.requestId.equals(other.requestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payges.ussd.mtnlib.entities.Requests[ requestId=" + requestId + " ]";
    }
    
}
