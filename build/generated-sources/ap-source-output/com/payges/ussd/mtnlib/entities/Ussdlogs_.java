package com.payges.ussd.mtnlib.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-10T07:22:54")
@StaticMetamodel(Ussdlogs.class)
public class Ussdlogs_ { 

    public static volatile SingularAttribute<Ussdlogs, Integer> id;
    public static volatile SingularAttribute<Ussdlogs, Date> requestdate;
    public static volatile SingularAttribute<Ussdlogs, Integer> msisdn;
    public static volatile SingularAttribute<Ussdlogs, Integer> userdate;

}