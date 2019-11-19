package Solution;

import Utils.DirReader;
import Utils.DirTree;

import java.util.Scanner;

public class Main {
    /**
     *  启动类，仅包含main函数
     */

    public static void main(String[] args){
        String str;
        DirTree dirTree;
        //File testFile = new File(treeFile);
//        if(testFile .exists()) { //判断之前是否写过文件
//            dirTree = new DirTree(DirWriter.readTree());
//            System.out.println("1");
//        }else{
            dirTree = DirReader.readByLine();
            //       System.out.println("2");
 //       }
        Scanner sc = new Scanner(System.in);
        String[] words;
        while(true) {
            System.out.println("请输入单词（句子用空格分隔,回车确定,-exit退出）：");
            str = sc.nextLine();
            if(str.equals("-exit"))
                break;
            words = str.split(" ");
            for (String word : words) {
                System.out.println(dirTree.searchWord(word));
            }
        }
    }
}
