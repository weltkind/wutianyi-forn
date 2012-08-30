package com.wutianyi.study.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RunnableImpl
{
    public static void main(String[] args) throws ScriptException
    {
        ScriptEngineManager manager = new ScriptEngineManager();

        ScriptEngine engine = manager.getEngineByName("JavaScript");
        String script = "function run(){println('run called');}";
        engine.eval(script);

        Invocable inv = (Invocable) engine;

        Runnable r = inv.getInterface(Runnable.class);
        
        Thread thread = new Thread(r);
        thread.start();
    }
}
