package com.wutianyi.study.encoding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.mozilla.universalchardet.UniversalDetector;

public class Main {
	public static void main(String[] args) throws IOException {
		UniversalDetector detector = new UniversalDetector(null);
		//
		// File file = new
		// File(Main.class.getResource("/com/wutianyi/study/encoding").getFile());
		// File[] files = file.listFiles();
		// for(File f : files)
		// {
		// System.out.println(f.getName());
		// byte[] datas = FileUtils.readFileToByteArray(f);
		// detector.handleData(datas, 0, datas.length);
		// detector.dataEnd();
		// System.out.println(detector.getDetectedCharset());
		// detector.reset();
		// }
		byte[] data = FileUtils
				.readFileToByteArray(new File(Main.class.getResource(
						"/com/wutianyi/study/encoding/gbk.txt").getFile()));

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(new File("out.txt")), "GB18030"));
		System.out.println(data.length);
		// detector.handleData(data, 0, data.length);
		// detector.dataEnd();
		// System.out.println(detector.getDetectedCharset());
		// detector.reset();
		String str = new String(data, "GB18030");
		System.out.println(str.getBytes("GB18030").length);
		System.out.print("中文");
		for (int i = 0; i < str.length(); i++) {
			System.out.println(str.charAt(i));
		}
		pw.println("中文");
		pw.println(str);
		pw.close();
		System.out.println();
		System.out.println();

		Charset charset = Charset.forName("utf-8");
		CharBuffer charBuffer = CharBuffer.allocate(1);
		charBuffer.put('囧');
		charBuffer.flip();
		ByteBuffer bytebuffer = charset.encode(charBuffer);
		byte[] ds = new byte[bytebuffer.limit()];
		bytebuffer.get(ds);
		for (int i = 0; i < ds.length; i++) {
			System.out.print(Integer.toHexString(ds[i] & 0xff));
		}
		System.out.println();
		Charset gbcharset = Charset.forName("GB18030");
		charBuffer.flip();
		ByteBuffer gbbytebuffer = gbcharset.encode(charBuffer);
		ds = new byte[gbbytebuffer.limit()];
		gbbytebuffer.get(ds);
		for (int i = 0; i < ds.length; i++) {
			System.out.print(Integer.toHexString(ds[i] & 0xff));
		}
		byte[] zh = new byte[] {  (byte) 0x81, (byte) 0x39,(byte) 0xef,0x33 };
		System.out.println();
		System.out.println(new String(zh, "GB18030"));
	}
}
