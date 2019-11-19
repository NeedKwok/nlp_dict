package Utils;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class DirReader {
    /**
     * 处理文件的读操作
     */
    private static final String dic_ec = "dic_ec.txt" ;

    public static DirTree readByLine()  {
        File file = new File(dic_ec);
        //BufferedReader reader = null;
        BufferedReader bfReader = null;
        DirTree dirTree = new DirTree();
        try {
            InputStreamReader utf8Reader = new InputStreamReader(new FileInputStream(file));
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            bfReader = new BufferedReader(utf8Reader);
            String tempString;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = bfReader.readLine()) != null) {
                String[] words = tempString.split("\uF8F5",2);
                if(!words[0].contains("é"))
                    dirTree.insert(words);
            }
            bfReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bfReader != null) {
                try {
                    bfReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
        //DirWriter.writeTree(dirTree.root);
        return dirTree;
    }
}
