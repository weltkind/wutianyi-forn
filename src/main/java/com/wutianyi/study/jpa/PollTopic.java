package com.wutianyi.study.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "2")
public class PollTopic extends Topic {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3666462761032305409L;

	private boolean multiple;

	@Column(name = "max_choice",columnDefinition="")
	@Basic(fetch=FetchType.EAGER)
	private int maxChoices;

	@OneToMany(mappedBy = "pollTopic", cascade = CascadeType.ALL)
	private Set options = new HashSet();

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public int getMaxChoices() {
		return maxChoices;
	}

	public void setMaxChoices(int maxChoices) {
		this.maxChoices = maxChoices;
	}

	public Set getOptions() {
		return options;
	}

	public void setOptions(Set options) {
		this.options = options;
	}

}
