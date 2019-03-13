package com.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    private static Logger logger  = Logger.getLogger(FileUtils.class);

    public static boolean createFile(String filePath){
        boolean flag = false;
        File newF = new File(filePath);
        if(!newF.exists()){
            try {
                newF.createNewFile();
            }catch (IOException e){
                e.getStackTrace();
            }
            flag = true;
        }
        return flag;
    }
    public static int getDocno(){
        int result = 0;
        String temp;

        File file = new File("D:\\");
        File[] tempFile = file.listFiles();
        for(int i = 0; i < tempFile.length; i++){
            System.out.println(tempFile[i].getName());
            if(tempFile[i].getName().startsWith(".anxianglong")){
                temp = tempFile[i].getName().replace(".anxianglong","");
                result = Integer.valueOf(temp);
                break;
            }
        }
        return result;
    }

    /**
     * 创建隐藏文件
     *
     * @param docno 单据号(纯数字)
     */
    public static void createHideFile(int docno){

        try{
            //创建新文件
            File file= new File("D:\\.anxianglong"+docno);

            //删除文件并创建新文件
            file.delete();
            file.createNewFile();

            //拼dos命令
            //attrib的祥细功能介绍请在DOS内输入"attrib/?"查看
            String sets="attrib +H \"" + file.getAbsolutePath() + "\"";
            //运行命令串
            Runtime.getRuntime().exec(sets);
        }catch (Exception e){
            logger.error("创建隐藏文件失败！",e);
        }
    }

    /**
     * 左补零
     * @param str
     * @param strLength
     * @return
     */
    public static String addZeroForNum(String str,int strLength) {
        int strLen =str.length();
        if (strLen <strLength) {
            while (strLen< strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);//左补0
//    sb.append(str).append("0");//右补0
                str= sb.toString();
                strLen= str.length();
            }
        }

        return str;
    }

    public static void main(String[] args) {
        getDocno();
    }
}
