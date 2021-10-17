import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class AppLoader extends JPanel {
    JLabel je = null;
    JLabel jl = null;
    JButton jb = null;
    public AppLoader(String name,String sign) {
        super();
        String DEVELOPER_DEFAULT_ICON_PATH = "rel_TrayIcon.png";
//        setting(name,sign,"");
        setting(name,sign,DEVELOPER_DEFAULT_ICON_PATH);
    }
    public AppLoader(String name,String sign,String iconRes) {
        super();
        setting(name, sign, iconRes);
    }
    public boolean startWith(String str, String with_str){
        String[] split_str = str.split("_",2);
        return split_str[0].equals(with_str);
    }
    public void setting(String name,String sign,String iconRes) {
        sign = sign.trim();
        setLayout(null);
        setSize(200,200);
        jl = new JLabel(name,JLabel.CENTER);
        jb = new JButton("启动");
        String finalSign = sign;
        jb.addActionListener(e->{
            if(startWith(finalSign,"open")){
                /*使用默认程序 打开文本文件和图片等。
                * */
                Desktop dek = Desktop.getDesktop();
                if(dek.isSupported(Desktop.Action.OPEN)){
                    try {
                        dek.open(new File(finalSign.split("_", 2)[1]));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }else if(startWith(finalSign,"url")){
                Desktop dek = Desktop.getDesktop();
                if(dek.isSupported(Desktop.Action.BROWSE)){
                    try {
                        dek.browse(new URI(finalSign.split("_", 2)[1]));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            }else{
                Runtime rt = Runtime.getRuntime();
                try {
                    Process process = rt.exec(finalSign.split(" "));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
        Icon icon = null;
        if(iconRes.startsWith("http")){
            /*
            * 从网络中获取地址
            * */
            try{
                icon = new ImageIcon(new URL(iconRes));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else if(iconRes.startsWith("rel")){
            URL url = Utils.class.getResource(iconRes.substring(4));
            assert url != null;
            icon = new ImageIcon(url) ;
            //ImageIcon url,filename,Image
        }else if(iconRes.split(":").length<2){
            /*
            * use default AppIcon file path,
            * */
            String DEFAULT_IMAGE_ICON_PATH = "C:\\Users\\Public\\Roaming\\AppLoader\\Icons\\";
            icon = new ImageIcon(DEFAULT_IMAGE_ICON_PATH + iconRes);
        }else{
            //use absolute path
            icon = new ImageIcon(iconRes);
        }

        je = (icon!=null&&icon.getIconWidth()!=-1)?new JLabel(icon,JLabel.CENTER):new JLabel(name,JLabel.CENTER);
        int DEFAULT_CENTER_X = 100;
        int DEFAULT_CENTER_Y = 80;
        if(icon!=null&&icon.getIconWidth() != -1&&icon.getIconHeight()<=80&&icon.getIconWidth()<=80){
            je.setBounds(
            DEFAULT_CENTER_X - (icon.getIconWidth() / 2),
            DEFAULT_CENTER_Y - (icon.getIconHeight() / 2),
            icon.getIconWidth(),
            icon.getIconHeight());
        }else if(icon!=null&&icon.getIconWidth() != -1){
            je.setBounds(50,40,80,80);
        }
        else
        {
            je.setBounds(40,40,120,100);
            je.setForeground(Color.BLUE);
        }
        jl.setBounds(40,120,120,40);
        jb.setBounds(40,160,120,40);
        jl.setForeground(new Color(0,0,0,0xf5));
//        jl.setOpaque(true);
//        jl.setBackground(new Color(0,0,0));
        this.add(je);
        this.add(jl);
        this.add(jb);
    }
    public void componentLayoutLocationChange(int x,int y){
        setSize(getX()+x,getY()+y);
        this.je.setBounds(this.je.getX()+x,this.je.getY()+y,this.je.getWidth(),this.je.getHeight());
        this.jl.setBounds(this.jl.getX()+x,this.jl.getY()+y,this.jl.getWidth(),this.jl.getHeight());
        this.jb.setBounds(this.jb.getX()+x,this.jb.getY()+y,this.jb.getWidth(),this.jb.getHeight());
//        System.out.println("Apploader已自适应扩展");
    }
}
