package com.wutianyi.study.discoverygroup.file;

import java.util.ArrayList;
import java.util.List;

public class MergeResult {

	List<ResultItem> resultItems;

	List<ResultItem> shareItems;

	List<ResultItem> selfItems;

	List<ResultItem> hisItems;

	public MergeResult() {
		resultItems = new ArrayList<ResultItem>();
		shareItems = new ArrayList<ResultItem>();
		selfItems = new ArrayList<ResultItem>();
		hisItems = new ArrayList<ResultItem>();
	}

	void addResultItems(ResultItem resultItem) {
		resultItems.add(resultItem);
		switch (resultItem.type) {
		case 1:
			shareItems.add(resultItem);
			break;
		case 2:
			selfItems.add(resultItem);
			break;
		case 3:
			hisItems.add(resultItem);
		}
	}

	public List<ResultItem> getResultItems() {
		return resultItems;
	}

	public List<ResultItem> getShareItems() {
		return shareItems;
	}

	public List<ResultItem> getSelfItems() {
		return selfItems;
	}

	public List<ResultItem> getHisItems() {
		return hisItems;
	}

}
