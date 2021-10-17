package Iterator;

import java.util.ArrayList;
import java.util.Arrays;

public class TextIterator{
    private int it = 0;
    public boolean hasNext = true;
    private final ArrayList<String> data = new ArrayList<>();
    public TextIterator(String text){
        data.addAll(Arrays.asList(text.split("\\n")));
    }
    public TextIterator(StringBuilder text){
        data.addAll(Arrays.asList((text.toString()).split("\\n")));
    }
    public ArrayList<String> getAsArrayList(){
        return this.data;
    }
    public void setIterator(){
        it = 0;
        if(data.size()>1){
            hasNext = true;
        }
    }
    public String getLine(){
        String s;
        if(it<data.size()-1){
            s =  data.get(it);
            hasNext = true;
            it ++;
        }else if(it==data.size()-1){
            s =  data.get(it);
            hasNext = false;
            it ++;
        }else{
            s = null;
        }

        return s;
    }

}