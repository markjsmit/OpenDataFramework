package mark.opendata.opendataframework;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import Datasets.Datasets.Kenteken.KentekenRetriever;
import Datasets.Datasets.Kenteken.MainEntity;
import OpenData.OpenDataFactory;


public class RDW extends ActionBarActivity {

    //test case RDW GEGEVENS een simpel formulier aanmaken
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //layout inladen
        setContentView(R.layout.activity_rdw);

        //startup informatie zetten
        ((EditText)findViewById(R.id.KentekenTekst)).setText("2XNB46");

        //data ophalen
        UpdateInformation();

        //knop event koppelen aan het ophalen van data
        ((Button)findViewById(R.id.KentekenButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateInformation();
            }
        });


    }

    private void UpdateInformation() {

        //een kenteken retriever aanmaken
        KentekenRetriever retriever = (KentekenRetriever) OpenDataFactory.GetRetriever(KentekenRetriever.class);

        //eenkenteken filter toevoegen.
        //rechstreeks het filter vullen kan worden gebruikt om bijv te filteren op AantalCilinders.
        retriever.FilterByKenteken(((EditText)findViewById(R.id.KentekenTekst)).getText().toString());

        //data ophalen
        MainEntity entity= retriever.RetrieveData();

        //en alle veldjes vullen
        ((TextView)findViewById(R.id.AantalCilindersBox)).setText(entity.Kentekeninfo.get(0).AantalCilinders);
        ((TextView)findViewById(R.id.AantalzitplaatsenBox)).setText(entity.Kentekeninfo.get(0).Aantalzitplaatsen);
        ((TextView)findViewById(R.id.BrandstofverbruikstadBox)).setText(entity.Kentekeninfo.get(0).Brandstofverbruikstad);
        ((TextView)findViewById(R.id.BrandstofverbruikbuitenwegBox)).setText(entity.Kentekeninfo.get(0).Brandstofverbruikbuitenweg);
        ((TextView)findViewById(R.id.CatalogusprijsBox)).setText(entity.Kentekeninfo.get(0).Catalogusprijs);
        ((TextView)findViewById(R.id.ZuinigheidslabelBox)).setText(entity.Kentekeninfo.get(0).Zuinigheidslabel);
        ((TextView)findViewById(R.id.HandelsbenamingBox)).setText(entity.Kentekeninfo.get(0).Handelsbenaming);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.menu_rdw, menu);
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
