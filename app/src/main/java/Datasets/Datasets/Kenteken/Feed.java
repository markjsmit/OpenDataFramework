package Datasets.Datasets.Kenteken;

import java.util.ArrayList;
import java.util.jar.Attributes;

import OpenData.OpenData.Annotations.OpenDataCollection;
import OpenData.OpenData.Annotations.OpenDataEntity;

/**
 * Created by Mark on 15-2-2015.
 */
@OpenDataEntity
public class Feed {
    @OpenDataCollection(Name = "entry", Type = KentekenInfo.class)
    public ArrayList<KentekenInfo> Kentekeninfo = new ArrayList<KentekenInfo>();
}
