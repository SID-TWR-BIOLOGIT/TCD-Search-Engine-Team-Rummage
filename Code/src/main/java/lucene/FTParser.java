package lucene;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;

class ParserFT {

    String baseDir = "";
    ArrayList<String> files = new ArrayList<String>();

    ParserFT(String path) {
        baseDir = path;
    }

    void createFileList() {
        ArrayList<String> dir = new ArrayList<String>();
        File currentDir = new File(baseDir);
        File[] dirList = currentDir.listFiles();
        for (File file : dirList) {
            try {
                if (file.isDirectory()) {
                    dir.add(file.getCanonicalPath());
                    // files.add(file.getCanonicalPath());
                    File curDir = new File(file.getCanonicalPath());
                    File[] curFiles = curDir.listFiles();
                    ArrayList<String> tempList = new ArrayList<String>();
                    for (File str : curFiles) {
                        tempList.add(str.getCanonicalPath());
                    }
                    files.addAll(tempList);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

public class FTParser {

    public static void main(String[] args) {
        Parse();
    }

    public static void Parse() {
        ParserFT p = new ParserFT("../Assignment Two Dataset/ft/");
        p.createFileList();
        BufferedReader br = null;

        System.out.println(p.files.size());
        int k = 0;
        for (String str : p.files) {
            try {
                StringBuilder sb = new StringBuilder();
                PrintWriter writer = new PrintWriter("A2-DOC/" + "DOC_" + k, "UTF-8");
//				System.out.println(str);

                String line = null;
                k++;
                br = new BufferedReader(new FileReader(str));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String[] content = sb.toString().split("</DOC>");
//				System.out.println(content.length);
                BufferedWriter bw = new BufferedWriter(writer);
                for (int i = 0; i < content.length; i++) {
                    bw.write("<DOC>");
                    String topic = StringUtils.substringBetween(content[i], "<HEADLINE>", "</HEADLINE>").trim(); // Topic
                    String text = "";
                    if (StringUtils.contains(content[i], "<TEXT>")) {
                        text = StringUtils.substringBetween(content[i], "<TEXT>", "</TEXT>").trim(); // Topic
                    } else {
                        text = StringUtils.substringBetween(content[i], "<DATELINE>", "</DATELINE>").trim(); // Topic
                    }
                    bw.write("<TITLE>" + topic + "</TITLE>");
                    bw.write("<TEXT>" + text + "</TEXT>");
                    bw.write("</DOC>");

                }
                bw.flush();
                bw.close();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NullPointerException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            } 
        }

    }
}