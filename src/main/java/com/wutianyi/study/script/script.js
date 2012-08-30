importPackage(java.awt);
importClass(java.awt.frame);
var frame = new java.awt.Frame("hello");
frame.setVisible(true);
print(frame.title);

var SwingGui = new JavaImporter(javax.swing,javax.swing.event,javax.swing.border,java.awt.event);
with(SwingGui){
	var mybutton = new JButton("test");	
	var myframe = new JFrame("test");
}
//var a = java.lang.reflect.Array.newInstance(java.lang.String.class, 5);

a[0] = "scripting is great!";
print(a.length);

var r = new java.lang.Runnable(){
	run:function(){
		print("running...\n");
	}
};
var th = new java.lang.Thread(r);
th.start();

function func()
{
	print("I ma func");
}
var th = new java.lang.Thread(func);
th.start();

var out = java.lang.System.out;
out["println(java.lang.Object)"]("hello");
