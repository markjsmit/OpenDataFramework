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
 */
public class XML extends DataFormat {
    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    public XML(){
        dbf = DocumentBuilderFactory.newInstance();
        try {
            db= dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Object Parse(String input, Class cls) {

        try {
            Object instance = cls.newInstance();
            InputStream is = new ByteArrayInputStream( input.getBytes() );
            Document doc= db.parse(is);
            Element elem = doc.getDocumentElement();
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

    private void FillObject(Object instance, Element elem) throws IllegalAccessException, InstantiationException {
        Class cls=instance.getClass();
        for(Field field: cls.getFields()) {
            if(field.isAnnotationPresent(OpenDataField.class)){
                FillField(instance,field,elem);
            }
            if(field.isAnnotationPresent(OpenDataCollection.class)){
                FillList(instance,field,elem);
            }
        }
    }

    private void FillList(Object instance, Field field, Element elem) throws IllegalAccessException, InstantiationException {

        ArrayList list = new ArrayList();

        OpenDataCollection fieldInfo=field.getAnnotation(OpenDataCollection.class);
        String fieldName= fieldInfo.Name();
        NodeList elements = elem.getElementsByTagName(fieldName);
        for (int i=0; i < elements.getLength(); i++) {
            Element pickedElem = (Element)elements.item(i);
            Object newInstance = fieldInfo.Type().newInstance();
            FillObject(newInstance,pickedElem);
            list.add(newInstance);
        }
        field.set(instance,list);

    }

    private void FillField(Object instance,Field field, Element elem) throws IllegalAccessException, InstantiationException {
        OpenDataField fieldInfo=field.getAnnotation(OpenDataField.class);
        String fieldName= fieldInfo.Name();
        Node node = elem.getElementsByTagName(fieldName).item(0);
        String text=node.getTextContent();


        //Daar heb je die lelijke if constructie weer

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
            Object newInstance= field.getType().newInstance();
            FillObject(newInstance,(Element)node);
            field.set(instance,newInstance);
        }



    }
}
