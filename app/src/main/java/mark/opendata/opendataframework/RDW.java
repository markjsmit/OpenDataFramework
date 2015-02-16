package mark.opendata.opendataframework;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import Datasets.Datasets.Kenteken.KentekenRetriever;
import Datasets.Datasets.Kenteken.MainEntity;
import OpenData.OpenDataFactory;


public class RDW extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdw);


        KentekenRetriever retriever = (KentekenRetriever) OpenDataFactory.GetRetriever(KentekenRetriever.class);
        retriever.FilterByKenteken("2XNB46");
        MainEntity entity= retriever.RetrieveData();

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
