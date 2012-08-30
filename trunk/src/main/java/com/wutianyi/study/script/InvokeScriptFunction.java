package com.wutianyi.study.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class InvokeScriptFunction
{
    public static void main(String[] args) throws ScriptException, NoSuchMethodException
    {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        String script = "function hello(name) {print('Hello, ' + name);}";
        
        engine.eval(script);
        
        Invocable inv = (Invocable) engine;
        
        inv.invokeFunction("hello", "Scripting!!");
    }
}
