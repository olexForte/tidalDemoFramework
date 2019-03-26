package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Diff {

    List<String> diff = new ArrayList<String>();

    public void Add(Field field, Object expexted, Object actual){
        diff.add(field.getName() + " -> " + "expected: " + expexted + ", actual: " + actual);
    }

    public Boolean isEmpty(){
        return diff.isEmpty();
    }

    public String prettyString(){
        String prettyString = "\n";

        for(String s: diff){
            prettyString += s + "\n";
        }

        return prettyString;
    }
}
