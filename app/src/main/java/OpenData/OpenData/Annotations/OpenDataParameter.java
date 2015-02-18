package OpenData.OpenData.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Mark on 1-2-2015.
 *Deze annotatie dient om aan te geven dat dit veld moet worden gekoppeld aan een parameter in de url
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OpenDataParameter {
    //aan welk veld in de url dit moet worden gekoppeld
    String BindTo();
}
