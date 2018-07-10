/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ptrack
 */
@Entity
@Table(name = "currencies")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Currencies.findAll", query = "SELECT c FROM Currencies c"),
    @NamedQuery(name = "Currencies.findById", query = "SELECT c FROM Currencies c WHERE c.id = :id"),
    @NamedQuery(name = "Currencies.findByCurrencyname", query = "SELECT c FROM Currencies c WHERE c.currencyname = :currencyname"),
    @NamedQuery(name = "Currencies.findByCurrencycode", query = "SELECT c FROM Currencies c WHERE c.currencycode = :currencycode"),
    @NamedQuery(name = "Currencies.findByExchangerate", query = "SELECT c FROM Currencies c WHERE c.exchangerate = :exchangerate"),
    @NamedQuery(name = "Currencies.findByLastupdatedate", query = "SELECT c FROM Currencies c WHERE c.lastupdatedate = :lastupdatedate"),
    @NamedQuery(name = "Currencies.findByDateadded", query = "SELECT c FROM Currencies c WHERE c.dateadded = :dateadded")})
public class Currencies implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "currencyname")
    private String currencyname;
    @Size(max = 3)
    @Column(name = "currencycode")
    private String currencycode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "exchangerate")
    private Double exchangerate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lastupdatedate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdatedate;
    @Column(name = "dateadded")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateadded;

    public Currencies() {
    }

    public Currencies(Integer id) {
        this.id = id;
    }

    public Currencies(Integer id, Date lastupdatedate) {
        this.id = id;
        this.lastupdatedate = lastupdatedate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrencyname() {
        return currencyname;
    }

    public void setCurrencyname(String currencyname) {
        this.currencyname = currencyname;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }

    public Double getExchangerate() {
        return exchangerate;
    }

    public void setExchangerate(Double exchangerate) {
        this.exchangerate = exchangerate;
    }

    public Date getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(Date lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
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
        if (!(object instanceof Currencies)) {
            return false;
        }
        Currencies other = (Currencies) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payges.ussd.mtnlib.entities.Currencies[ id=" + id + " ]";
    }
    
}
