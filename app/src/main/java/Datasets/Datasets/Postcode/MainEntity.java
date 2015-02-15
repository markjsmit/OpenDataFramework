package Datasets.Datasets.Postcode;


import java.util.ArrayList;

import OpenData.OpenData.Annotations.OpenDataCollection;
import OpenData.OpenData.Annotations.OpenDataEntity;
import OpenData.OpenData.Annotations.OpenDataField;

@OpenDataEntity
public class MainEntity {

    public MainEntity(){}

    @OpenDataField(Name="status")
    public String status;
    @OpenDataCollection(Name="details",Type=Details.class)
    public ArrayList<Details> Details;

}
