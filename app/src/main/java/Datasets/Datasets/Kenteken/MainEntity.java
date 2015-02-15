package Datasets.Datasets.Kenteken;

import OpenData.OpenData.Annotations.OpenDataEntity;
import OpenData.OpenData.Annotations.OpenDataField;

/**
 * Created by Mark on 15-2-2015.
 */
@OpenDataEntity
public class MainEntity {
     @OpenDataField(Name="feed")
     public Feed feed=new Feed();
}
