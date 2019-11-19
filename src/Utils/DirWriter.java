package Utils;

import java.io.*;

public class DirWriter {
    /**
     * 处理文件的写操作
     * 持久化字典树，但比直接建树更慢，就不适用了（也许是因为数据结构不好）
     */

    public static final String trieFile = "dic_tree.out" ;

    static void writeTrie(Trie.TrieNode root){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(trieFile));
            oos.writeObject(root);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Trie.TrieNode readTrie(){
        Trie.TrieNode root = null;
        ObjectInputStream oin ;
        try {
            oin = new ObjectInputStream(new FileInputStream(trieFile));
            root =(Trie.TrieNode) oin.readObject();
        }  catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return root;
    }
    /*public static void  writeOtherWords(String[] words)  { //写带有"é"字符的单词
        File file = new File(otherWordsFileName);
        BufferedWriter bfWriter;
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            bfWriter = new BufferedWriter(new OutputStreamWriter(fos));

            bfWriter.write(words[0]+ " " +words[1]);
            bfWriter.newLine();

           bfWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
