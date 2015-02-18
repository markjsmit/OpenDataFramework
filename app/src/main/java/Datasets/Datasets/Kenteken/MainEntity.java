package Datasets.Datasets.Kenteken;

import java.util.ArrayList;

import OpenData.OpenData.Annotations.OpenDataCollection;
import OpenData.OpenData.Annotations.OpenDataEntity;
import OpenData.OpenData.Annotations.OpenDataField;

/**
 * Created by Mark on 15-2-2015.
 * Deze klasse dient als wrapper om de kenteken info. Er worden een aantal niet relevante velden meegestuurd.
 * Deze class zorgt er d.m.v. de annotations voor dat enkel de kenteken informatie word ingeladen
 */
@OpenDataEntity
public class MainEntity {
    @OpenDataCollection(Name = "entry", Type = KentekenInfo.class)
    public ArrayList<KentekenInfo> Kentekeninfo = new ArrayList<KentekenInfo>();
}
