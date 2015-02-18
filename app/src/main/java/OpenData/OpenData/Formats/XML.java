package OpenData.OpenData.Formats;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import OpenData.OpenData.Abstract.DataFormat;
import OpenData.OpenData.Annotations.OpenDataCollection;
import OpenData.OpenData.Annotations.OpenDataField;

/**
 * Created by Mark on 15-2-2015.
 * Deze class word gebruikt om XML te parsen.
 */
public class XML extends DataFormat {
    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    public XML(){

        dbf = DocumentBuilderFactory.newInstance();
        try {
            // een een document builder aanmaken.
            db= dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


    }

    //deze methode is verantwoordelijk van het parsen van xml naar een java object
    @Override
    public Object Parse(String input, Class cls) {

        try {
            //een nieuwe instantie aanmaken
            Object instance = cls.newInstance();

            //het een DOM object vullen met de opgehaalde data
            InputStream is = new ByteArrayInputStream( input.getBytes() );
            Document doc= db.parse(is);

            //het main element ophalen
            Element elem = doc.getDocumentElement();

            //de instantie vullen
            FillObject(instance,elem);
            return instance;

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    //deze methode is verantworodleijk voor het vullen van een object
    private void FillObject(Object instance, Element elem) throws IllegalAccessException, InstantiationException {

        //de class achter de instnatie ophalen
        Class cls=instance.getClass();

        //alle velden doorlopen
        for(Field field: cls.getFields()) {
            //als het een gewoon veld is en gevuld moet worden
            if(field.isAnnotationPresent(OpenDataField.class)){
                //veld vullen
                FillField(instance,field,elem);
            }

            //als het een lijst is en gevuld moet worden
            if(field.isAnnotationPresent(OpenDataCollection.class)){
                //de lijst vullen
                FillList(instance,field,elem);
            }
        }
    }

    //deze methode is verantwoordelijk voor het vullen voor een lijst
    private void FillList(Object instance, Field field, Element elem) throws IllegalAccessException, InstantiationException {

        //Nieuwe Array list aanmaken
        ArrayList list = new ArrayList();

        //de veld informatie ophalen
        OpenDataCollection fieldInfo=field.getAnnotation(OpenDataCollection.class);
        String fieldName= fieldInfo.Name();

        //alle nodes doorlopen met de juiste element naam
        NodeList elements = elem.getElementsByTagName(fieldName);
        for (int i=0; i < elements.getLength(); i++) {
            //de node inladen als element.
            Element pickedElem = (Element)elements.item(i);

            // Een nieuwe instantie aanmaken voor het te vullen object
            Object newInstance = fieldInfo.Type().newInstance();

            //de nieuwe isntantie vullen met het opgehaalde element
            FillObject(newInstance,pickedElem);

            //Instantie aan de lisjt toevoegen
            list.add(newInstance);
        }
        //met reflectie de lijst in de instantie zetten.
        field.set(instance,list);

    }


    //Een veld vullen met xml data. Let op Omdat open data nergens attributen gebruikt voor de data, is dit hierin ook niet verwerkt.
    private void FillField(Object instance,Field field, Element elem) throws IllegalAccessException, InstantiationException {

        //de veld informatie ophalen
        OpenDataField fieldInfo=field.getAnnotation(OpenDataField.class);
        String fieldName= fieldInfo.Name();

        //  De node bij het veld ophalen
        Node node = elem.getElementsByTagName(fieldName).item(0);

        //de tekst uit de node ophalen
        String text=node.getTextContent();


        //Daar heb je die lelijke if constructie weer
        //in deze if constructie wordt er voor elk type de jusite manier van vullen aangeroepen.
        if(field.getType()==int.class){
            field.setInt(instance,Integer.parseInt(text));
        }

        else if(field.getType()==float.class){
            field.setFloat(instance,Float.parseFloat(text));
        }

        else if(field.getType()==boolean.class){
            field.setBoolean(instance,Boolean.parseBoolean(text));
        }

        else if(field.getType()==double.class){
            field.setDouble(instance, Double.parseDouble(text));
        }

        else if(field.getType()==char.class){
            field.setChar(instance, text.charAt(0));
        }

        else if(field.getType()==String.class){
            field.set(instance, text);
        }

        else{
            //en het uitzonderings gevalletje weer. Er meot een nieuwe instantie worden gevuld in plaats van een primitief type
            Object newInstance= field.getType().newInstance();
            FillObject(newInstance,(Element)node);
            field.set(instance,newInstance);
        }



    }
}
