package Datasets.Datasets.Kenteken;

import Datasets.Datasets.Postcode.*;
import OpenData.OpenData.Annotations.OpenDataParameter;
import OpenData.OpenData.Annotations.OpenDataRetriever;
import OpenData.OpenData.Formats.JSON;
import OpenData.OpenData.Formats.XML;
import OpenData.OpenData.UrlParser.QueryString;

/**
 * Created by Mark on 15-2-2015.
 */

@OpenDataRetriever(Url="https://api.datamarket.azure.com/opendata.rdw/VRTG.Open.Data/v1/KENT_VRTG_O_DAT",Format= XML.class, MainEntity= Datasets.Datasets.Kenteken.MainEntity.class, UrlParser = QueryString.class)
public class KentekenRetriever extends OpenData.OpenData.Abstract.Retriever<KentekenRetriever>{
    public KentekenRetriever(){}

    @OpenDataParameter(BindTo = "$filter")
    public String Filter;

    public void FilterByKenteken(String kenteken){
        kenteken=kenteken.replace("-","");
        Filter="Kenteken%20eq%20%27"+kenteken+"%27";
    }

}
