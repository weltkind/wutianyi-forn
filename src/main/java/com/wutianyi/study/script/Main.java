package com.wutianyi.study.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException, ScriptException
    {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try
        {
            engine.eval(new InputStreamReader(new FileInputStream(new File("phone.js"))));
            Invocable inv = (Invocable) engine;
            System.out.println(inv.invokeFunction("md5", inv.invokeFunction("md5", inv.invokeFunction("hexchar2bin", inv.invokeFunction("md5", "wuhanjie860728")).toString() + engine.get("code")) + "!JID"));
            System.out.println();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
