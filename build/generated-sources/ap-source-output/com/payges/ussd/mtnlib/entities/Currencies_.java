package com.payges.ussd.mtnlib.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-11T17:05:00")
@StaticMetamodel(Currencies.class)
public class Currencies_ { 

    public static volatile SingularAttribute<Currencies, Integer> id;
    public static volatile SingularAttribute<Currencies, Date> lastupdatedate;
    public static volatile SingularAttribute<Currencies, Date> dateadded;
    public static volatile SingularAttribute<Currencies, String> currencyname;
    public static volatile SingularAttribute<Currencies, String> currencycode;
    public static volatile SingularAttribute<Currencies, Double> exchangerate;

}