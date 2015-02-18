package OpenData.OpenData.Formats;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import OpenData.OpenData.Abstract.DataFormat;
import OpenData.OpenData.Annotations.OpenDataCollection;
import OpenData.OpenData.Annotations.OpenDataEntity;
import OpenData.OpenData.Annotations.OpenDataField;

/**
 * Created by Mark on 1-2-2015.
 * Deze class dient voor het omzetten van json data naar een werkelijk object
 */

public class JSON extends DataFormat {

    public JSON(){}

    @Override
    public Object Parse(String input,Class cls) {
        try {
            //eerst een instanrie van de class aanmaken
            Object instance = cls.newInstance();

            //Json object aanmaken
            JSONObject mainObj=new JSONObject(input);

            //en de instantie invullen met het json object
            FillObject(instance, mainObj);
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void FillObject(Object instance, JSONObject data){
        //De class van de instantie ophalen
        Class cls=instance.getClass();

        //alle velden van het object doorlopen ( geen lamba expressions mogelijk :( )
        for(Field field: cls.getFields()) {

            //Als het een data veld is en gevuld moet worden.
            if(field.isAnnotationPresent(OpenDataField.class)){
                //het veld vullen met de data, voor deze specivieke instantie
                FillField(instance,field,data);
            }

            //als het een hele lijst is en gevuld moet worden
            if(field.isAnnotationPresent(OpenDataCollection.class)){
                //wordt het wat ingewikkelder. daarom daarom is dit in een apparte methode gezet anders dan veld.
                FillList(instance,field,data);
            }
        }
    }


    //methode om een lijst te vullen
    private void FillList(Object instance, Field field, JSONObject data) {

        //Eerst wordt de veldinformatie opgheaald voro het veld waar het in moet komen.
        OpenDataCollection fieldInfo=field.getAnnotation(OpenDataCollection.class);
        String fieldName= fieldInfo.Name();

        //Er wordt een nieuwe Arraylist instantie aangemaakt.
        ArrayList list = new ArrayList();
        try {
            //Er wordt een json array opgehaald uit de jsondata aan de hand van de veld naam annotatie
            JSONArray jsonArray=data.getJSONArray(fieldName);

            //alle items in deze jsonarrray worden doorgelopen
            for(int i=0 ; i< jsonArray.length();i++){

                //er wordt een nieuw json object aangemaakt voor dit specifieke item in de array
                JSONObject obj = jsonArray.getJSONObject(i);

                //er wordt een nieuwe isntantie aangemaakt voor het te vullen item
                Object newInstance = fieldInfo.Type().newInstance();

                //deze instantie wordt gevuld
                FillObject(newInstance,obj);

                //de instantie wordt aan de lijst toegevoegd
                list.add(newInstance);
            }
            //de list wordt in het originele veld gezet met reflection
            field.set(instance,list);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

   //deze methode wordt gebruikt om een veld te vullen met ruwe data.
    private void FillField(Object instance,Field field, JSONObject data){
        try {

            //haal het veld op.
        OpenDataField fieldInfo=field.getAnnotation(OpenDataField.class);
        String fieldName= fieldInfo.Name();

            //lelijke if constructie.. rot json parser.. rot reflection. Zie geen betere oplossing, helaas
            //in deze if constructie wordt er voor elk type de jusite manier van vullen aangeroepen.
            if(field.getType()==int.class){
                field.setInt(instance,data.getInt(fieldName));
            }

            else if(field.getType()==float.class){
                field.setFloat(instance,(float)data.getDouble(fieldName));
            }

            else if(field.getType()==boolean.class){
                field.setBoolean(instance,data.getBoolean(fieldName));
            }

            else if(field.getType()==double.class){
                field.setDouble(instance, data.getDouble(fieldName));
            }

            else if(field.getType()==char.class){
                field.setChar(instance, data.getString(fieldName).charAt(0));
            }

            else if(field.getType()==String.class){
                field.set(instance, data.getString(fieldName));
            }

            else{
                //hier is de uitzonderingssituatie. namelijk dat er een nieuwe instantie aan meot worden gemaakt en worden gevuld. in plaats van een primitief type
                Object newInstance= field.getType().newInstance();
                FillObject(newInstance,data.getJSONObject(fieldName));
                field.set(instance,newInstance);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}
