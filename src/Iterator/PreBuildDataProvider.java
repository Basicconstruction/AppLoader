package Iterator;

import java.util.ArrayList;

public class PreBuildDataProvider {
    public static void main(String[] args){
        ArrayList<ArrayList<String>> x = new ArrayList<>();
        int NUM = 2;
        ArrayList<String> y1 = new ArrayList<>();
        y1.add("1");
        y1.add("2");
        y1.add("3");
        ArrayList<String> y2 = new ArrayList<>();
        y2.add("4");
        y2.add("5");
        ArrayList<String> y3 = new ArrayList<>();
        y3.add("6");
        y3.add("7");
        x.add(y1);
        x.add(y2);
        x.add(y3);
        int counter;
        int pointer = 0;
        int p = 0;
        boolean continued;
        int storedPointer = 0;
        ArrayList<String> collector = new ArrayList<>();
        for(int i = 0;i < 10;i++){

            counter = 0;
            continued = true;
            pointer = storedPointer;
            collector = new ArrayList<>();

            for(int j = p;j<x.size()&&continued;){
                while(counter<NUM){
                    if(pointer<x.get(j).size()){
                        collector.add(x.get(j).get(pointer));
                        counter++;
                        pointer++;
                        storedPointer = pointer;
                        if(pointer==x.get(j).size()){
                            counter = NUM;//退出while循环
                            j++;
                            storedPointer = 0;
                            p = j;
                        }
                    }else{
                        counter = NUM;//退出while循环
                        j++;
                        storedPointer = 0;
                        p = j;
                    }
                }
                continued = false;
            }
            if(p>=x.size()){
                p = 0;
            }
            for(String s :collector){
                System.out.println(s);
            }
            System.out.println();
        }
        for(int i = 0;i < 10;i++){
            collector = new ArrayList<String>();
            counter = 0;
            continued = true;
            for(int j = p;j<x.size()&&continued;){
                while(counter<NUM){
                    if(pointer<x.get(j).size()){
                        collector.add(x.get(j).get(pointer));
                        counter++;
                        pointer++;
                    }else{
                        pointer = 0;
                        j++;
                        if(j>=x.size()){
                            j = 0;
                        }
                    }
                }
                p = j;
                continued = false;
            }
            for(String s :collector){
                System.out.println(s);
            }
            System.out.println();
        }
    }
    public void Test(){
        ArrayList<String> y1 = new ArrayList<>();
        y1.add("1");
        y1.add("2");
        y1.add("3");
        ArrayList<String> y2 = new ArrayList<>();
        y2.add("4");
        y2.add("5");
        ArrayList<String> y3 = new ArrayList<>();
        y3.add("6");
        y3.add("7");
        ArrayList<ArrayList<String>> jk = new ArrayList<>();
        jk.add(y1);
        jk.add(y2);
        jk.add(y3);
        DataProvider<String> dp = new DataProvider<String>(jk,2);
        for(int i = 0;i <5;i++){
            for(String s:dp.get()){
                System.out.print(s+" ");
            }
            System.out.println();
        }
    }
}
