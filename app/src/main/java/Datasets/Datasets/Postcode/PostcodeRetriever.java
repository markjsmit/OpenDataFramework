package Datasets.Datasets.Postcode;

import OpenData.OpenData.Annotations.OpenDataParameter;
import OpenData.OpenData.Annotations.OpenDataRetriever;
import OpenData.OpenData.Formats.JSON;
import OpenData.OpenData.UrlParser.QueryString;
import OpenData.OpenData.*;

/**
 * Created by Mark on 1-2-2015.
 */
@OpenDataRetriever(Url="http://api.postcodedata.nl/v1/postcode/",Format= JSON.class, MainEntity=MainEntity.class, UrlParser = QueryString.class)
public class PostcodeRetriever extends OpenData.OpenData.Abstract.Retriever<MainEntity> {
    public PostcodeRetriever(){}
    @OpenDataParameter(BindTo = "postcode")
    public String Postcode;
    @OpenDataParameter(BindTo = "streetnumber")
    public String Huisnummer;

    @OpenDataParameter(BindTo = "ref")
    public String Refurl="domeinnaam.nl";

    @OpenDataParameter(BindTo="type")
    public String DataFormat="JSON";

}
