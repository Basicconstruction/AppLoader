package permanent_storage;

import java.util.ArrayList;

public class StorageMenu {
    public ArrayList<StorageItem> data = new ArrayList<>();
    public String name;
    public String value;
    public int order;
    public boolean hasItem;
    public boolean includedInParent = true;
    public String singleLocation = null;
    public StorageMenu(String name) {
        this.name = name;
        this.order = 0;
        hasItem = false;
    }
    public StorageMenu(String name,String value) {
        this.name = name;
        this.value = value;
        this.order = 0;
        hasItem = false;
    }
    public StorageMenu(String name,boolean includedInParent,String singleLocation) {
        this.name = name;
        this.order = 0;
        hasItem = false;
        this.includedInParent = includedInParent;
        this.singleLocation = singleLocation;
    }
    public StorageMenu(String name,String value,boolean includedInParent,String singleLocation) {
        this.name = name;
        this.value = value;
        this.order = 0;
        hasItem = false;
        this.includedInParent = includedInParent;
        this.singleLocation = singleLocation;
    }
    public StringBuilder getStringBuilder(){
        if(this.includedInParent){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(StorageItem si:this.data){
            if(si.value != null){
                sb.append(si.name).append(" , ").append(si.value).append("\n");
            }else{
                sb.append(si.name).append("\n");
            }
        }
        return sb;
    }
    public void removeStorageItemByIndex(int index){
        data.remove(data.get(index));
        hasItem = this.data.size()>0;
    }
    public void remove(StorageItem item){
        data.remove(item);
        hasItem = this.data.size()>0;
    }
    public void add(StorageItem item){
        data.add(item);
        hasItem = true;
    }
    public static void setValue(StorageMenu sm, String label, String value){
        if(sm.name.equals(label)){
            sm.value = value;
        }else{
            if(sm.hasItem){
                for(int i = 0;i < sm.data.size();i++){
                    StorageItem.setValue(sm.data.get(i),label,value);
                }
            }
        }
    }
    public void setValue(String label, String value){
        if(this.name.equals(label)){
            this.value = value;
        }else{
            if(this.hasItem){
                for(int i = 0;i < this.data.size();i++){
                    StorageItem.setValue(this.data.get(i),label,value);
                }
            }
        }
    }
    public static String getValue(StorageMenu sm, String label){
        if(sm.name.equals(label)){
            return sm.value;
        }else{
            if(sm.hasItem){
                for(int i = 0;i < sm.data.size();i++){
                    if(StorageItem.getValue(sm.data.get(i),label)!=null){
                        return StorageItem.getValue(sm.data.get(i),label);
                    }
                }
            }
        }
        return null;
    }
    public String getValue(String label){
        if(this.name.equals(label)){
            return this.value;
        }else{
            if(this.hasItem){
                for (StorageItem datum : this.data) {
                    if (StorageItem.getValue(datum, label) != null) {
                        return StorageItem.getValue(datum, label);
                    }
                }
            }
        }
        return null;
    }
    public StorageItem getSymbol(String label){
        if(this.hasItem){
            for (StorageItem datum : this.data) {
                if (StorageItem.getValue(datum, label) != null) {
                    return StorageItem.getSymbol(datum, label);
                }
            }
        }
        return null;
    }
    public static StorageItem getSymbol(StorageMenu sm,String label){
        if(sm.hasItem){
            for(int i = 0;i < sm.data.size();i++){
                if(StorageItem.getValue(sm.data.get(i),label)!=null){
                    return StorageItem.getSymbol(sm.data.get(i),label);
                }
            }
        }
        return null;
    }

}
