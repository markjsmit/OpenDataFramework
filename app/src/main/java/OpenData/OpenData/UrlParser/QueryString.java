package OpenData.OpenData.UrlParser;

import java.lang.reflect.Field;

import OpenData.OpenData.Abstract.Retriever;
import OpenData.OpenData.Abstract.UrlParser;
import OpenData.OpenData.Annotations.OpenDataParameter;
import OpenData.OpenData.Annotations.OpenDataRetriever;

/**
 * Created by Mark on 2-2-2015.
 * deze class veranderd de data retriever url in een url met een querystring
 */
public class QueryString extends UrlParser {


    public QueryString(){}

    @Override
    public String Parse(Retriever retriever) {

        //alle velden uit de retriever worden opgehaald
       Field[] fields= retriever.getClass().getFields();

        //er word een url aangemaakt
        String url="";

        //de basis ( het begin van de url) wordt in de url variable geplaatst
        if(retriever.getClass().isAnnotationPresent(OpenDataRetriever.class)){
            OpenDataRetriever retrieverInfo= (OpenDataRetriever)retriever.getClass().getAnnotation(OpenDataRetriever.class);
            url+=retrieverInfo.Url();
        }
        int i=0;

        //alle velden worden dorogelopen
        for(Field field :fields){
            //als dit veld ook daadwerkelijk als filter moet dienen
            if(field.isAnnotationPresent(OpenDataParameter.class)) {

                //wordt de veld informatie opgehaald.
                OpenDataParameter annotation = (OpenDataParameter) field.getAnnotation(OpenDataParameter.class);
                //en bepaald aan welke url parameter hij moet worden gekoppeld
                String bindTo= annotation.BindTo();

                try {
                    //als dit de eerste itteratie is beginnen we met een ?
                    if(i==0){
                        url+="?";
                    }else{
                        //alle andere parameters worden gescheiden met een & teken
                        url+="&";
                    }

                    //vervolgens wordt de parameter aan de url toegevoegd
                    url+=bindTo+"="+field.get(retriever).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                i++;
            }

        }
        return url;
    }
}
