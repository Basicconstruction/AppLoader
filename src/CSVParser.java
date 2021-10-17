import Iterator.TextIterator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVParser {
    public static ArrayList<ArrayList<String>> parser(File file,int rows,int maxRows){
        if(!file.exists()){
            System.out.println("文件不存在，请确保选择正确的文件来加载启动程序列表");
            return null;
        }
        if(!file.canRead()){
            System.out.println("文件正在被其他程序占用或者需要额外的权限访问，请尝试关闭占用软件或者重启计算机！");
            return null;
        }
        if(file.isDirectory()){
            System.out.println("你选择了一个文件夹，我们需要一个包含加载信息的文件来加载");
            return null;
        }
        String fileName = file.getName();
        int fileNameLength = fileName.length();
        if(!fileName.substring(fileNameLength - 4, fileNameLength).equals(".csv")){
            System.out.println("Warning,The file extension is not csv");
        }
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        int ignoreDataCollector = 0;
        try{
            FileReader reader = new FileReader(file);
            BufferedReader bf = new BufferedReader(reader);
            String singleData;
            while((singleData = bf.readLine()) != null){
                String[] al = singleData.split(",");
                extractParser(rows,maxRows, data, al);
            }
            bf.close();
            reader.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return data;

    }

    public static ArrayList<ArrayList<String>> parser(StringBuilder sb,int rows,int maxRows){
        return parser(sb,rows,maxRows,",");

    }
    public static ArrayList<ArrayList<String>> parser(StringBuilder sb,int rows,int maxRows,String split){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        TextIterator ti = new TextIterator(sb);
        String singleData;
        while((singleData = ti.getLine()) != null){
            String[] al = singleData.split(split);
            extractParser(rows, maxRows,data, al);
        }
        return data;

    }

    private static void extractParser(int rows, int maxRows, ArrayList<ArrayList<String>> data, String[] al) {
        int preferLength = 0;
        if(al.length>=rows&&al.length<=maxRows){
            preferLength = al.length;
        }else if(al.length>maxRows){
            preferLength = maxRows;
        }else{
            System.out.println("unused data.");
        }
        data.add(new ArrayList<>(Arrays.asList(al).subList(0,preferLength)));
    }
}
