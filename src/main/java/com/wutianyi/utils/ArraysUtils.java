package com.wutianyi.utils;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

public class ArraysUtils {
	@Test
	public void copyOf() {
		Object[] element = new Object[] { "string", "10" };
		String[] str = Arrays.copyOf(element, element.length, String[].class);
		for (String s : str) {
			System.out.println(s);
		}
	}

	@Test
	public void copyObject() {
		CopyA[] copys = new CopyA[]{new CopyA(1, "copy_1")};
		CopyA[] copyBy = Arrays.copyOf(copys, copys.length);
		copyBy[0].setI(2);
		copyBy[0].setStr("copy_change");
		for(CopyA c : copys) {
			System.out.println(c);
		}
	}
	
	/**
	 * 类库类插入算法
	 */
	@Test
	public void insertAgri() {
		String[] strs = new String[3];
		strs[0] = "test_1";
		strs[1] = "test_3";
		System.arraycopy(strs, 1, strs, 2, 1);
		strs[1] = "test_2";
		System.out.println(StringUtils.join(strs, ','));
	}

	@Test
	public void createArray() {
		Object[] objs = (Object[]) Array.newInstance(String[].class, 11);
		System.out.println(objs.length);
	}
	
	private class CopyA {
		private int i = 0;
		private String str;

		public CopyA(int _i, String _str) {
			this.i = _i;
			this.str = _str;
		}

		public String toString() {

			return i + " : " + str;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}

	}
}
