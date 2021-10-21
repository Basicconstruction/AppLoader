package Iterator;

import java.util.ArrayList;

public class DataProvider<T> {
    private int pointer1 = 0;//一维指针，高维
    private int pointer2 = 0;//二维指针，低维
    private int currentSteps = 0;
    private int maxSteps = 0;
    public boolean hasNext = true;
    private ArrayList<ArrayList<T>> data;
    public final ArrayList<ArrayList<Integer>> storagePointer = new ArrayList<>();
    public final ArrayList<ArrayList<Integer>> storageExcelPointer = new ArrayList<>();
    private int getSize;
    /**
     * 二维数据集提供器，对于每一分类（一维），从一维的二维中取得给定长度的数据。
     * 不会跨越第一
     * */
    public DataProvider(ArrayList<ArrayList<T>> data, int getSize) {
        this.data = data;
        this.getSize = getSize;
        if(!(this.data.size() >0&&this.data.get(0).size()>0)){
            hasNext = false;
        }
        getMaxSteps();
        ArrayList<Integer> tp = new ArrayList<>(2);
        tp.add(0);
        tp.add(0);
        storagePointer.add(tp);//add 0 0 in!
        storageExcelPointer.add(tp);//add 0 0 in!
    }
    public void printPointers(){
        System.out.println("pointer1: "+pointer1+"\n"+"pointer2 "+pointer1);
    }
    public int getCurrentSteps(){
        return currentSteps;
    }
    /**将迭代指针置0，使得迭代可以从头开始。 */
    public void setPointerInit(){
        pointer1 = 0;
        pointer2 = 0;
        currentSteps = 0;
    }
    public int getMaxSteps(){
        int i = 0;
        for (ArrayList<T> datum : data) {
            i += datum.size() % getSize == 0 ? datum.size() / getSize : datum.size() / getSize + 1;
        }
        this.maxSteps = i;
        return i;
    }
    /**
     * need to change when add another get function
     * */
    public void run(int steps){
        currentSteps = steps;
        if(currentSteps<=storagePointer.size()-1){
            pointer1 = storagePointer.get(currentSteps).get(0);
            pointer2 = storagePointer.get(currentSteps).get(1);
        }else{
            currentSteps = storagePointer.size()-1;
            pointer1 = storagePointer.get(currentSteps).get(0);
            pointer2 = storagePointer.get(currentSteps).get(1);
            for(int h = currentSteps;h<=steps;h++){
                get();
            }
        }
    }
    /**
     * 回退一步
     * */
    public void back(){
        pointer1 = storagePointer.get(currentSteps-1).get(0);
        pointer2 = storagePointer.get(currentSteps-1).get(1);
        currentSteps--;
    }
    /**从对象的数据中取出适当数据，并返回。
     * 不跨越一维数组的取出形式
     * 工作参数:来自对象-pointer1,pointer2,getSize,data,返回ArrayList<T>
     *     即：想要返回什么，就传入想返回的类型包含的类型
     *     例如：想返回 ArrayList<ArrayList<String>>就传入ArrayList<String>
     * */
    public ArrayList<T> get(){
        currentSteps++;
        int counter = 0;
        boolean continued = true;
        ArrayList<T> collector = new ArrayList<>();
        for(int j = pointer1;j<data.size()&&continued;){
            while(counter< getSize){
                //必然入口
                if(pointer2<data.get(j).size()){
                    collector.add(data.get(j).get(pointer2));
                    counter++;
                    pointer2++;
                    if(pointer2==data.get(j).size()){
                        counter = getSize;
                        pointer2 = 0;
                        pointer1++;
                    }
                }else{
                    counter = getSize;//退出while循环
                    pointer2 = 0;
                    pointer1++;
                }
            }
            continued = false;
            //必然出口
        }
        if(pointer1<this.data.size()-1){
            hasNext = true;
        }else if(pointer1==this.data.size()-1){
            hasNext = pointer2 < this.data.get(pointer1).size() - 1;
        }else{
            hasNext = false;
        }
        if(storagePointer.size()<=currentSteps){
            ArrayList<Integer> tp = new ArrayList<>(2);
            tp.add(pointer1);
            tp.add(pointer2);
            storagePointer.add(tp);
        }
        return collector;
    }
    /**从对象的数据中取出适当数据，并返回。
     * 跨越一维数组的取出形式
     * 工作参数:来自对象-pointer1,pointer2,getSize,data,返回ArrayList<T>
     *     即：想要返回什么，就传入想返回的类型包含的类型
     *     例如：想返回 ArrayList<ArrayList<String>>就传入ArrayList<String>
     * */
    public void get(int param){
        currentSteps++;
        int counter = 0;
        boolean continued = true;
        for(int j = pointer1;j<data.size()&&continued;){
            while(counter< getSize){
                //必然入口
                if(pointer2<data.get(j).size()){
                    counter++;
                    pointer2++;
                    if(pointer2==data.get(j).size()){
                        counter = getSize;
                        pointer2 = 0;
                        pointer1++;
                    }
                }else{
                    counter = getSize;//退出while循环
                    pointer2 = 0;
                    pointer1++;
                }
            }
            continued = false;
            //必然出口
        }
        if(pointer1<this.data.size()-1){
            hasNext = true;
        }else if(pointer1==this.data.size()-1){
            hasNext = pointer2 < this.data.get(pointer1).size() - 1;
        }else{
            hasNext = false;
        }
    }


}

