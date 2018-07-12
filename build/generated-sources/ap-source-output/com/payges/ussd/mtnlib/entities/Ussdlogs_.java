package com.payges.ussd.mtnlib.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-11T17:05:00")
@StaticMetamodel(Ussdlogs.class)
public class Ussdlogs_ { 

    public static volatile SingularAttribute<Ussdlogs, Integer> id;
    public static volatile SingularAttribute<Ussdlogs, Date> requestdate;
    public static volatile SingularAttribute<Ussdlogs, String> status;
    public static volatile SingularAttribute<Ussdlogs, String> msisdn;
    public static volatile SingularAttribute<Ussdlogs, String> useroperation;

}