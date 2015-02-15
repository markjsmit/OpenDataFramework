package Datasets.Datasets.Kenteken;

import java.util.jar.Attributes;

import OpenData.OpenData.Annotations.OpenDataEntity;
import OpenData.OpenData.Annotations.OpenDataField;

/**
 * Created by Mark on 15-2-2015.
 */
@OpenDataEntity()
public class KentekenInfo {
    @OpenDataField(Name="d:Aantalcilinders")
    public String AantalCilinders;

    @OpenDataField(Name="d:Aantalzitplaatsen")
    public String Aantalzitplaatsen;

    @OpenDataField(Name="d:Brandstofverbruikbuitenweg")
    public String Brandstofverbruikbuitenweg;

    @OpenDataField(Name="d:Brandstofverbruikstad")
    public String Brandstofverbruikstad;

    @OpenDataField(Name="d:Catalogusprijs")
    public String Catalogusprijs;

    @OpenDataField(Name="d:Zuinigheidslabel")
    public String Zuinigheidslabel;


    @OpenDataField(Name="d:Merk")
    public String Merk;

    @OpenDataField(Name="d:Handelsbenaming")
    public String Handelsbenaming;




}

