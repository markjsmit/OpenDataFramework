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
 */
public class HTTPHelper {


    public static void AsyncGETRequest(String url, IRequestResult callback){
        instance.DoAsyncGETRequest(url, callback);
    }
    public void DoAsyncGETRequest(String url, IRequestResult callback){
        new HttpAsyncGetTask(callback).execute(url);
    }

    public static void AsyncPOSTRequest(String url,String postdata, IRequestResult callback){
        instance.DoAsyncPostRequest(url, postdata, callback);
    }
    public void DoAsyncPostRequest(String url,String postdata, IRequestResult callback){
        new HttpAsyncPostTask(callback,postdata).execute(url);
    }

    public static String PostRequest(String url, String postdata){
        return instance.DoPostRequest(url, postdata);
    }

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


    public static String GetRequest(String url) {
        return   instance.DoGetRequest(url);
    }

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

    private static HTTPHelper instance= new HTTPHelper();

    private static String POST(String url, String data){
        //instantiates httpclient to make request
        DefaultHttpClient httpclient = new DefaultHttpClient();

        //url with the post data
        HttpPost httpost = new HttpPost(url);


        //passes the results to a string builder/entity
        StringEntity se = null;
        try {
            se = new StringEntity(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //sets the post request as the resulting string
        httpost.setEntity(se);
        //sets a request header so the page receving the request
        //will know what to do with it
        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        //Handles what is returned from the page
        ResponseHandler responseHandler = new BasicResponseHandler();

        try {
            httpclient.execute(httpost, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseHandler.toString();

    }

    private static String GET(String url) {
        String result = "";
        InputStream inputStream = null;
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    private class HttpAsyncGetTask extends AsyncTask<String, Void, String> {
        private IRequestResult resultObject;

        public HttpAsyncGetTask(){

        }

        public HttpAsyncGetTask(IRequestResult requestResult){
            resultObject=requestResult;
        }

        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if(resultObject!=null) {
                resultObject.OnLoaded(result);
            }
        }
    }



    private class HttpAsyncPostTask extends AsyncTask<String, Void, String> {
        private IRequestResult resultObject;
        private String postdata;

        public HttpAsyncPostTask(){

        }

        public HttpAsyncPostTask(String postdata){
            this.postdata=postdata;
        }
        public HttpAsyncPostTask(IRequestResult requestResult,String postdata){
            resultObject=requestResult;
            this.postdata=postdata;
        }

        @Override
        protected String doInBackground(String... urls) {
            return POST(urls[0], postdata);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if(resultObject!=null) {
                resultObject.OnLoaded(result);
            }
        }
    }

}
