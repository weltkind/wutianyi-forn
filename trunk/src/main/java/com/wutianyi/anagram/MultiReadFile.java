/**
 * 
 */
package com.wutianyi.anagram;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * @author wutianyi
 *
 */
public class MultiReadFile {
	
	public static void main(String[] args) throws IOException {

		FileInputStream output_1 = new FileInputStream("index");
		FileInputStream output_2 = new FileInputStream("index");
		FileChannel fc_1 = output_1.getChannel();
		FileChannel fc_2 = output_2.getChannel();
		
		ByteBuffer buffer_1 = ByteBuffer.allocate(1024);
		
		int len = 0;
		while((len = fc_1.read(buffer_1)) != -1) {
//			byte[] b = new byte[buffer_1.position() - 1];
//			buffer_1.flip();
//			buffer_1.get(b);
//			System.out.println(new String(b, "utf8"));
//			buffer_1.clear();
			System.out.println(new String(buffer_1.array(),0, buffer_1.position()));
			buffer_1.clear();
		}
		ByteBuffer buffer_2 = ByteBuffer.allocate(1024);
		while((len = fc_2.read(buffer_2)) != -1) {
			byte[] b = new byte[buffer_2.position()];
			buffer_2.flip();
			buffer_2.get(b);
			System.out.println(new String(b, "utf8"));
			buffer_2.clear();
		}
		
//		fc_2.read(buffer_2, 26);
		
		if(fc_1 == fc_2) {
			System.out.println("reference equal!");
		}
		
		if(fc_1.equals(fc_2)) {
			System.out.println("value equal!");
		}
		
		output_1.close();
		output_2.close();
	}
}
