package com.wutianyi.threadpattern.activeObject;

/**Active Object -- 接受异步消息的主动对象
 * @author hanjie.wuhj
 *
 */
public class Main {
	
	public static void main(String[] args) {
		ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
		
		new MakerClientThread("Alice", activeObject).start();
		new MakerClientThread("Bobby", activeObject).start();
		new DisplayClientThread("Chris", activeObject).start();
	}
}
