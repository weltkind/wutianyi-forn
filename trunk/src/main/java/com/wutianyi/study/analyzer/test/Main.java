package com.wutianyi.study.analyzer.test;

import java.io.IOException;
import java.io.StringReader;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

public class Main {
	public static void main(String[] args) throws IOException {
		StringReader sr = new StringReader("IK-Analyzer 是一个开源的，基于java 语言开发的轻量级的中文分词工具包。从2006 年12月推出1.0 版开始， IKAnalyzer 已经推出了3 个大版本。");
		IKSegmentation ikSegmentation = new IKSegmentation(sr, false);
		Lexeme lexeme = ikSegmentation.next();
		while(null != lexeme)
		{
			System.out.print(lexeme.getLexemeText() + " | ");
			lexeme = ikSegmentation.next();
		}
	}
}
