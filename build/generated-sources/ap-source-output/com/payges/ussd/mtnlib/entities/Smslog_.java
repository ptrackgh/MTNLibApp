package com.payges.ussd.mtnlib.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-10T07:22:54")
@StaticMetamodel(Smslog.class)
public class Smslog_ { 

    public static volatile SingularAttribute<Smslog, Integer> id;
    public static volatile SingularAttribute<Smslog, String> message;
    public static volatile SingularAttribute<Smslog, Date> requestdate;
    public static volatile SingularAttribute<Smslog, String> reason;
    public static volatile SingularAttribute<Smslog, String> msisdn;
    public static volatile SingularAttribute<Smslog, Integer> transactionid;

}