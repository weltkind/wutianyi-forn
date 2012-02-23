package com.wutianyi.study.discoverygroup.parser.dataobject;

import java.util.Map;

public class Blogger {

	String title;
	String url;
	String category;
	String description;

	Map<String, Integer> words;

	public Blogger(String _title, String _url, String _category,
			String _description, Map<String, Integer> _words) {
		this.title = _title;
		this.url = _url;
		this.category = _category;
		this.description = _description;
		this.words = _words;
	}

	@Override
	public String toString() {
		return "Blogger title: " + title + "\turl: " + url + "\tdescription: "
				+ description + "\tcategory: " + category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, Integer> getWords() {
		return words;
	}

	public void setWords(Map<String, Integer> words) {
		this.words = words;
	}

}
