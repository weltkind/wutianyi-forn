package com.wutianyi.study.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "t_topic")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "topic_type", discriminatorType = DiscriminatorType.INTEGER, length = 1)
@DiscriminatorValue(value="1")
public class Topic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6706039748504622904L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "topic_id")
	private int topicId;

	@Column(name = "topic_title", length = 100)
	private String topicTitle;

	@Column(name = "topic_time")
	@Temporal(TemporalType.DATE)
	private Date topicTime;

	@Column(name = "topic_views")
	private int topicViews;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public Date getTopicTime() {
		return topicTime;
	}

	public void setTopicTime(Date topicTime) {
		this.topicTime = topicTime;
	}

	public int getTopicViews() {
		return topicViews;
	}

	public void setTopicViews(int topicViews) {
		this.topicViews = topicViews;
	}

}
