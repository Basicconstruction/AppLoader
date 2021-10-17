package permanent_storage;

import java.util.ArrayList;

public class StorageItem {
    public ArrayList<StorageItem> data = new ArrayList<>();
    public String name;
    public String value;
    public boolean hasItem = false;
    public int order;
    public StorageItem(String name,String value) {
        this.name = name;
        this.value = value;
        this.order = 0;
    }
    public StorageItem(String name) {
        this.name = name;
        this.order = 0;
    }
    public void removeStorageItemByIndex(int index){
        data.remove(data.get(index));
        this.hasItem = this.data.size() > 0;
    }
    public void remove(StorageItem item){
        data.remove(item);
        this.hasItem = this.data.size() > 0;
    }
    public void add(StorageItem item){
        data.add(item);
        this.hasItem = true;
    }
    public void setValue(String label, String value){
        StorageItem.setValue(this,label,value);
    }
    public static void setValue(StorageItem item, String label, String value){
        if(item.name.equals(label)){
            item.value = value;
        }else{
            if(item.hasItem){
                for(int i = 0;i < item.data.size();i++){
                    StorageItem.setValue(item.data.get(i),label,value);
                }
            }
        }
    }
    public static String getValue(StorageItem item, String label){
        if(item.name.equals(label)){
            return item.value;
        }else{
            if(item.hasItem){
                for(int i = 0;i < item.data.size();i++){
                    if(StorageItem.getValue(item.data.get(i),label)!=null){
                        return StorageItem.getValue(item.data.get(i),label);
                    }
                }
            }
        }
        return null;
    }
    public static StorageItem getSymbol(StorageItem item, String label){
        if(item.name.equals(label)){
            return item;
        }else{
            if(item.hasItem){
                for(int i = 0;i < item.data.size();i++){
                    if(StorageItem.getValue(item.data.get(i),label)!=null){
                        return StorageItem.getSymbol(item.data.get(i),label);
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
                for(int i = 0;i < this.data.size();i++){
                    if(StorageItem.getValue(this.data.get(i),label)!=null){
                        return StorageItem.getValue(this.data.get(i),label);
                    }
                }
            }
        }
        return null;
    }


}
