package OpenData.OpenData.Annotations;

import java.lang.annotation.*;

import OpenData.OpenData.Abstract.DataFormat;
import OpenData.OpenData.Abstract.UrlParser;


/**
 * Created by Mark on 1-2-2015.
 * Deze annoatie bevat de informatie die nodig is om de jusite data op te halen
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OpenDataRetriever {

    //de url waar het vandaan  moet komen
      String Url();
    //het formaat waarin het wordt aangeleverd
     Class<? extends DataFormat> Format();

    //welke url parser er moet worden gebruikt
    Class<? extends UrlParser> UrlParser();

    //in welk object dit moet worden gegoten
    Class MainEntity();
}
