import javax.swing.*;
import java.io.File;

public class TestJPanel extends JFrame {
    public TestJPanel(){
        super();
        setLayout(null);
        setBounds(1000,500,216,239);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AppLoader jk = new AppLoader("AE","notepad.exe");
        setContentPane(jk);
        setTitle("TestJPane");
        jk.setVisible(true);
        JButton jh = new JButton("删除");
        jh.setBounds(0,0,100,100);
        jh.addActionListener(e->{
            File f = new File("H:\\Desktop\\git-Methods\\WeChat.jl");
            if(f.exists()){
                f.delete();
            }
            System.out.println("ok!");
        });
        jk.add(jh);
        setVisible(true);
    }
    public static void main(String[] args){
        new TestJPanel();
    }

}
