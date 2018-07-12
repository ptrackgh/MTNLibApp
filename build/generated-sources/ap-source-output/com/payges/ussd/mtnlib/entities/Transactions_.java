package com.payges.ussd.mtnlib.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-11T17:05:00")
@StaticMetamodel(Transactions.class)
public class Transactions_ { 

    public static volatile SingularAttribute<Transactions, Integer> id;
    public static volatile SingularAttribute<Transactions, Double> amount;
    public static volatile SingularAttribute<Transactions, Date> requestdate;
    public static volatile SingularAttribute<Transactions, String> status;
    public static volatile SingularAttribute<Transactions, String> msisdn;
    public static volatile SingularAttribute<Transactions, Integer> transactionid;
    public static volatile SingularAttribute<Transactions, Date> lastresponsedate;
    public static volatile SingularAttribute<Transactions, Integer> retries;
    public static volatile SingularAttribute<Transactions, String> externaltransactionid;
    public static volatile SingularAttribute<Transactions, String> currency;
    public static volatile SingularAttribute<Transactions, Double> exchangerate;

}