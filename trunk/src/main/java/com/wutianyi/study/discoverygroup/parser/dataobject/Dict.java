package com.wutianyi.study.discoverygroup.parser.dataobject;

public class Dict {
	int id;
	String word;

	public Dict() {

	}

	public Dict(String _word) {
		this.word = _word;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String toString() {
		return "id: " + id + "\tword: " + word;
	}

}
