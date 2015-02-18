package Helpers;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import Helpers.Abstract.IRequestResult;

/**
 * Created by Mark on 12/09/2014.
 * Deze class dient als helper om een aantal verschillende http activiteiten uit te voeren. De basis is geschreven in het verleden voor een andere app.
 * Al heb ik een deel over genomen hij was niet geheel sufficiënt  voor het gebruik, daarom nog wel enkele kleine aanpassingen gemaakt
 */
public class HTTPHelper {


    //deze statische methode voert de  voert een async post request uit op de instantie die word aangemaakt.
    public static void AsyncGETRequest(String url, IRequestResult callback){
        instance.DoAsyncGETRequest(url, callback);
    }

    //deze methode  voert een async post request uit.
    public void DoAsyncGETRequest(String url, IRequestResult callback){
        new HttpAsyncGetTask(callback).execute(url);
    }


    //deze statische methode voert de  voert een async get request uit op de instantie die word aangemaakt.
    public static void AsyncPOSTRequest(String url,String postdata, IRequestResult callback){
        instance.DoAsyncPostRequest(url, postdata, callback);
    }
    //deze methode  voert een async get request uit
    public void DoAsyncPostRequest(String url,String postdata, IRequestResult callback){
        new HttpAsyncPostTask(callback,postdata).execute(url);
    }


    //deze voert een post request uit op de instantie
    public static String PostRequest(String url, String postdata){
        return instance.DoPostRequest(url, postdata);
    }

    /*deze voert asynchroon een post request uit, om vervolgens te wachten tot dit geheel is uitgevoerd
    * dit is omdat sommige versies van android eisen dat een request async worden gedaan.
    * */

    public  String DoPostRequest(String url, String postdata) {
        try {
            String result =(String)(new HttpAsyncPostTask(postdata).execute(url).get(10000, TimeUnit.MILLISECONDS));
            return  result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    //deze methode  voert een async post request uit
    public static String GetRequest(String url) {
        return   instance.DoGetRequest(url);
    }

    /*deze voert asynchroon een get request uit, om vervolgens te wachten tot dit geheel is uitgevoerd
  * dit is omdat sommige versies van android eisen dat een request async worden gedaan.
  * */
    public String DoGetRequest(String url) {

        try {
            String result =(String)(new HttpAsyncGetTask().execute(url).get(10000, TimeUnit.MILLISECONDS));
            return  result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    /*hier word er een instantie van de http helper aangemaakt om voor de statische methoden te gebruiken
    * Hiervoor is gekozen zodat de async classes binnen deze class konden vallen, en dus ook gebruik konden maken van de lokale variabelen
     */
    private static HTTPHelper instance= new HTTPHelper();


    //Hier word de daadwerkelijke post request uitgevoerd
    private static String POST(String url, String data){
        //Instantie van httpclient aanmaken
        DefaultHttpClient httpclient = new DefaultHttpClient();

        //Http post aanmaken voor de url
        HttpPost httpost = new HttpPost(url);


        //De informatie in een stringbuilder gooien
        StringEntity se = null;
        try {
            se = new StringEntity(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //De post informatie in de post request plaatsen
        httpost.setEntity(se);

        //Wat benodigde headers zetten
        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        //En de response afhandelen
        ResponseHandler responseHandler = new BasicResponseHandler();

        try {
            httpclient.execute(httpost, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseHandler.toString();

    }

    //doet de daadwerkelijke get request
    private static String GET(String url) {
        String result = "";
        InputStream inputStream = null;
        try {

            // Een http client aanmaken
            HttpClient httpclient = new DefaultHttpClient();

            //Een get request aanmken voor de url
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // het resultaat afhandelen
            inputStream = httpResponse.getEntity().getContent();

            // het resultaat in een string ombouwen
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Dit werkte niet zo best!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    //input stream naar een string ombouwen
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        //reader aanmaken
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";

        //en regeltje voor regeltje toevoegen.
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }



    // simpele implementatie voor de async get task. Doet eigenlijk niets meer dan een callback functie invullen, een reqeuest doen, en deze aanroepen als de request is gedaan.
    private class HttpAsyncGetTask extends AsyncTask<String, Void, String> {
        private IRequestResult resultObject;

        public HttpAsyncGetTask(){

        }
        //callback definieren
        public HttpAsyncGetTask(IRequestResult requestResult){
            resultObject=requestResult;
        }

        //de taak die moet worden uitgevoerd
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }
        // als het klaar is met uitvoeren
        @Override
        protected void onPostExecute(String result) {
            if(resultObject!=null) {
                resultObject.OnLoaded(result);
            }
        }
    }


    // simpele implementatie voor de async post task. Doet eigenlijk niets meer dan een callback functie invullen,een request doen,  en deze aanroepen als de request is gedaan.
    private class HttpAsyncPostTask extends AsyncTask<String, Void, String> {
        private IRequestResult resultObject;
        private String postdata;

        public HttpAsyncPostTask(){

        }

        public HttpAsyncPostTask(String postdata){
            this.postdata=postdata;
        }

        //informatie definieren
        public HttpAsyncPostTask(IRequestResult requestResult,String postdata){
            resultObject=requestResult;
            this.postdata=postdata;
        }

        //de taak uitvoeren
        @Override
        protected String doInBackground(String... urls) {
            return POST(urls[0], postdata);
        }
        // de callback methode aanroepen
        @Override
        protected void onPostExecute(String result) {
            if(resultObject!=null) {
                resultObject.OnLoaded(result);
            }
        }
    }

}
