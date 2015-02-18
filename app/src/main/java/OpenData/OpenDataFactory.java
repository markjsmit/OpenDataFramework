package OpenData;

import OpenData.OpenData.Abstract.Retriever;

/**
 * Created by Mark on 1-2-2015.
 * Deze class dient als factory om een retriever aan te maken. Hier is in het begin gekozen om een factory te gebruiken
 * Dit omdat er werd verwacht dat er meer achter de instnatiatie weg zou komen dan enkel de jusite constructor aanroepen.
 * Het bleek echter niet het geval te zijn. 
 */
public class OpenDataFactory {

    public static Retriever GetRetriever(Class<? extends  Retriever> cls){

        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
