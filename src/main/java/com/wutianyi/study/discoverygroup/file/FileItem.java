package com.wutianyi.study.discoverygroup.file;

public class FileItem {
	Item head;
	Item curItem;
	Item nextItem;
	int type;
	int line = 1;

	public FileItem(int _type) {
		this.type = _type;
	}

	void addNextItem(Item item) {
		if (null == head) {
			head = item;
			curItem = head;
		} else {
			line++;
			curItem.addChild(item, type, line);
			item.addParent(curItem, type, line);
			curItem = item;
			
		}
	}

	Item getNextItem() {
		if (null == nextItem) {
			nextItem = head;
		} else {
			nextItem = nextItem.getChild(type);
		}
		return nextItem;
	}
}
