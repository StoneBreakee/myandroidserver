package test.java.com.jenkins.spellcorrect;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.com.jenkins.spellcorrect.SpellCorrect;

public class SpellCorrectTest
{

    static int min(int x, int y, int z)
    {
        if (x < y && x < z)
            return x;
        if (y < x && y < z)
            return y;
        else
            return z;
    }

    static int editDist(String str1, String str2, int m, int n)
    {
        // if first string is empty,the only option is insert all characters of
        // second string to the first
        if(m == 0)
            return n;
        
        //if the second string is empty,the only option is insert all characters of
        //first string to the second
        if(n == 0)
            return m;
        
        //if the last characters of two strings are same,nothing much to do.
        //Ignore last characters and get count for remaining strings
        if(str1.charAt(m-1) == str2.charAt(n-1))
            return editDist(str1, str2, m-1, n-1);
        
        //if the last characters of two strings are not same,consider all three
        //oprations on last character of first string ,recursively compute minimum
        //cost for all thress oprations and take minimum of three values
        return 1 + min(editDist(str1,str2,m,n-1)
                ,editDist(str1,str2,m-1,n)
                ,editDist(str1,str2,m-1,n-1));
    }
    
    @Test
    public void testEdits()
    {
        SpellCorrect spell = new SpellCorrect("testbig.txt");
        String testStr = "words";
        boolean flag = true;
        for(String s:spell.edits(testStr)){
            int i = editDist(testStr,s,testStr.length(),s.length());
            if(i != 1 && i != 2)
                flag = false;
                break;
        }
        assertTrue(flag);
    }

    @Test
    public void testCorect()
    {
        SpellCorrect spell = new SpellCorrect("testbig.txt");
        assertTrue("Gutenberg".equals(spell.corect("Gutenberg")));
        assertTrue("Gutenberg".equals(spell.corect("Gutenbeg")));
    }

}
