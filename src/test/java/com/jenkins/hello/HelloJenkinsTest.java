package test.java.com.jenkins.hello;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.com.jenkins.hello.HelloJenkins;

public class HelloJenkinsTest
{

    @Test
    public void testSayHello()
    {
        HelloJenkins hello = new HelloJenkins();
        assertEquals(hello.sayHello() , "Hello,Jenkins,lyj");
    }

}
