package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirTree {
    /**
     * 字典树类，包括建立字典树，查询单词两个方法
     * 字典以ASCII序排列，但是有一个单词含有字母 "é" ，不放入字典树中即可
     */
    /*private static char[] charTable = {'!', '\"', '\'','(',')','-','.',
            '0','1','2','3','4','5','6','7','8','9','?'};*/
    private TreeNode root;
    private int ct = 1;

    DirTree(){
        root = new TreeNode();
    }
    public DirTree(TreeNode treeNode){//为反序列化准备的构造方法，但没用了
        root = treeNode;
    }

    void insert(String[] line){
        if(line == null)
            return;
        TreeNode node = root;
        char[] letters = line[0].toCharArray();
        int len = letters.length;
        for (char letter : letters) {
            int pos = letter - '!';
            if (node.son[pos] == null) {
                node.haveSon = true;
                node.son[pos] = new TreeNode();
                node.son[pos].val = letter;
            } else {
                node.num++;
            }
            node = node.son[pos];
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
        if(str.equals("moiré")){
            return "moiré none. 波纹";
        }
        TreeNode node = root;
        char[] letters = str.toCharArray();
        int len = letters.length;
        for (char letter : letters) {
            int pos = letter - '!';
            if (node.son[pos] == null)
                return null;
            node = node.son[pos];
        }
        if(!node.isEnd)
            return null;
        return str + " " + node.meaning;
    }

    private void traversal(TreeNode node, int ct){
        if(node != null){
            System.out.println(node.val+ " " + ct);
            if(node.haveSon){
                for(TreeNode i:node.son){
                    traversal(i,ct+1);
                }
            }
        }
    }

}
