package Datasets.Datasets.Postcode;


import OpenData.OpenData.Annotations.OpenDataEntity;
import OpenData.OpenData.Annotations.OpenDataField;

@OpenDataEntity
public class Details {
    public Details(){}
    @OpenDataField(Name="street")
    public String Straat;
    @OpenDataField(Name="city")
    public String Stad;
    @OpenDataField(Name="municipality")
    public String Gemeente;
    @OpenDataField(Name="province")
    public String Provincie;
    @OpenDataField(Name="lat")
    public String Breedtegraad;
    @OpenDataField(Name="lon")
    public String Lengtegraad;
}
