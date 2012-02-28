package com.wutianyi.study.discoverygroup.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Item {
	String md5;
	String value;

	List<ItemRelationship> parents = new ArrayList<ItemRelationship>();

	List<ItemRelationship> children = new ArrayList<ItemRelationship>();

	public Item() {

	}

	public Item(String _md5, String _value) {
		this.value = _value;
		this.md5 = _md5;
	}

	void addParent(Item parent, int type, int line) {
		parents.add(new ItemRelationship(parent, this, line - 1, type));
	}

	void addChild(Item child, int type, int line) {
		children.add(new ItemRelationship(this, child, line, type));

	}

	void removeParent(int type) {
		for (Iterator<ItemRelationship> itr = parents.iterator(); itr.hasNext();) {
			ItemRelationship rs = itr.next();
			if (type == rs.type) {
				itr.remove();
				return;
			}
		}
	}

	Item getParentHeader(int type) {
		Item p = getParent(type);
		Item nP = null;
		while (p != null && p.getChildCount() == 1) {
			nP = p;
			p = p.getParent(type);
		}
		if(null != p && p.getChildCount() > 1) {
			return null;
		}
		return nP;
	}

	int getParentCount() {
		return parents.size();
	}

	int getChildCount() {
		return children.size();
	}

	int getLine(int type) {
		for (ItemRelationship rs : children) {
			if (rs.type == type) {
				return rs.line -1;
			}
		}
		for (ItemRelationship rs : parents) {
			if (rs.type == type) {
				return rs.line + 1;
			}
		}
		return 1;
	}

	Item getParent(int type) {
		for (ItemRelationship rs : parents) {
			if (rs.type == type) {
				return rs.head;
			}
		}
		return null;
	}

	Item getChild(int type) {
		for (ItemRelationship rs : children) {
			if (rs.type == type) {
				return rs.end;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Item i1 = new Item();
		Item i2 = i1;
		if (i1 == i2) {
			System.out.println("true");
		}

	}
}
