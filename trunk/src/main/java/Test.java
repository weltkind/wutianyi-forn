import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class Test {
	enum t {
		One{
			public void test() {
				System.out.println("out");
			}
		};
	}
	
	private static void deleteFile(File f) {
		File[] files = f.listFiles();

		for (File file : files) {
			if (file.getName().equals(".svn")) {
				try {
					FileUtils.deleteDirectory(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (file.isDirectory()) {
				deleteFile(file);
			}
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
//	    System.out.println("yesy");
//	    String test = "\u6E29\u99A8\u63D0\u9192\uFF1A<br/>\u60A8\u5F53\u524D\u7684\u8054\u7F51\u65B9\u5F0F\u548C\u7EC8\u7AEF\u6682";
//	    System.out.println(new String(test.getBytes("utf-8")));;
//	    
//	    long l = 192;
//	    System.out.println(l >> 24 & 0xFF);
//	    System.out.println((l & 0xFFFFFF) >> 16 & 0xFF);
//	    System.out.println((l & 0xFFFF) >> 8 & 0xFF);
//	    System.out.println(l & 0xFF);
	    String url = "http://qudao.ebinf.com/qshop_admin/upload/20110623/1308731405856.jpg";
	    
	    String fileName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
	    System.out.println(fileName);
	    long start = System.currentTimeMillis();
	    for(int i = 0; i < 100; i ++) {
	        UUID id = UUID.randomUUID();
	        System.out.println(Long.toHexString(id.getMostSignificantBits() + id.getLeastSignificantBits()));
	    }
	    long end = System.currentTimeMillis();
	    System.out.println(end - start);
	}
	
}
