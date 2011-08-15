/**
 * 
 */

package com.wutianyi.anagram;

/**
 * 简单的记录该变位词在文件中的位置
 * @author wutianyi
 * 
 */
public class Index {

	int	start;
	int	end;

	Index(int start, int end) {

		this.start = start;
		this.end = end;
	}

	public int getStart() {

		return start;
	}

	public void setStart(int start) {

		this.start = start;
	}

	public int getEnd() {

		return end;
	}

	public void setEnd(int end) {

		this.end = end;
	}

}
