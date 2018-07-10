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
import javax.persistence.Lob;
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
@Table(name = "response")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Response.findAll", query = "SELECT r FROM Response r"),
    @NamedQuery(name = "Response.findByResponseId", query = "SELECT r FROM Response r WHERE r.responseId = :responseId"),
    @NamedQuery(name = "Response.findBySenderId", query = "SELECT r FROM Response r WHERE r.senderId = :senderId"),
    @NamedQuery(name = "Response.findByMsisdn", query = "SELECT r FROM Response r WHERE r.msisdn = :msisdn"),
    @NamedQuery(name = "Response.findByStatus", query = "SELECT r FROM Response r WHERE r.status = :status"),
    @NamedQuery(name = "Response.findByApplication", query = "SELECT r FROM Response r WHERE r.application = :application"),
    @NamedQuery(name = "Response.findByProcessedTime", query = "SELECT r FROM Response r WHERE r.processedTime = :processedTime"),
    @NamedQuery(name = "Response.findByReceivedTime", query = "SELECT r FROM Response r WHERE r.receivedTime = :receivedTime"),
    @NamedQuery(name = "Response.findByFlag", query = "SELECT r FROM Response r WHERE r.flag = :flag"),
    @NamedQuery(name = "Response.findByRequestDelivery", query = "SELECT r FROM Response r WHERE r.requestDelivery = :requestDelivery"),
    @NamedQuery(name = "Response.findByDelivered", query = "SELECT r FROM Response r WHERE r.delivered = :delivered"),
    @NamedQuery(name = "Response.findByAccount", query = "SELECT r FROM Response r WHERE r.account = :account"),
    @NamedQuery(name = "Response.findBySrcTon", query = "SELECT r FROM Response r WHERE r.srcTon = :srcTon"),
    @NamedQuery(name = "Response.findBySrcNpi", query = "SELECT r FROM Response r WHERE r.srcNpi = :srcNpi"),
    @NamedQuery(name = "Response.findByDestTon", query = "SELECT r FROM Response r WHERE r.destTon = :destTon"),
    @NamedQuery(name = "Response.findByDestNpi", query = "SELECT r FROM Response r WHERE r.destNpi = :destNpi"),
    @NamedQuery(name = "Response.findByErrorCode", query = "SELECT r FROM Response r WHERE r.errorCode = :errorCode"),
    @NamedQuery(name = "Response.findByMessageId", query = "SELECT r FROM Response r WHERE r.messageId = :messageId"),
    @NamedQuery(name = "Response.findBySequenceNo", query = "SELECT r FROM Response r WHERE r.sequenceNo = :sequenceNo"),
    @NamedQuery(name = "Response.findByRetries", query = "SELECT r FROM Response r WHERE r.retries = :retries"),
    @NamedQuery(name = "Response.findByMessagePriority", query = "SELECT r FROM Response r WHERE r.messagePriority = :messagePriority")})
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "responseId")
    private Integer responseId;
    @Size(max = 15)
    @Column(name = "senderId")
    private String senderId;
    @Size(max = 15)
    @Column(name = "msisdn")
    private String msisdn;
    @Lob
    @Size(max = 65535)
    @Column(name = "message")
    private String message;
    @Column(name = "status")
    private Short status;
    @Size(max = 50)
    @Column(name = "application")
    private String application;
    @Column(name = "processedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedTime;
    @Column(name = "receivedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedTime;
    @Column(name = "flag")
    private Short flag;
    @Column(name = "requestDelivery")
    private Short requestDelivery;
    @Column(name = "delivered")
    private Short delivered;
    @Size(max = 20)
    @Column(name = "account")
    private String account;
    @Column(name = "srcTon")
    private Short srcTon;
    @Column(name = "srcNpi")
    private Short srcNpi;
    @Column(name = "destTon")
    private Short destTon;
    @Column(name = "destNpi")
    private Short destNpi;
    @Column(name = "errorCode")
    private Short errorCode;
    @Size(max = 50)
    @Column(name = "messageId")
    private String messageId;
    @Column(name = "sequenceNo")
    private Integer sequenceNo;
    @Column(name = "retries")
    private Short retries;
    @Column(name = "messagePriority")
    private int messagePriority;

    public Response() {
    }

    public Response(Integer responseId) {
        this.responseId = responseId;
    }

    public Response(Integer responseId, Date processedTime, Date receivedTime, int messagePriority) {
        this.responseId = responseId;
        this.processedTime = processedTime;
        this.receivedTime = receivedTime;
        this.messagePriority = messagePriority;
    }

    public Integer getResponseId() {
        return responseId;
    }

    public void setResponseId(Integer responseId) {
        this.responseId = responseId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Date getProcessedTime() {
        return processedTime;
    }

    public void setProcessedTime(Date processedTime) {
        this.processedTime = processedTime;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Short getFlag() {
        return flag;
    }

    public void setFlag(Short flag) {
        this.flag = flag;
    }

    public Short getRequestDelivery() {
        return requestDelivery;
    }

    public void setRequestDelivery(Short requestDelivery) {
        this.requestDelivery = requestDelivery;
    }

    public Short getDelivered() {
        return delivered;
    }

    public void setDelivered(Short delivered) {
        this.delivered = delivered;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Short getSrcTon() {
        return srcTon;
    }

    public void setSrcTon(Short srcTon) {
        this.srcTon = srcTon;
    }

    public Short getSrcNpi() {
        return srcNpi;
    }

    public void setSrcNpi(Short srcNpi) {
        this.srcNpi = srcNpi;
    }

    public Short getDestTon() {
        return destTon;
    }

    public void setDestTon(Short destTon) {
        this.destTon = destTon;
    }

    public Short getDestNpi() {
        return destNpi;
    }

    public void setDestNpi(Short destNpi) {
        this.destNpi = destNpi;
    }

    public Short getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Short errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Short getRetries() {
        return retries;
    }

    public void setRetries(Short retries) {
        this.retries = retries;
    }

    public int getMessagePriority() {
        return messagePriority;
    }

    public void setMessagePriority(int messagePriority) {
        this.messagePriority = messagePriority;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (responseId != null ? responseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Response)) {
            return false;
        }
        Response other = (Response) object;
        if ((this.responseId == null && other.responseId != null) || (this.responseId != null && !this.responseId.equals(other.responseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payges.ussd.mtnlib.entities.Response[ responseId=" + responseId + " ]";
    }
    
}
