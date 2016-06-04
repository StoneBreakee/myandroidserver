package main.java.com.jenkins.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.jenkins.spellcorrect.SpellCorrect;

public class SpellServlet extends HttpServlet
{
    public SpellServlet() {
        super();
    }

    public void destroy()
    {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            String keyWord = request.getParameter("key");
            String str = this.getServletContext().getRealPath("big.txt");
            SpellCorrect spell = new SpellCorrect(str);
            String correctWord = spell.corect1(keyWord);
            PrintWriter out = response.getWriter();
            out.print(correctWord);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        this.doGet(request, response);
    }

    public void init()
    {
    }
}
