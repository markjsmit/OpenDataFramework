package OpenData.OpenData.Abstract;

import Helpers.HTTPHelper;
import OpenData.OpenData.Abstract.DataFormat;
import OpenData.OpenData.Annotations.OpenDataRetriever;

/**
 * Created by Mark on 2-2-2015.
 * Hier word de retriever gedefinieerd. Hier vind alle "Magie" plaats met het betrekking tot het ophalen van de data
 * Alle retrievers meoten over erven uit deze klasse.
 * De type parameter is om er voor te zorgen dat er geen typecast bij elk gebruik hoeft te worden uitgevoerd.
 * Deze heeft dus verder geen invloed op de werking. Een typecast bij elk gebruik en Object als type parameter meegeven kan ook
 * Dit is mogelijk omdat de instantie word aangemaakt door annotatie.
 */
public abstract class Retriever<T> {

    //Hier wordt het ophalen van data in verschillende stappen uitgevoerd
   public T RetrieveData()
   {
       //Eerst wordt de url opgehaald
       String url=GetUrl();
       //De data wordt opgehaald
       String data=GetData(url);
       //De data parser word topgehaald
       DataFormat format = GetFormat();
       //En een geparsede versie wordt terug gegeven
       return (T)format.Parse(data,GetClass());
   }

    //deze methode is verantwoordelijk voor het ophalen van de jusite class
    private Class GetClass() {
        //Eerst wordt het type van de class die deze class overerft opgehaald
        Class cls=this.getClass();

        //vervolgens wordt de  open data retriever annotation uitgelezen om daar in te kijken welke class er naar moet worden geconverteerd
        if(cls.isAnnotationPresent(OpenDataRetriever.class)) {
            OpenDataRetriever retrieverInfo = (OpenDataRetriever) cls.getAnnotation(OpenDataRetriever.class);
            return retrieverInfo.MainEntity();
        }
        return null;
    }

    //Simpele wrapper rond de HTTPHelper.
    private String GetData(String url) {
        return HTTPHelper.GetRequest(url);
    }


    //Het formaat van de data ophalen
    private DataFormat GetFormat() {
        //de class van het object die dit object overerft ophalen
        Class cls=this.getClass();

        //Uit de annotatie de class halen die aangeeft welke klasse de retriever heeft
        if(cls.isAnnotationPresent(OpenDataRetriever.class)) {
            OpenDataRetriever retrieverInfo = (OpenDataRetriever) cls.getAnnotation(OpenDataRetriever.class);
            try {
                //en met reflectie een nieuwe instantie aanmaken van de format provider
                return retrieverInfo.Format().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;

    }

    //deze methode haalt de url op
    private String GetUrl(){

        //de class van het object die dit object overerft ophalen
        Class cls=this.getClass();

        //url leeg ohalen
        String url="";
        if(cls.isAnnotationPresent(OpenDataRetriever.class)) {
            //de annotatie ophalen waar de informatie in opgeslagen staat
            OpenDataRetriever retrieverInfo= (OpenDataRetriever)cls.getAnnotation(OpenDataRetriever.class);
            try {
                //Een instantie aanmaken van de juiste Url parser
                UrlParser parser = (UrlParser)retrieverInfo.UrlParser().newInstance();

                //en de parse functie uitvoeren op de instantie.
                url=parser.Parse(this);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //de uiteindelijke url returnen
        return url;
    }



}
