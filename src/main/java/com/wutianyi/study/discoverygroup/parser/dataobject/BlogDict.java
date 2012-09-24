package com.wutianyi.study.discoverygroup.parser.dataobject;

public class BlogDict {
	int id;
	int blogerId;
	int dictId;
	int count;

	public BlogDict() {

	}

	public BlogDict(int _bloggerId, int _dictId, int _count) {
		this.blogerId = _bloggerId;
		this.dictId = _dictId;
		this.count = _count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBlogerId() {
		return blogerId;
	}

	public void setBlogerId(int blogerId) {
		this.blogerId = blogerId;
	}

	public int getDictId() {
		return dictId;
	}

	public void setDictId(int dictId) {
		this.dictId = dictId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}