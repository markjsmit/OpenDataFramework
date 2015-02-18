package OpenData.OpenData.Abstract;

/**
 * Created by Mark on 1-2-2015.
 * Hier word een data format parser gedefinieerd
 * In de parse methode komt een methode die de input kan omzetten naar een instantie van de meegegeven klasse
 */
public abstract class DataFormat {
    public DataFormat(){}
    public abstract Object Parse(String input,Class cls);
}
