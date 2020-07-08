import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

/**
 * @author wanggh8
 * @version 1.0
 * @date 2020/7/8 10:58
 */

public class NewBlog implements ActionListener{

    JButton btnCreate;
    JTextField tName;
    JLabel jlHint;
    String path = "";

    public static void main(String[] args) {
        new NewBlog();
    }

    public NewBlog(){

        final JFrame jf = new JFrame("New Blog");
        Container c = jf.getContentPane();
        jf.setBounds(700, 350, 400, 300);
        c.setLayout(null);

        jlHint = new JLabel("Please input blog name!     ",JLabel.CENTER);
        jlHint.setBounds(0, 30, 400, 30);
        c.add(jlHint);

        JLabel jl = new JLabel("Name");
        jl.setBounds(125, 80, 150, 30);
        c.add(jl);

        tName = new JTextField("");
        tName.setBounds(125, 120, 150, 30);
        c.add(tName);

        btnCreate=new JButton("Create");
        btnCreate.setBounds(125, 180, 150, 30);

        c.add(btnCreate);
        c.setBackground(Color.white);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        btnCreate.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String name = tName.getText();
        if (name!=null && "".equals(name)){
            jlHint.setText("Error: name cannot be empty!");
            return;
        }
        JFileChooser jfc=new JFileChooser("D:\\Github\\Blog");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
        int jfcRes = jfc.showDialog(new JLabel(), "选择路径");
        File file=jfc.getSelectedFile();
        String fileName = jfc.getSelectedFile().getName();

        if (jfcRes == 1) {
            System.out.println("cancel");
        }

        if(jfcRes == 0 && file != null && file.isDirectory()){
            if (fileName.equals("Blog")) {
                jlHint.setText("Error: cannot choose root directory!");
                return;
            }
            path = file.getAbsolutePath();
            System.out.println(fileName);
            System.out.println(path);
            if (0 == createFile(name,path, fileName)) {
                jlHint.setText("Success: have created new blog!");
            }
            else {
                jlHint.setText("Error: file name exists!");
            }
        }

    }

    public int createFile(String name, String path, String tag) {
        /**
        * @describe 根据文件名创建文件
        * @param [name:文件名, path:文件路径, tag:MarkDown标签]
        * @return int 0:成功, 1:文件已存在失败
        */
        System.out.println(name);
        System.out.println(path);
        File file = new File(path, name+".md");
        if (file.exists()) {
            System.out.println(file.exists());
            return 1;
        }
        try {
            file.createNewFile(); // 创建文件
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdate = formatter.format(date);

        String str = "--- \ntitle: " + name + "\ndate: " + nowdate + "\ntags: " + tag+ "\n---\n\n<!--more-->\n";

        byte bt[] = str.getBytes();

        try {
            FileOutputStream in = new FileOutputStream(file);
            try {
                in.write(bt, 0, bt.length);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
