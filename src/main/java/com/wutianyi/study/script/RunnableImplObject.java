package com.wutianyi.study.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RunnableImplObject
{
    public static void main(String[] args) throws ScriptException
    {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        
        String script = "var obj= new Object();obj.run = function(name){println('run method called');}";
        engine.eval(script);
        
        Object obj = engine.get("obj");
        
        Invocable inv = (Invocable) engine;
        
        Runnable r = inv.getInterface(obj, Runnable.class);
        Thread th = new Thread(r);
        th.start();
    }
}
