package com.core;

import com.util.FileUtils;
import com.util.POIUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UpLoad {

    public void UpLoadFile(String title) {
        JFrame jframe = new JFrame(title);// 实例化一个JFrame
        JPanel jPanel = new JPanel(); // 创建一个轻量级容器
        JToolBar jToolBar = new JToolBar(); // 提供了一个用来显示常用的 Action 或控件的组件
        jframe.setVisible(true);// 可见
        jframe.setSize(300, 300);// 窗体大小
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// close的方式
        jframe.setContentPane(jPanel); // 设置 contentPane 属性。
        JLabel jl = new JLabel("请选择：");// 创建一个Label标签
        jl.setHorizontalAlignment(SwingConstants.LEFT);// 样式，让文字居中
        jPanel.add("North", jl);// 将标签添加到容器中
        jPanel.add("North", jToolBar);
        JButton developer = new JButton("上传文件");
        developer.setHorizontalAlignment(SwingConstants.CENTER);
        jToolBar.add(developer);// 上传文件按钮添加到容器
        jPanel.add("North", jToolBar);
        developer.addMouseListener(new MouseAdapter() { // 添加鼠标点击事件
            public void mouseClicked(MouseEvent event) {
                eventOnImport(new JButton());
            }
        }); // 文件上传功能
    }

    /**
     * 文件上传功能
     *
     * @param developer
     *            按钮控件名称
     */
    public static void eventOnImport(JButton developer) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);//一次只能上传一个文件
        /** 过滤文件类型 * */
        FileNameExtensionFilter filter = new FileNameExtensionFilter("xls", "xls");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(developer);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            /** 得到选择的文件* */
            File choseFile = chooser.getSelectedFile();

            FileInputStream input = null;
            FileOutputStream out = null;
            String path = System.getProperty("java.io.tmpdir");
            try {
//                File dir = new File(path);
                /** 目标文件夹 * */
//                File[] fs = dir.listFiles();
//                HashSet<String> set = new HashSet<String>();
//                for (File file : fs) {
//                    set.add(file.getName());
//                }
                /** 判断是否已有该文件* */
//                    if (set.contains(f.getName())) {
//                        JOptionPane.showMessageDialog(new JDialog(),
//                                f.getName() + ":该文件已存在！");
//                        return;
//                    }
//                input = new FileInputStream(choseFile);
//                byte[] buffer = new byte[1024];
//                File des = new File(path, choseFile.getName());
//                out = new FileOutputStream(des);
//                int len = 0;
//                while (-1 != (len = input.read(buffer))) {
//                    out.write(buffer, 0, len);
//                }
//                out.close();
//                input.close();

                List<String[]> list = POIUtil.readExcel(choseFile);
                StringBuffer str = new StringBuffer();
                for (int i = 0 ; i<list.size();i++){
                    for (int j = 0;j<list.get(i).length;j++){
                        String[] temp = list.get(i);
                        str.append(temp[j]).append("\t");
                    }
                    str.append("\r\n");
                }
                String txtFileName = "D:\\B0255N02"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                        +".txt";
                FileUtils.createFile(txtFileName);
                FileWriter fileWriter = new FileWriter(txtFileName);
                fileWriter.write(str.toString());
                fileWriter.flush();
                fileWriter.close();

                JOptionPane.showMessageDialog(null, "上传成功！", "提示",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "上传失败！", "提示",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

}
