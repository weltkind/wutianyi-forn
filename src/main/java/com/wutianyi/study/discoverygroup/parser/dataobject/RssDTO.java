package com.wutianyi.study.discoverygroup.parser.dataobject;

import java.util.List;

public class RssDTO {

	Author author;
	List<Blogger> bloggers;

	public RssDTO() {

	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<Blogger> getBloggers() {
		return bloggers;
	}

	public void setBloggers(List<Blogger> bloggers) {
		this.bloggers = bloggers;
	}

}
