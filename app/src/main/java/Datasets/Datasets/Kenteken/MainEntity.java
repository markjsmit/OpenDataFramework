package Datasets.Datasets.Kenteken;

import java.util.ArrayList;

import OpenData.OpenData.Annotations.OpenDataCollection;
import OpenData.OpenData.Annotations.OpenDataEntity;
import OpenData.OpenData.Annotations.OpenDataField;

/**
 * Created by Mark on 15-2-2015.
 */
@OpenDataEntity
public class MainEntity {
    @OpenDataCollection(Name = "entry", Type = KentekenInfo.class)
    public ArrayList<KentekenInfo> Kentekeninfo = new ArrayList<KentekenInfo>();
}
