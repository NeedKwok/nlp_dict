package Utils;

import java.io.Serializable;

class TreeNode implements Serializable {
    private static int SIZE = 'z' - '!' + 1;
    int num;//有多少单词通过这个节点,即由根至该节点组成的字符串模式出现的次数
     TreeNode[]  son;//所有的儿子节点
     boolean isEnd;//是不是最后一个节点
    char val;//节点的值
    //public String partOfSpeech;//词性
    String meaning;//解释
    boolean haveSon;//有无孩子
    TreeNode()
    {
        num=1;
        son=new TreeNode[SIZE];
        isEnd=false;
        haveSon=false;
    }
}