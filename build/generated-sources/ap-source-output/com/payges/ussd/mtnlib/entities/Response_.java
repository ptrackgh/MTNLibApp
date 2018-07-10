package com.payges.ussd.mtnlib.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-10T07:22:54")
@StaticMetamodel(Response.class)
public class Response_ { 

    public static volatile SingularAttribute<Response, String> application;
    public static volatile SingularAttribute<Response, Short> delivered;
    public static volatile SingularAttribute<Response, Short> status;
    public static volatile SingularAttribute<Response, String> msisdn;
    public static volatile SingularAttribute<Response, String> senderId;
    public static volatile SingularAttribute<Response, String> message;
    public static volatile SingularAttribute<Response, Short> srcNpi;
    public static volatile SingularAttribute<Response, Integer> responseId;
    public static volatile SingularAttribute<Response, Date> receivedTime;
    public static volatile SingularAttribute<Response, Short> flag;
    public static volatile SingularAttribute<Response, Short> requestDelivery;
    public static volatile SingularAttribute<Response, Short> destNpi;
    public static volatile SingularAttribute<Response, Short> errorCode;
    public static volatile SingularAttribute<Response, Integer> messagePriority;
    public static volatile SingularAttribute<Response, String> account;
    public static volatile SingularAttribute<Response, Short> retries;
    public static volatile SingularAttribute<Response, Date> processedTime;
    public static volatile SingularAttribute<Response, Short> destTon;
    public static volatile SingularAttribute<Response, Integer> sequenceNo;
    public static volatile SingularAttribute<Response, String> messageId;
    public static volatile SingularAttribute<Response, Short> srcTon;

}