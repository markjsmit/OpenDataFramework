package OpenData;

import OpenData.OpenData.Abstract.Retriever;

/**
 * Created by Mark on 1-2-2015.
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
