package mark.opendata.opendataframework;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import Datasets.Datasets.Postcode.MainEntity;
import Datasets.Datasets.Postcode.PostcodeRetriever;
import OpenData.OpenData.Abstract.Retriever;
import OpenData.OpenDataFactory;


public class TestForm extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_form);
        TextView plaatsnaambox = (TextView) findViewById(R.id.PlaatsnaamBox);
        TextView straatnaambox = (TextView) findViewById(R.id.StraatnaamBox);

        PostcodeRetriever retriever = (PostcodeRetriever)OpenDataFactory.GetRetriever(PostcodeRetriever.class);
        retriever.Huisnummer="39";
        retriever.Postcode="7823sc";
        MainEntity entity= retriever.RetrieveData();

   if(entity.status.equals("ok")) {
            plaatsnaambox.setText(entity.Details.get(0).Stad);
            straatnaambox.setText(entity.Details.get(0).Straat);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
