package permanent_storage;

import Iterator.TextCollector;
import Iterator.TextIterator;

import java.io.*;
import java.util.ArrayList;

import static permanent_storage.Utils.*;

public class PermanentStorage {
    private String split = ",";
    public File file;
    public ArrayList<StorageMenu> data = new ArrayList<>();
    public PermanentStorage(){

    }
    public PermanentStorage(StorageMenu menu){
        this.file = new File("Virtual at "+menu.toString());
        this.data.add(menu);
    }
    public PermanentStorage(StringBuilder stringBuilderAllSeries){
        this.file = new File("Virtual at "+stringBuilderAllSeries.toString());
        try{
            TextIterator ti = new TextIterator(stringBuilderAllSeries);
            getInstanceHelper(ti,this);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public PermanentStorage(StringBuilder stringBuilderNum,StringBuilder stringBuilderSeries){
        this.file = new File("Virtual at "+stringBuilderNum.toString());
        try{
            TextIterator ti = new TextIterator(stringBuilderNum.append(stringBuilderSeries));
            getInstanceHelper(ti,this);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public PermanentStorage(ArrayList<StringBuilder> sbList){
        this.file = new File("Virtual at "+sbList.toString());
        try{
            TextIterator ti = new TextIterator(getSingleStringBuilder(sbList));
            getInstanceHelper(ti,this);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public PermanentStorage(File file){
        initialize(file);
    }
    public PermanentStorage(String names){
        this.file = new File(names+".ps");
        initialize(file);
    }
    public void removeStorageMenuByIndex(int index){
        data.remove(data.get(index));
    }
    public void remove(StorageMenu menu){
        data.remove(menu);
    }
    public void add(StorageMenu menu){
        data.add(menu);
    }
    public static PermanentStorage getInstance(StringBuilder stringBuilderAllSeries){
        PermanentStorage ps = new PermanentStorage();
        ps.file = new File("Virtual at"+stringBuilderAllSeries.toString());
        try{
            TextIterator ti = new TextIterator(stringBuilderAllSeries);
            getInstanceHelper(ti,ps);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ps;
    }
    public static PermanentStorage getInstance(StringBuilder stringBuilderNum,StringBuilder stringBuilderSeries){
        PermanentStorage ps = new PermanentStorage();
        ps.file = new File("Virtual at "+stringBuilderNum.toString());
        try{
            TextIterator ti = new TextIterator(stringBuilderNum.append(stringBuilderSeries));
            getInstanceHelper(ti,ps);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ps;
    }
    public static PermanentStorage getInstance(ArrayList<StringBuilder> sbList){
        PermanentStorage ps = new PermanentStorage();
        ps.file = new File("Virtual at "+sbList.toString());
        try{
            TextIterator ti = new TextIterator(getSingleStringBuilder(sbList));
            getInstanceHelper(ti,ps);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ps;
    }
    public static StorageMenu getStorageMenu(File file){
        //可重载
        TextIterator ti = new TextCollector(file).getAsTextIterator();
        return extractGetStorageMenu(ti);
    }
    public static StorageMenu getStorageMenu(String path){
        //可重载
        TextIterator ti = new TextCollector(path).getAsTextIterator();
        return extractGetStorageMenu(ti);
    }
    public static StorageMenu extractGetStorageMenu(TextIterator ti){
        return extractGetStorageMenu(ti,",");
    }
    public static StorageMenu extractGetStorageMenu(TextIterator ti,String split){
        StorageMenu sm = null;
        String s = ti.getLine();
        if(s!=null){
            if(s.split(split).length>1){
                String s1 = s.split(split,2)[0].trim();
                String s2 = s.split(split,2)[1].trim();
                sm = new StorageMenu(s1,s2);
            }else{
                sm = new StorageMenu(s);
            }
        }else{
            return null;
        }
        while((s=ti.getLine())!=null){
            if(s.split(split).length>1){
                String s1 = s.split(split,2)[0].trim();
                String s2 = s.split(split,2)[1].trim();
                sm.add(new StorageItem(s1,s2));
            }else{
                sm.add(new StorageItem(s));
            }

        }
        return sm;
    }
    public static PermanentStorage getInstance(File file){
        PermanentStorage ps = new PermanentStorage();
        ps.file = file;
        try{
            TextIterator ti = new TextCollector(ps.file).getAsTextIterator();
            getInstanceHelper(ti,ps);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ps;
    }
    public void setValue(String label, String value){
        for(int i =0;i < this.data.size();i++){
            if((this.data.get(i)).name.equals(label)){
                (this.data.get(i)).value = value;
            }
            if((this.data.get(i)).hasItem){
                StorageMenu.setValue(this.data.get(i),label,value);
            }
        }
    }
    public String getValue(String label){
        for(int i =0;i < this.data.size();i++){
            if((this.data.get(i)).name.equals(label)){
                return (this.data.get(i)).value;
            }
            if((this.data.get(i)).hasItem){
                if(StorageMenu.getValue(this.data.get(i),label)!=null){
                    return StorageMenu.getValue(this.data.get(i),label);
                }
            }
        }
        return null;
    }
    public StorageMenu getMenuSymbol(String label){
        for(int i =0;i < this.data.size();i++){
            if((this.data.get(i)).name.equals(label)){
                return (this.data.get(i));
            }
        }
        return null;
    }
    public StorageItem getItemSymbol(String label){
        for(int i =0;i < this.data.size();i++){
            if((this.data.get(i)).hasItem){
                if(StorageMenu.getValue(this.data.get(i),label)!=null){
                    return StorageMenu.getSymbol(this.data.get(i),label);
                }
            }
        }
        return null;
    }
    /**将更改写入到指定地址,但不包括，独立设定的StorageMenu*/
    public void write(String path){
        try{
            FileWriter fw = new FileWriter(path);
            Writer(fw,this,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void write(String path,String label){
        try{
            FileWriter fw = new FileWriter(path);
            for(StorageMenu sm:this.data){
                if(sm.name.equals(label)){
                    PermanentStorage ps = new PermanentStorage(sm);
                    Writer(fw,ps,true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**将更改写入到当前对象的初始化地址*/
    public void write(){
        try{
            FileWriter fw = new FileWriter(this.file);
            Writer(fw,this,true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<StringBuilder> getStringBuilder(){
        try{
            return Writer(new FileWriter(this.file),this,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 抽离出来的函数，用于将PermanentStorage对象写入到文件中
     */
    private ArrayList<StringBuilder> Writer(FileWriter fw,PermanentStorage ps,boolean hasCheck) throws IOException {
        BufferedWriter bfw = new BufferedWriter(fw);
        StringBuilder sb = new StringBuilder();
        StringBuilder bo = new StringBuilder();
        ArrayList<StringBuilder> as = new ArrayList<>();
        for(StorageMenu menu:ps.data){
            if((!menu.includedInParent)&&hasCheck){
                Writer(new FileWriter(menu.singleLocation),new PermanentStorage(menu),false);
                continue;
            }
            menu.order = -1;
            if(menu.value == null){
                sb.append(menu.name).append("\n");
            }else{
                sb.append(menu.name).append(" ").append(this.split).append(" ").append(menu.value).append("\n");
            }
            bo.append(menu.order).append(this.split);
            for(StorageItem si : menu.data){
                si.order = 0;
                if(si.hasItem){
                    iterator(si,sb,si.order,bo);
                }else{
                    if(si.value == null){
                        sb.append(si.name).append("\n");
                    }else{
                        sb.append(si.name).append(" ").append(this.split).append(" ").append(si.value).append("\n");
                    }
                    bo.append(si.order).append(this.split);
                }
            }
        }
        //也可以截取sb对象，使得生成的文件不含一个额外的空行
        bo.append("\n");
        as.add(bo);
        as.add(sb);
        //如果是单StorageMenu模式的话，就不写入定位数字
        if(hasCheck){
            bfw.write(bo.toString());
        }
        bfw.write(sb.toString());
        bfw.close();
        fw.close();
//        System.out.println("保存到文件成功");
        return as;
    }
    public void initialize(File file){
        this.file = file;
        if(file.exists()){
            boolean status = file.delete();
            if(status){
                System.out.println("previous file has been deleted");
            }else{
                System.out.println("error when delete the file!");
            }
            try{
                boolean status2 = file.createNewFile();
                if(status2){
                    System.out.println("file create ok");
                }else{
                    System.out.println("error when create the file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try{
                boolean status2 = file.createNewFile();
                if(status2){
                    System.out.println("file create ok");
                }else{
                    System.out.println("error when create the file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void iterator(StorageItem item,StringBuilder bs,int order,StringBuilder ob){
        item.order = order;
        if(item.hasItem){
            if(!(item.value==null)){
                bs.append(item.name).append(" ").append(this.split).append(" ").append(item.value).append("\n");
            }else{
                bs.append(item.name).append("\n");
            }
            ob.append(order).append(this.split);

            for(StorageItem si:item.data){
                iterator(si,bs,order+1,ob);
            }
        }else{
            if(!(item.value==null)){
                bs.append(item.name).append(" ").append(this.split).append(" ").append(item.value).append("\n");
            }else{
                bs.append(item.name).append("\n");
            }
            ob.append(order).append(this.split);

        }
    }
    public void setSplit(String split){
        this.split = split;
    }
    public static void getInstanceHelper(TextIterator ti,PermanentStorage ps){
        getInstanceHelper(ti,ps,",");
    }
    public static void getInstanceHelper(TextIterator ti,PermanentStorage ps,String inSplit){
        String s;
        StorageMenu sm = null;
        ArrayList<StorageItem> ob = null;
        int[] sys = new int[0];
        int counter = 0;
        int start = 0;
        int maxOrder = 0;
        boolean hasGenerate = false;
        int mini = -1;
        int maxi = -1;
        String value;
        boolean k;
        boolean firstload = true;
        while((s=ti.getLine())!=null){
            s = s.trim();
            if(firstload&&s.length()>0){
                k = startWithNumber(s);
            }else if(!firstload){
                k = false;
            }else{
                continue;
            }
            if(k){
                String[] symbols;
                symbols = s.split(inSplit);
                sys = new int[symbols.length];
                counter = sys.length;
                for(int i = 0;i<symbols.length;i++){
                    sys[i] = convert(symbols[i]);
                    maxOrder = Math.max(maxOrder,sys[i]);
                }
                firstload = false;
            }else{
                start++;
                value = null;
                if(s.split(inSplit).length>1){
                    String[] nv = s.split(inSplit,2);
                    s = (nv[0]).trim();
                    value = (nv[1]).trim();
                }
                if(start<=counter){
                    if(!hasGenerate){
                        ob = new ArrayList<>(maxOrder+1);
                        for(int i = 0;i < maxOrder+1;i++){
                            ob.add(new StorageItem(i+" "));
                            //初始化操作栈
                        }
                        hasGenerate = true;
                    }
                    int ki = sys[start-1];
                    if(ki==mini){
                        sm = new StorageMenu(s,value);
                        ps.add(sm);
                        maxi = mini;
                    }else if(ki>maxi){
                        if(maxi==-1){
                            assert sm != null;
                            ob.set(0,new StorageItem(s,value));
                            sm.add(ob.get(0));
                        }else{
                            ob.set(ki,new StorageItem(s,value));
                            ob.get(ki-1).add(ob.get(ki));
                        }
                        maxi = sys[start-1];
                    }else if(ki==maxi){
                        ob.set(ki,new StorageItem(s,value));
                        if(ki==0){
                            assert sm != null;
                            sm.add(ob.get(0));
                        }else{
                            ob.get(ki-1).add(ob.get(ki));
                        }
                    }else{
                        //ki < maxi
                        ob.set(ki,new StorageItem(s,value));
                        if(ki==0){
                            assert sm != null;
                            sm.add(ob.get(0));
                        }else{
                            ob.get(ki-1).add(ob.get(ki));
                        }
                        maxi = ki;
                    }
                }


            }
        }
    }
}
