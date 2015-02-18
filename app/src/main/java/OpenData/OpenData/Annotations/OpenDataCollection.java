package OpenData.OpenData.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import Datasets.Datasets.Postcode.Details;

/**
 * Created by Mark on 1-2-2015.
 * hier worden de parameters voor de opendatacollection annotatie mee gegeven.
 * Deze annotatie dient om aan te geven dat een veld om moet worden gezet naar een list
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OpenDataCollection {
    //Aan welk veld er moet worden gekoppeld
    String Name();
    //van welke class er een instantie moet worden aangemaakt voor elk item
    Class Type();
}
