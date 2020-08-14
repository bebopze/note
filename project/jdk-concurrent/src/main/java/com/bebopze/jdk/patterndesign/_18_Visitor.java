package com.bebopze.jdk.patterndesign;

import java.util.List;
import java.util.ArrayList;

/**
 * 18. 访问者模式
 *
 * @author bebopze
 * @date 2020/8/12
 */
public class _18_Visitor {


    // 核心：         解耦


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        test_1();

    }

    private static void test_1() {

        List<ResourceFile> resourceFiles = listAllResourceFiles("");

        Extractor extractor = new Extractor();
        for (ResourceFile resourceFile : resourceFiles) {
            resourceFile.accept(extractor);
        }

        Compressor compressor = new Compressor();
        for (ResourceFile resourceFile : resourceFiles) {
            resourceFile.accept(compressor);
        }
    }


    private static List<ResourceFile> listAllResourceFiles(String resourceDirectory) {

        List<ResourceFile> resourceFiles = new ArrayList<>();

        // ...根据后缀(pdf/ppt/word)   由工厂方法创建不同的类对象(PdfFile/PPTFile/WordFile)
        resourceFiles.add(new PdfFile("a.pdf"));
        resourceFiles.add(new WordFile("b.word"));
        resourceFiles.add(new PPTFile("c.ppt"));

        return resourceFiles;
    }
}


// -------------------------------- 实现 ------------------------------------------


abstract class ResourceFile {
    protected String filePath;

    public ResourceFile(String filePath) {
        this.filePath = filePath;
    }

    abstract public void accept(Visitor vistor);
}

class PdfFile extends ResourceFile {
    public PdfFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    //...
}

class PPTFile extends ResourceFile {
    public PPTFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    //...
}

class WordFile extends ResourceFile {
    public WordFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    //...
}


interface Visitor {
    void visit(PdfFile pdfFile);

    void visit(PPTFile pdfFile);

    void visit(WordFile pdfFile);
}

class Extractor implements Visitor {
    @Override
    public void visit(PPTFile pptFile) {
        //...
        System.out.println("提取 PPT.");
    }

    @Override
    public void visit(PdfFile pdfFile) {
        //...
        System.out.println("提取 PDF.");
    }

    @Override
    public void visit(WordFile wordFile) {
        //...
        System.out.println("提取 WORD.");
    }
}

class Compressor implements Visitor {
    @Override
    public void visit(PPTFile pptFile) {
        //...
        System.out.println("压缩 PPT.");
    }

    @Override
    public void visit(PdfFile pdfFile) {
        //...
        System.out.println("压缩 PDF.");
    }

    @Override
    public void visit(WordFile wordFile) {
        //...
        System.out.println("压缩 WORD.");
    }

}

