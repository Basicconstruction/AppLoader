package Iterator;

import java.io.*;
import java.util.ArrayList;

public class TextCollector {
    private int it = 0;
    public boolean hasNext = true;
    private final StringBuilder sb = new StringBuilder();
    private ArrayList<String> al = new ArrayList<>();
    public TextCollector(File file) {
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String s = null;
            while((s=br.readLine())!=null){
                sb.append(s).append("\n");
            }
            br.close();
            fr.close();
            al = new TextIterator(sb).getAsArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public TextCollector(String path){
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String s = null;
            while((s=br.readLine())!=null){
                sb.append(s).append("\n");
            }
            br.close();
            fr.close();
            al = new TextIterator(sb).getAsArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public TextCollector(FileReader fr){
        try{
            BufferedReader br = new BufferedReader(fr);
            String s = null;
            while((s=br.readLine())!=null){
                sb.append(s).append("\n");
            }
            br.close();
            al = new TextIterator(sb).getAsArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public TextCollector(BufferedReader br){
        try{
            String s = null;
            while((s=br.readLine())!=null){
                sb.append(s).append("\n");
            }
            br.close();
            al = new TextIterator(sb).getAsArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getAsArrayList(){
        return this.al;
    }
    public StringBuilder getAsStringBuilder(){
        return this.sb;
    }
    public void setIterator(){
        it = 0;
        if(al.size()>1){
            hasNext = true;
        }
    }
    public String getLine(){
        String s;
        if(it<al.size()-1){
            s =  al.get(it);
            hasNext = true;
            it ++;
        }else if(it==al.size()-1){
            s =  al.get(it);
            hasNext = false;
            it ++;
        }else{
            s = null;
        }

        return s;
    }
    public TextIterator getAsTextIterator(){
        return new TextIterator(this.sb);
    }
}
