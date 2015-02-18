package OpenData.OpenData.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Mark on 1-2-2015.
 *Deze annotatie dient om aan te geven dat dit veld moet worden gekoppeld aan de teruggegeven data.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OpenDataField {

    //de naam in de data waaraan dit veld moet worden gekoppeld
    String Name();
}
