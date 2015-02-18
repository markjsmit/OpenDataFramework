package OpenData.OpenData.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by Mark on 1-2-2015.
 * Deze annotatie dient om aan te geven dat dit een open data entiteit is. met deze annotatie wordt niets gedaan, maar kan in de toekomst wel worden gebruikt als controlemiddel.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OpenDataEntity {
}
