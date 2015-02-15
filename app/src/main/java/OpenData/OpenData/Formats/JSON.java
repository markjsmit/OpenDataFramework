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
 */

public class JSON extends DataFormat {

    public JSON(){}

    @Override
    public Object Parse(String input,Class cls) {
        //nieuwe instantie van class aanmaken
        try {
            Object instance = cls.newInstance();

            //alle velden van de instance doorlopen
            JSONObject mainObj=new JSONObject(input);
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
        Class cls=instance.getClass();
        for(Field field: cls.getFields()) {
            if(field.isAnnotationPresent(OpenDataField.class)){
                FillField(instance,field,data);
            }
            if(field.isAnnotationPresent(OpenDataCollection.class)){
                FillList(instance,field,data);
            }
        }
    }

    private void FillList(Object instance, Field field, JSONObject data) {
        OpenDataCollection fieldInfo=field.getAnnotation(OpenDataCollection.class);
        String fieldName= fieldInfo.Name();
        ArrayList list = new ArrayList();
        try {
            JSONArray jsonArray=data.getJSONArray(fieldName);
            for(int i=0 ; i< jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                Object newInstance = fieldInfo.Type().newInstance();
                FillObject(newInstance,obj);
                list.add(newInstance);
            }
            field.set(instance,list);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }


    private void FillField(Object instance,Field field, JSONObject data){
        try {
        OpenDataField fieldInfo=field.getAnnotation(OpenDataField.class);
        String fieldName= fieldInfo.Name();

            //lelijke if constructie.. rot json parser.. rot reflection. Zie geen betere oplossing, helaas

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
