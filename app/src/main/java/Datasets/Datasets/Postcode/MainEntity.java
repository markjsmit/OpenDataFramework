package Datasets.Datasets.Postcode;


import java.util.ArrayList;

import OpenData.OpenData.Annotations.OpenDataCollection;
import OpenData.OpenData.Annotations.OpenDataEntity;
import OpenData.OpenData.Annotations.OpenDataField;
/**
 * Created by Mark on 1-2-2015.
 * Deze klasse dient als wrapper om de postcode info. Er worden een aantal niet relevante velden meegestuurd.
 * Deze class zorgt er d.m.v. de annotations voor dat enkel de kenteken informatie word ingeladen
 */
@OpenDataEntity
public class MainEntity {

    public MainEntity(){}

    @OpenDataField(Name="status")
    public String status;
    @OpenDataCollection(Name="details",Type=Details.class)
    public ArrayList<Details> Details;

}
