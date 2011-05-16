package com.wutianyi.threadpattern.activeObject;

public class DisplayStringRequest extends MethodRequest{
	
	private final String string;
	
	public DisplayStringRequest(Servant servant, String string) {
		super(servant, null);
		this.string = string;
	}

	@Override
	public void execute() {
		servant.displayString(string);
	}

}
