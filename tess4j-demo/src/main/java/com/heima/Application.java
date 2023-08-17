package com.heima;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class Application {
    /**
     * 识别图片中的文字
     * @param args
     */
    public static void main(String[] args) throws TesseractException {

        //创建实例
        ITesseract tesseract = new Tesseract();

        //设置字体库路径
        tesseract.setDatapath("D:\\DOWNLOAD\\Tess4J\\tessdata");
        //设置语言
        tesseract.setLanguage("chi_sim");

        File file = new File("D:/DOWNLOAD/imc图片/Snipaste_2022-10-09_16-37-47.png");

        //识别图片
        String s = tesseract.doOCR(file);

        System.out.println("文字识别结果:"+s);

    }
}
