package OpenData.OpenData.Abstract;

/**
 * Created by Mark on 1-2-2015.
 */
public abstract class DataFormat {
    public DataFormat(){}
    public abstract Object Parse(String input,Class cls);
}
