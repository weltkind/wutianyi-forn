package com.wutianyi.study.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class InvokeScriptMethod
{
    public static void main(String[] args) throws ScriptException, NoSuchMethodException
    {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        String script = "var obj = new Object();obj.hello = function(name){print('Hello, ' + name);}";
        engine.eval(script);
        
        Invocable inv = (Invocable) engine;
        Object obj = engine.get("obj");
        
        inv.invokeMethod(obj, "hello", "Script Method !!");
    }
}
