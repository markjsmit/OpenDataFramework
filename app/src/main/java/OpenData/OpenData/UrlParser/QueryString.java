package OpenData.OpenData.UrlParser;

import java.lang.reflect.Field;

import OpenData.OpenData.Abstract.Retriever;
import OpenData.OpenData.Abstract.UrlParser;
import OpenData.OpenData.Annotations.OpenDataParameter;
import OpenData.OpenData.Annotations.OpenDataRetriever;

/**
 * Created by Mark on 2-2-2015.
 */
public class QueryString extends UrlParser {

    public QueryString(){}

    @Override
    public String Parse(Retriever retriever) {
       Field[] fields= retriever.getClass().getFields();
        String url="";
        if(retriever.getClass().isAnnotationPresent(OpenDataRetriever.class)){
            OpenDataRetriever retrieverInfo= (OpenDataRetriever)retriever.getClass().getAnnotation(OpenDataRetriever.class);
            url+=retrieverInfo.Url();
        }
        int i=0;
        for(Field field :fields){
            if(field.isAnnotationPresent(OpenDataParameter.class)) {
                OpenDataParameter annotation = (OpenDataParameter) field.getAnnotation(OpenDataParameter.class);
                String bindTo= annotation.BindTo();
                try {
                    if(i==0){
                        url+="?";
                    }else{
                        url+="&";
                    }
                    url+=bindTo+"="+field.get(retriever).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        return url;
    }
}
