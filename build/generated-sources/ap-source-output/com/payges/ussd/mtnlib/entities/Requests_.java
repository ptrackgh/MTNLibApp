package com.payges.ussd.mtnlib.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-11T17:05:00")
@StaticMetamodel(Requests.class)
public class Requests_ { 

    public static volatile SingularAttribute<Requests, String> message;
    public static volatile SingularAttribute<Requests, Date> receivedTime;
    public static volatile SingularAttribute<Requests, String> shortCode;
    public static volatile SingularAttribute<Requests, Integer> requestId;
    public static volatile SingularAttribute<Requests, Integer> status;
    public static volatile SingularAttribute<Requests, String> msisdn;
    public static volatile SingularAttribute<Requests, String> account;
    public static volatile SingularAttribute<Requests, String> read;
    public static volatile SingularAttribute<Requests, Integer> sequenceNo;
    public static volatile SingularAttribute<Requests, Date> processedTime;
    public static volatile SingularAttribute<Requests, String> messageId;

}