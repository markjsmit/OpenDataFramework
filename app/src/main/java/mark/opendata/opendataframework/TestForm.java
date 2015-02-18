package mark.opendata.opendataframework;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Datasets.Datasets.Postcode.MainEntity;
import Datasets.Datasets.Postcode.PostcodeRetriever;
import OpenData.OpenDataFactory;

//testcase postcodes ophalen
public class TestForm extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //layout instellen
        setContentView(R.layout.activity_test_form);

        //basis informatie isntellen
        ((EditText) findViewById(R.id.PostcodeTekst)).setText("7823sc");
        ((EditText) findViewById(R.id.HuisnummerTekst)).setText("39");

        //data ophalen
        UpdateInformation();

        //knopje koppelen aan het ophalen van data
        ((Button)findViewById(R.id.PostcodeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateInformation();
            }
        });



    }

    
    private void UpdateInformation(){

        //alle tekstfields in variablen zetten
        TextView plaatsnaambox = (TextView) findViewById(R.id.PlaatsnaamBox);
        TextView straatnaambox = (TextView) findViewById(R.id.StraatnaamBox);
        EditText postcodebox = (EditText) findViewById(R.id.PostcodeTekst);
        EditText huisnummerbox = (EditText) findViewById(R.id.HuisnummerTekst);
        try {
            //postcode retriever aanmaken
            PostcodeRetriever retriever = (PostcodeRetriever) OpenDataFactory.GetRetriever(PostcodeRetriever.class);
            //filters op huisnummer en postcode zetten
            retriever.Huisnummer = huisnummerbox.getText().toString();
            retriever.Postcode = postcodebox.getText().toString();

            //de data ophalen
            MainEntity entity = retriever.RetrieveData();
            if (entity.status.equals("ok")) {

                //en veldjes invullen
                plaatsnaambox.setText(entity.Details.get(0).Stad);
                straatnaambox.setText(entity.Details.get(0).Straat);
            }
        }catch(Exception ex){}
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
            Intent intent = new Intent(this, RDW.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
