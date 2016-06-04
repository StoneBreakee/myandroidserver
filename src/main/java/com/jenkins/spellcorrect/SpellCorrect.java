package main.java.com.jenkins.spellcorrect;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class SpellCorrect
{
    // 单词库
    private final HashMap<String, Integer> nWords = new HashMap<String, Integer>();

    // 加载单词库
    public SpellCorrect(String file)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Pattern p = Pattern.compile("\\w+");
            for (String temp = ""; temp != null; temp = br.readLine())
            {
                Matcher m = p.matcher(temp);
                while (m.find())
                {
                    nWords.put((temp = m.group()), nWords.containsKey(temp)
                            ? nWords.get(temp) + 1 : 1);
                }
            }
            br.close();
        }
        catch(FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @function 得到与参数最小编辑距离为1,2的所有单词
     * @param String
     *            等待用户输入的单词
     * @return ArrayList<String> 所有与word最小编辑距离为1,2的所有单词的集合
     */
    public final HashSet<String> edits(String word)
    {
        HashSet<String> result = new HashSet<String>();
        // 根据的单词word，得到该单词最小编辑距离为1的单词集合

        // Condition:缺少一个字母
        for (int i = 0; i < word.length(); i++)
        {
            result.add(word.substring(0, i) + word.substring(i + 1));
        }

        // Condition：变换两个连续字母的顺序
        for (int i = 0; i < word.length() - 1; i++)
        {
            result.add(word.substring(0, i) + word.substring(i + 1, i + 2)
                    + word.substring(i, i + 1) + word.substring(i + 2));
        }

        // Condition：替换word中的字母
        for (int i = 0; i < word.length(); i++)
        {
            for (char c = 'a'; c <= 'z'; c++)
            {
                result.add(word.substring(0, i) + String.valueOf(c)
                        + word.substring(i + 1));
            }
        }

        // Condition：向word中添加一个字母
        for (int i = 0; i < word.length(); i++)
        {
            for (char c = 'a'; c <= 'z'; c++)
            {
                result.add(word.substring(0, i) + String.valueOf(c)
                        + word.substring(i));
            }
        }
        result.remove(word);
        return result;
    }

    public final String corect(String word)
    {
        if (nWords.containsKey(word))
        {
            return word;
        }
        HashSet<String> set = edits(word);
        HashMap<Integer, String> candidates = new HashMap<Integer, String>();

        for (String s : set)
        {
            if (nWords.containsKey(s))
            {
                candidates.put(nWords.get(s), s);
            }
        }
        if (candidates.size() > 0)
        {
            return candidates.get(Collections.max(candidates.keySet()));
        }

        for (String s : set)
        {
            for (String w : edits(s))
            {
                if (nWords.containsKey(w))
                {
                    candidates.put(nWords.get(w), w);
                }
            }
        }
        if (candidates.size() > 0)
        {
            return candidates.get(Collections.max(candidates.keySet()));
        }
        return word;
    }

    public final String corect1(String word)
    {
        String temp = "";
        int count = 0;
        if (nWords.containsKey(word) || corect(word).equals(word))
        {
            return word;
        }
        HashSet<String> set = edits(word);
        HashMap<Integer, String> candidates = new HashMap<Integer, String>();

        for (String s : set)
        {
            if (nWords.containsKey(s))
            {
                candidates.put(nWords.get(s), s);
            }
        }
        while (candidates.size() > 0 && count < 5)
        {
            temp += candidates.get(Collections.max(candidates.keySet())) + " ";
            candidates.remove(Collections.max(candidates.keySet()));
            count++;
        }
        if (count < 5)
        {
            for (String s : set)
            {
                for (String w : edits(s))
                {
                    if (nWords.containsKey(w))
                    {
                        candidates.put(nWords.get(w), w);
                    }
                }
            }
            while (candidates.size() > 0 && count < 5)
            {
                temp += candidates.get(Collections.max(candidates.keySet())) + " ";
                candidates.remove(Collections.max(candidates.keySet()));
                count++;
            }
        }
        return temp;
    }
}
