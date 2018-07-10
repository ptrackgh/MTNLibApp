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
@Table(name = "serials")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Serials.findAll", query = "SELECT s FROM Serials s"),
    @NamedQuery(name = "Serials.findById", query = "SELECT s FROM Serials s WHERE s.id = :id"),
    @NamedQuery(name = "Serials.findBySerial", query = "SELECT s FROM Serials s WHERE s.serial = :serial"),
    @NamedQuery(name = "Serials.findByPin", query = "SELECT s FROM Serials s WHERE s.pin = :pin"),
    @NamedQuery(name = "Serials.findByStatus", query = "SELECT s FROM Serials s WHERE s.status = :status"),
    @NamedQuery(name = "Serials.findByDateloaded", query = "SELECT s FROM Serials s WHERE s.dateloaded = :dateloaded"),
    @NamedQuery(name = "Serials.findByLastupdatetime", query = "SELECT s FROM Serials s WHERE s.lastupdatetime = :lastupdatetime"),
    @NamedQuery(name = "Serials.findByUsedby", query = "SELECT s FROM Serials s WHERE s.usedby = :usedby"),
    @NamedQuery(name = "Serials.findLastTransactionByMsisdn", query = "SELECT s FROM Serials s WHERE s.usedby = :usedby order by s.dateused desc"),
    @NamedQuery(name = "Serials.findByDateused", query = "SELECT s FROM Serials s WHERE s.dateused = :dateused")})
public class Serials implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(max = 15)
    @Column(name = "serial")
    private String serial;
    @Size(max = 15)
    @Column(name = "pin")
    private String pin;
    @Size(max = 9)
    @Column(name = "status")
    private String status;
    @Column(name = "dateloaded")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateloaded;
    @Column(name = "lastupdatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdatetime;
    @Size(max = 15)
    @Column(name = "usedby")
    private String usedby;
    @Column(name = "dateused")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateused;

    public Serials() {
    }

    public Serials(Integer id) {
        this.id = id;
    }

    public Serials(Integer id, Date lastupdatetime) {
        this.id = id;
        this.lastupdatetime = lastupdatetime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateloaded() {
        return dateloaded;
    }

    public void setDateloaded(Date dateloaded) {
        this.dateloaded = dateloaded;
    }

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }

    public String getUsedby() {
        return usedby;
    }

    public void setUsedby(String usedby) {
        this.usedby = usedby;
    }

    public Date getDateused() {
        return dateused;
    }

    public void setDateused(Date dateused) {
        this.dateused = dateused;
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
        if (!(object instanceof Serials)) {
            return false;
        }
        Serials other = (Serials) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payges.ussd.mtnlib.entities.Serials[ id=" + id + " ]";
    }
    
}
