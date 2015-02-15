package OpenData.OpenData.Annotations;

import java.lang.annotation.*;

import OpenData.OpenData.Abstract.DataFormat;
import OpenData.OpenData.Abstract.UrlParser;


/**
 * Created by Mark on 1-2-2015.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OpenDataRetriever {
      String Url();

     Class<? extends DataFormat> Format();
    Class<? extends UrlParser> UrlParser();


    Class MainEntity();
}
