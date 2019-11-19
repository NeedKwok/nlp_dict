package Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
    private TrieNode root;
    static class TrieNode implements Serializable {
        int num;//有多少单词通过这个节点,即由根至该节点组成的字符串模式出现的次数
        Map<Character, TrieNode> sons;//所有的儿子节点
        boolean isEnd;//是不是最后一个节点
        String meaning;//解释
        boolean haveSon;//有无孩子
        TrieNode()
        {
            num = 1;
            sons = new HashMap<>();
            isEnd=false;
            haveSon=false;
        }
    }

    public Trie(){
        root = new TrieNode();
    }

    public Trie(TrieNode node){
        root = node;
    }

    public TrieNode getRoot(){
        return root;
    }

    void insert(String[] line){
        if(line == null)
            return;
        TrieNode node = root;
        char[] letters = line[0].toCharArray();
        for (char letter : letters) {
            if (!node.sons.containsKey(letter)) {
                node.haveSon = true;
                node.sons.put(letter, new TrieNode()) ;
            } else {
                node.num++;
            }
            node = node.sons.get(letter);
        }
        node.isEnd = true;
        node.meaning = line[1].replace("\uF8F5"," ");
    }

    private Map<String, String> getRule(String word){ //获取规则变形规则 仅包括动词和名词
        Map<String,String> rule = new HashMap<>();
        int len = word.length();

        if(word.endsWith("ies")){
            rule.put("*ies->*y",word.substring(0,len-3)+"y");
            rule.put("*es->*",word.substring(0,len-2));
            rule.put("*s->*",word.substring(0,len-1));
        }else if(word.endsWith("es")){
            rule.put("*es->*",word.substring(0,len-2));
            rule.put("*s->*",word.substring(0,len-1));
        }else if(word.endsWith("s")){
            rule.put("*s->*",word.substring(0,len-1));
        }

        if(word.endsWith("ying")){
            rule.put("*ying->ie*",word.substring(0,len-4)+"ie");
            rule.put("*ing->*",word.substring(0,len-3));
            rule.put("*ing->e*",word.substring(0,len-3)+"e");
            if(word.charAt(len-5) == 'y')
                rule.put("*??ing->*?",word.substring(0,len-4));
        }else  if(word.endsWith("ing")){
            rule.put("*ing->*",word.substring(0,len-3));
            rule.put("*ing->e*",word.substring(0,len-3)+"e");
            char s = word.charAt(len-4);
            if(word.charAt(len-5) == s)
                rule.put("*??ing->*?",word.substring(0,len-4));
        }

        if(word.endsWith("ied")){
            rule.put("*ied->y*",word.substring(0,len-3)+"y");
            rule.put("*ed->*",word.substring(0,len-2));
            rule.put("*ed->e*",word.substring(0,len-1));
            char s = word.charAt(len-3);
            if(word.charAt(len-4) == s)
                rule.put("*??ed->*?",word.substring(0,len-3));

        } else if(word.endsWith("ed")){
            rule.put("*ed->*",word.substring(0,len-2));
            rule.put("*ed->e*",word.substring(0,len-1));
            char s = word.charAt(len-3);
            if(word.charAt(len-4) == s)
                rule.put("*??ed->*?",word.substring(0,len-3));
        }
        return rule;
    }

    public String searchWord(String word){
        List<String> res = new ArrayList<>();
        String result = search(word);

        if(result != null){
            return result;
        }else{
            Map<String, String> rule = getRule(word);
            String notFound = "<未登录词>";
            if(rule.isEmpty()){
                return word + " " + notFound;
            }else{
                for(Map.Entry<String, String> entry : rule.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    String meaning = search(value);
                    if(meaning != null){
                        if( meaning.contains(" v") || (meaning.contains("n.")&&word.endsWith("s"))){ //是动词或者是名词的复数才正确
                            res.add("词：" + word +" "+ "规则：" + key);
                            res.add(meaning);
                        }
                    }
                }
                if(res.isEmpty()){
                    return word + " " + notFound;
                }else{
                    StringBuilder s = new StringBuilder();
                    for(String str:res){
                        s.append(str);
                        s.append("\n");
                    }
                    return s.deleteCharAt(s.length()-1).toString();
                }
            }
        }
    }

    private String search(String str){
        TrieNode node = root;
        char[] letters = str.toCharArray();
        for (char letter : letters) {
            if (!node.sons.containsKey(letter))
                return null;
            node = node.sons.get(letter);
        }
        if(!node.isEnd)
            return null;
        return str + " " + node.meaning;
    }
}
