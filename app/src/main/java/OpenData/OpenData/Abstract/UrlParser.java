package OpenData.OpenData.Abstract;

/**
 * Created by Mark on 2-2-2015.
 * Simpele representatie van een url parser.
 */
public abstract  class UrlParser {
    public UrlParser(){}
    public abstract String Parse(Retriever retriever);
}
