package com.payges.ussd.mtnlib.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-11T17:05:00")
@StaticMetamodel(Serials.class)
public class Serials_ { 

    public static volatile SingularAttribute<Serials, Integer> id;
    public static volatile SingularAttribute<Serials, Date> lastupdatetime;
    public static volatile SingularAttribute<Serials, String> pin;
    public static volatile SingularAttribute<Serials, String> status;
    public static volatile SingularAttribute<Serials, String> usedby;
    public static volatile SingularAttribute<Serials, Date> dateloaded;
    public static volatile SingularAttribute<Serials, String> serial;
    public static volatile SingularAttribute<Serials, Date> dateused;

}