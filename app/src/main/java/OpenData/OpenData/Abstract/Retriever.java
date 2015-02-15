package OpenData.OpenData.Abstract;

import Helpers.HTTPHelper;
import OpenData.OpenData.Abstract.DataFormat;
import OpenData.OpenData.Annotations.OpenDataRetriever;

/**
 * Created by Mark on 2-2-2015.
 */
public abstract class Retriever<T> {
   public T RetrieveData()
   {
       String url=GetUrl();
       String data=GetData(url);
       DataFormat format = GetFormat();
       return (T)format.Parse(data,GetClass());
   }

    private Class GetClass() {
        Class cls=this.getClass();
        if(cls.isAnnotationPresent(OpenDataRetriever.class)) {
            OpenDataRetriever retrieverInfo = (OpenDataRetriever) cls.getAnnotation(OpenDataRetriever.class);
            return retrieverInfo.MainEntity();
        }
        return null;
    }


    private String GetData(String url) {
        return HTTPHelper.GetRequest(url);
    }

    private DataFormat GetFormat() {
        Class cls=this.getClass();
        if(cls.isAnnotationPresent(OpenDataRetriever.class)) {
            OpenDataRetriever retrieverInfo = (OpenDataRetriever) cls.getAnnotation(OpenDataRetriever.class);
            try {
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

    private String GetUrl(){
        Class cls=this.getClass();
        String url="";
        if(cls.isAnnotationPresent(OpenDataRetriever.class)) {
            OpenDataRetriever retrieverInfo= (OpenDataRetriever)cls.getAnnotation(OpenDataRetriever.class);
            try {
                UrlParser parser = (UrlParser)retrieverInfo.UrlParser().newInstance();
                url=parser.Parse(this);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return url;
    }



}
