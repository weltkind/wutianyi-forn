package com.wutianyi.study.spring.trademanager;

import com.wutianyi.study.ibatis.dao.Test1DAO;

public class DefaultFooService implements FooService {

	private Test1DAO dao;

	public Foo getFoo(String fooName) {
		System.out.println("Begin");
		// throw new UnsupportedOperationException();
		return null;
	}

	public Foo getFoo(String fooName, String barName) {
		throw new UnsupportedOperationException();
	}

	public void insertFoo(Foo foo) {
		dao.insert("wutianyi");
//		throw new UnsupportedOperationException();

	}

	public void updateFoo(Foo foo) {
		throw new UnsupportedOperationException();

	}

	public Test1DAO getDao() {
		return dao;
	}

	public void setDao(Test1DAO dao) {
		this.dao = dao;
	}

}
