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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ptrack
 */
@Entity
@Table(name = "ussdlogs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ussdlogs.findAll", query = "SELECT u FROM Ussdlogs u"),
    @NamedQuery(name = "Ussdlogs.findById", query = "SELECT u FROM Ussdlogs u WHERE u.id = :id"),
    @NamedQuery(name = "Ussdlogs.findByMsisdn", query = "SELECT u FROM Ussdlogs u WHERE u.msisdn = :msisdn"),
    @NamedQuery(name = "Ussdlogs.findByRequestdate", query = "SELECT u FROM Ussdlogs u WHERE u.requestdate = :requestdate"),
    @NamedQuery(name = "Ussdlogs.findByUseroperation", query = "SELECT u FROM Ussdlogs u WHERE u.useroperation = :useroperation")})
public class Ussdlogs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "msisdn")
    private String msisdn;
    @Column(name = "requestdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestdate;
    @Column(name = "useroperation")
    private String useroperation;
    @Column(name = "status")
    private String status;

    public Ussdlogs() {
    }

    public Ussdlogs(Integer id) {
        this.id = id;
    }

    public Ussdlogs(Integer id, Date requestdate) {
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

    public Date getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(Date requestdate) {
        this.requestdate = requestdate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ussdlogs)) {
            return false;
        }
        Ussdlogs other = (Ussdlogs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payges.ussd.mtnlib.entities.Ussdlogs[ id=" + id + " ]";
    }

    /**
     * @return the useroperation
     */
    public String getUseroperation() {
        return useroperation;
    }

    /**
     * @param useroperation the useroperation to set
     */
    public void setUseroperation(String useroperation) {
        this.useroperation = useroperation;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
}
