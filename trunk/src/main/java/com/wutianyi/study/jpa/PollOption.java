package com.wutianyi.study.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "t_polloption")
public class PollOption implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2821952281261232882L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "option_id")
	private int optionId;

	@ManyToOne
	@JoinColumn(name = "topic_id", nullable = false)
	private PollTopic pollTopic;

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public PollTopic getPollTopic() {
		return pollTopic;
	}

	public void setPollTopic(PollTopic pollTopic) {
		this.pollTopic = pollTopic;
	}

}
