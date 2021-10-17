package Iterator;

import java.util.ArrayList;

public class DataProvider<T> {
    private int pointer1 = 0;//一维指针，高维
    private int pointer2 = 0;//二维指针，低维
    private int currentSteps = 0;
    private int maxSteps = 0;
    public boolean hasNext = true;
    private ArrayList<ArrayList<T>> data = new ArrayList<>();
    private int getSize = 0;
    public DataProvider(){

    }
    /**
     * 二维数据集提供器，对于每一分类（一维），从一维的二维中取得给定长度的数据。
     * 不会跨越第一
     * */
    public DataProvider(ArrayList<ArrayList<T>> data,int getSize) {
        this.data = data;
        this.getSize = getSize;
        if(!(this.data.size() >0&&this.data.get(0).size()>0)){
            hasNext = false;
        }
        getMaxSteps();
    }
    public void printPointers(){
        System.out.println("pointer1: "+pointer1+"\n"+"pointer2 "+pointer1);
    }
    public int getCurrentSteps(){
        return currentSteps;
    }
    public void setGetSize(int getSize){
        this.getSize = getSize;
    }
    public void setData(ArrayList<ArrayList<T>> data){
        this.data = data;
        if(!(this.data.size() >0&&this.data.get(0).size()>0)){
            hasNext = false;
        }
        getMaxSteps();

    }
    /**将迭代指针置0，使得迭代可以从头开始。 */
    public void setPointerInit(){
        pointer1 = 0;
        pointer2 = 0;
        currentSteps = 0;
    }
    public void getMaxSteps(){
        int i = 0;
        while(this.get().size()>1){
            i++;
        }
        this.maxSteps = i;
        setPointerInit();
    }
    public void run(int steps){
        if(steps>maxSteps){
            steps = maxSteps;
        }else if(steps<=0){
            steps = 0;
        }
        while(steps>0){
            steps--;
            get(0);
        }
    }
    public void back(){
        setPointerInit();
        for(int i = 0;i<currentSteps;i++){
            get(0);
        }
    }
    /**从对象的数据中取出适当数据，并返回。
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
        return collector;
    }
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

