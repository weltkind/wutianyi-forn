package com.wutianyi.study.script;

import java.io.File;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptVars
{
    public static void main(String[] args) throws ScriptException
    {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        
        File f = new File("test.txt");
        engine.put("file", f);
        engine.eval("print(file.getAbsolutePath())");
    }
}
