package Solution;

import Utils.DirReader;
import Utils.Trie;

import java.util.Scanner;

public class Main {
    /**
     *  启动类，仅包含main函数
     */

    public static void main(String[] args){
        String str;
        Trie trie;
        /**
         * 由于字典较小，持久化字典比重建字典慢很多，所以下面的代码弃用了
         */
//        File testFile = new File(trieFile);
//        if(testFile .exists()) { //判断之前是否写过文件
//            trie = new Trie(DirWriter.readTrie());
//            //System.out.println("1");
//        }else{
//            trie = DirReader.readByLine();
//            //System.out.println("2");
//        }
        trie = DirReader.readByLine();
        Scanner sc = new Scanner(System.in);
        String[] words;
        while(true) {
            System.out.println("请输入单词（句子用空格分隔,回车确定,-exit退出）：");
            str = sc.nextLine();
            if(str.equals("-exit"))
                break;
            words = str.split(" ");
            for (String word : words) {
                System.out.println(trie.searchWord(word));
            }
        }
    }
}
