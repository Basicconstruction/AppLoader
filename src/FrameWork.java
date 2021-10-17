import Iterator.DataProvider;
import permanent_storage.PermanentStorage;
import permanent_storage.StorageItem;
import permanent_storage.StorageMenu;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class FrameWork extends JFrame {
    private DataProvider<ArrayList<String>> dataProvider;
    public ArrayList<ArrayList<String>> currentLoadData = new ArrayList<>();
    public ArrayList<ArrayList<ArrayList<String>>> totalData = new ArrayList<>();
    private JDialog jd = null;
    private final int y_expend = 39;
    private final int x_expend = 16;
    private final int num = 28;
    public PermanentStorage ps;
    private final String ps_path = "C:\\Users\\Public\\Roaming\\AppLoader.ps";
    JPanel jPanel = new JPanel(new GridLayout(4,7));
    JScrollPane jsc = null;
    public FrameWork()  {
        super();
        RevolveWindowsChanged();
        setTitle("应用启动器");
        setLayout(null);
        setBounds(200,100,1400+x_expend,800+y_expend*2);
        setPreferredSize(new Dimension(1400,800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tackleGeneratePath();
        generate_load();
        loadData();
        AddedToJPanel();
        jsc = new JScrollPane(jPanel);
        setContentPane(jsc);
        tackleMenuBar();
        tackleSystemTray();
        jsc.addMouseListener(
            new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (!dataProvider.hasNext){
                        dataProvider.setPointerInit();
                    }
                    currentLoadData = dataProvider.get();
                    AddedToJPanel();
                    jsc.removeAll();
                    jsc.add(jPanel);
//                    jsc.getVerticalScrollBar().setUnitIncrement(16);
                    jsc.repaint();
                    jPanel.setSize(jsc.getWidth(),jsc.getHeight());
//                    System.out.println(jPanel.getHeight()+" "+jsc.getHeight());
                    setVisible(true);
                }
            }

          @Override
          public void mousePressed(MouseEvent e) {}

          @Override
          public void mouseReleased(MouseEvent e) {}

          @Override
          public void mouseEntered(MouseEvent e) {}

          @Override
          public void mouseExited(MouseEvent e) {}
        });
        setVisible(true);
    }
    public void RevolveWindowsChanged(){
        this.addWindowStateListener(e -> {
            int currentState = e.getNewState();
            switch (currentState) {
                case Frame.NORMAL, Frame.MAXIMIZED_BOTH -> {
                    jsc.setSize(this.getWidth()-x_expend,this.getHeight()-2*y_expend);
                    jPanel.setSize(jsc.getWidth(), jsc.getHeight());
                    jPanel.repaint();
                    jsc.repaint();
                    this.repaint();
                    setVisible(true);
                }
                default -> jPanel.setSize(0, 0);
            }
//            System.out.println(jPanel.getHeight()+" "+jsc.getHeight());
        });
    }
    public void generate_load(){
        if(new File(ps_path).exists()&&PermanentStorage.getInstance(new File(ps_path)).getValue("load_path")!=null){
            ps = PermanentStorage.getInstance(new File(ps_path));
            StorageMenu settings = PermanentStorage.getStorageMenu(new File(ps.getValue("settings_load")));
            settings.includedInParent = false;
            settings.singleLocation = ps.getValue("settings_load");
            StorageMenu app = PermanentStorage.getStorageMenu(new File(ps.getValue("app_load")));
            app.includedInParent = false;
            app.singleLocation = ps.getValue("app_load");
            StorageMenu url = PermanentStorage.getStorageMenu(new File(ps.getValue("url_load")));
            url.includedInParent = false;
            url.singleLocation = ps.getValue("url_load");
            StorageMenu filepath = PermanentStorage.getStorageMenu(new File(ps.getValue("filepath_load")));
            filepath.includedInParent = false;
            filepath.singleLocation = ps.getValue("filepath_load");
            ps.add(settings);
            ps.add(app);
            ps.add(url);
            ps.add(filepath);
        }else{
            ps = new PermanentStorage(new File(ps_path));
            StorageMenu sm0 = new StorageMenu("load_path","specific path");
            ps.add(sm0);
            sm0.add(new StorageItem("settings_load", "C:\\Users\\Public\\Roaming\\AppLoader\\settings.ps"));
            sm0.add(new StorageItem("app_load", "C:\\Users\\Public\\Roaming\\AppLoader\\app.ps"));
            sm0.add(new StorageItem("url_load", "C:\\Users\\Public\\Roaming\\AppLoader\\url.ps"));
            sm0.add(new StorageItem("filepath_load", "C:\\Users\\Public\\Roaming\\AppLoader\\filepath.ps"));
            //sm1 dialog settings
            StorageMenu sm1 = new StorageMenu("settings",ps.getValue("settings_load"),false,ps.getValue("settings_load"));
            StorageItem si1= new StorageItem("Dialog1");
            ps.add(sm1);
            sm1.add(si1);
            StorageItem selected = new StorageItem("radio1","true");
            si1.add(selected);
            si1.add(new StorageItem("imgLocation","C:\\Users\\Public\\Roaming\\testIcon"));
            StorageMenu app = new StorageMenu("app", ps.getValue("app_load"));
            ps.add(app);
            app.includedInParent = false;
            app.singleLocation = ps.getValue("app_load");
            app.add(new StorageItem("app_data_file","open_"+ps.getValue("app_load")));
            StorageMenu url = new StorageMenu("url", ps.getValue("url_load"));
            ps.add(url);
            url.includedInParent = false;
            url.singleLocation = ps.getValue("url_load");
            url.add(new StorageItem("url_data_file","open_"+ps.getValue("url_load")));
            StorageMenu filepath = new StorageMenu("filepath", ps.getValue("filepath_load"));
            ps.add(filepath);
            filepath.includedInParent = false;
            filepath.singleLocation = ps.getValue("filepath_load");
            filepath.add(new StorageItem("filepath_data_file","open_"+ps.getValue("filepath_load")));
        }
        File file2 = new File(ps.getValue("imgLocation"));
        if(!file2.exists()){
            try{
                boolean created = file2.createNewFile();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        ps.write();

    }
    public void refresh(){
        int x = dataProvider.getCurrentSteps();
        generate_load();
        totalData = new ArrayList<>();
        totalData.add(CSVParser.parser(ps.getMenuSymbol("app").getStringBuilder(),2,3));
        totalData.add(CSVParser.parser(ps.getMenuSymbol("url").getStringBuilder(),2,3));
        totalData.add(CSVParser.parser(ps.getMenuSymbol("filepath").getStringBuilder(),2,3));
        dataProvider = new DataProvider<>(totalData,num);
        dataProvider.run(x-1);
        currentLoadData = dataProvider.get();
        AddedToJPanel();
        jsc.removeAll();
        jsc.add(jPanel);
        jsc.getVerticalScrollBar().setUnitIncrement(16);
        jsc.repaint();
        jPanel.setSize(jsc.getWidth(),jsc.getHeight());
        setVisible(true);
    }
    public void AddedToJPanel(){
//        jPanel = null;
//        jPanel = new JPanel(new GridLayout(4,7));
//        setBounds(200,100,1400+x_expend,800+y_expend*2);
//        jPanel.setSize(1400,800);
//        int counter = 0;
//        jPanel.setBounds(0,0,1400,800);
        jPanel.removeAll();
        for (ArrayList<String> datum : currentLoadData) {
//            counter++;
            AppLoader appLoader = null;
            if (datum.size() < 3) {
                appLoader = new AppLoader(datum.get(0), datum.get(1));
            } else {
                appLoader = new AppLoader(datum.get(0), datum.get(1), datum.get(2));
            }
//            int x_dimension = 200;
//            int y_dimension = 200;
//            int tmp_x = ((counter-1)%7)*(x_dimension);
//            int edit = counter%7==0?1:0;
//            int tmp_y = (counter/7-edit)*(y_dimension);
//            appLoader.setBounds(tmp_x,tmp_y,x_dimension,y_dimension);
            jPanel.add(appLoader);

        }

//        jPanel.setPreferredSize(new Dimension(1400,800));

    }

    public Dialog tackleJMenuItem11_Dialog(JFrame jf,String title,boolean mode){
        //develop not finished
        int JD_WIDTH = 800;
        int JD_HEIGHT = 500;
        this.jd = new JDialog(jf,title,mode);
        jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jd.setBounds(700,300,JD_WIDTH,JD_HEIGHT);
        this.jd.setLayout(null);
        boolean jr1_boolean;
        jr1_boolean = ps.getValue("radio1").equals("true");
        JRadioButton jr1 = new JRadioButton("use default path to load file",jr1_boolean);
        jr1.setBounds(40,20,200,40);
        this.jd.add(jr1);
        JLabel jl1 = new JLabel("file path of the initial file",JLabel.LEFT);
        jl1.setBounds(47,80,200,40);
        this.jd.add(jl1);
        JTextField jt1 = new JTextField(ps.getValue("location"), 200);
        jt1.setLocation(247,80);
        jt1.setSize(300,30);
        this.jd.add(jt1);
        JButton jbt1 = new JButton("select file");
        jbt1.setBounds(547,80,80,30);
        this.jd.add(jbt1);
        jbt1.addActionListener(e->{
            JFileChooser fileChooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("数据集文件(.csv,.txt,.c,.cpp,.jl,.rc,.go)",
                    "csv","txt","c","cpp","jl","rc","go");
            fileChooser.setFileFilter(filter);
            int i = fileChooser.showDialog(this.jd,"open");
            if(i == JFileChooser.APPROVE_OPTION){
                ps.setValue("imgLocation",fileChooser.getSelectedFile().getPath());
                jt1.setText(ps.getValue("imgLocation"));
            }
        });
        if(jr1.isSelected()){
            jbt1.setEnabled(false);
            jt1.setEnabled(false);
        }else{
            jbt1.setEnabled(true);
            jt1.setEnabled(true);
        }
        jr1.addActionListener(e->{
            if(jr1.isSelected()){
                ps.setValue("radio1","true");
                jbt1.setEnabled(false);
                jt1.setEnabled(false);
            }else{
                ps.setValue("radio1","false");
                jbt1.setEnabled(true);
                jt1.setEnabled(true);
            }
        });
        JButton bj1 = new JButton("ok");
        JButton bj2 = new JButton("cancel");
        JButton bj3 = new JButton("apply");
        int bj_width = 80;
        int bj_height = 30;
        int ex_inside = 10;
        int bj_x = JD_WIDTH - 3 * bj_width-x_expend-2*ex_inside;
        int bj_y = JD_HEIGHT - bj_height - y_expend;
        bj1.setBounds(bj_x,bj_y,bj_width,bj_height);
        bj2.setBounds(bj_x+bj_width+ex_inside,bj_y,bj_width,bj_height);
        bj3.setBounds(bj_x+2*bj_width+2*ex_inside,bj_y,bj_width,bj_height);
        bj1.addActionListener(e->{
            ps.setValue("radio1",jr1.isSelected()?"true":"false");
            ps.setValue("location",jt1.getText());
            ps.write();
            this.jd.setVisible(false);
        });
        bj2.addActionListener(e-> this.jd.setVisible(false));
        bj3.addActionListener(e->{
            ps.setValue("radio1",jr1.isSelected()?"true":"false");
            ps.setValue("location",jt1.getText());
            ps.write();
        });
        this.jd.add(bj1);
        this.jd.add(bj2);
        this.jd.add(bj3);
        return this.jd;
    }
    public void tackleSystemTray(){
        if(SystemTray.isSupported()){
            URL res = this.getClass().getResource("TrayIcon.png");
            assert res != null;
            ImageIcon ic = new ImageIcon(res);
            PopupMenu pm = new PopupMenu();
            MenuItem m1 = new MenuItem("exit");
            m1.addActionListener(e -> System.exit(0));
            MenuItem m2 = new MenuItem("MainFrame");
            m2.addActionListener(e->{
                setBounds(200,100,1416,839);
                setVisible(true);
            });
            pm.add(m2);
            pm.add(m1);
            TrayIcon trayIcon = new TrayIcon(ic.getImage(),"AppLoader",pm);
            trayIcon.setImageAutoSize(true);
            SystemTray systemTray = SystemTray.getSystemTray();
            try{
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }
    public void tackleMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu1 = new JMenu("setting");
        JMenu menu2 = new JMenu("help");
        menuBar.add(menu1);
        menuBar.add(menu2);
        JMenuItem menuItem11 = new JMenuItem("load data file");
        menu1.add(menuItem11);
        JMenuItem menuItem12 = new JMenuItem("refresh");
        menuItem12.addActionListener(e-> this.refresh());
        menu1.add(menuItem12);
        JMenuItem menuItem13 = new JMenuItem("exit");
        menu1.add(menuItem13);
        menuItem11.addActionListener(e-> tackleJMenuItem11_Dialog(FrameWork.this
                ,"load file configuration"
                ,true).setVisible(true));
        menuItem13.addActionListener(e-> System.exit(0));
        JMenuItem menuItem21 = new JMenuItem("web help");
        menu2.add(menuItem21);
        menuItem21.addActionListener(e-> Utils.Browse("https://github.com/Basicconstruction/AppLoader/tree/Preferred_dimension"));
    }
    public void loadData(){
//        data = CSVParser.parser(new File(DEFAULT_LOAD_FILE),3);
        totalData = new ArrayList<>();
        totalData.add(CSVParser.parser(ps.getMenuSymbol("app").getStringBuilder(),2,3));
        totalData.add(CSVParser.parser(ps.getMenuSymbol("url").getStringBuilder(),2,3));
        totalData.add(CSVParser.parser(ps.getMenuSymbol("filepath").getStringBuilder(),2,3));
        dataProvider = new DataProvider<>(totalData,num);
        currentLoadData= dataProvider.get();
    }
    public void tackleGeneratePath(){
        System.out.println(initPath());
    }
    /**
     * 生成文件夹，以便继续生成其他文件*/
    public boolean initPath(){
        return initPath("C:\\Users\\Public\\Roaming\\AppLoader\\Icons")&&initPath("C:\\Users\\Public\\Roaming\\AppLoader\\");
    }
    public boolean initPath(String path){
        File file = new File(path);
        return initPath(file);
    }
    public boolean initPath(File file){
        if(!file.exists()){
            return file.mkdirs();
        }
        return true;
    }
}

