package com.wutianyi.study.discoverygroup.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.ibatis.common.resources.Resources;
import com.wutianyi.study.discoverygroup.parser.dataobject.Author;
import com.wutianyi.study.discoverygroup.parser.dataobject.BlogDict;
import com.wutianyi.study.discoverygroup.parser.dataobject.Blogger;
import com.wutianyi.study.discoverygroup.parser.dataobject.Dict;
import com.wutianyi.utils.OperatorTimes;

public class BloggerMapperService {
	private SqlSessionFactory sessionFactory;
	private String resource = "com/wutianyi/study/discoverygroup/blogger-config.xml";

	private OperatorTimes time = new OperatorTimes();

	public BloggerMapperService() throws IOException {
		sessionFactory = new SqlSessionFactoryBuilder().build(Resources
				.getResourceAsReader(resource));
	}

	// FIXME!事务性的问题需要考虑
	/**
	 * @param author
	 */
	public void insertAuthor(Author author) {
		time.start();
		SqlSession session = sessionFactory.openSession();
		try {
			session
					.insert(
							"com.wutianyi.study.discoverygroup.mapper.BloggerMapper.insertAuthor",
							author);
			List<Blogger> bloggers = author.getBloggers();

			for (Blogger blogger : bloggers) {
				blogger.setAuthorId(author.getId());
				session
						.insert(
								"com.wutianyi.study.discoverygroup.mapper.BloggerMapper.insertBlogger",
								blogger);

				List<Dict> tDicts = getDictsByWord(session, blogger.getWords()
						.keySet().toArray(new String[] {}));
				Map<String, Integer> dicts = new HashMap<String, Integer>();
				addDictToLocators(dicts, tDicts);
				insertDicts(blogger.getId(), dicts, blogger.getWords(), session);

			}
		} finally {
			session.commit();
			session.close();
		}
		time.end(author.getName());
	}

	private void insertDicts(int bloggerId, Map<String, Integer> lDicts,
			Map<String, Integer> bloggerDicts, SqlSession session) {
		List<Dict> iDicts = new ArrayList<Dict>();
		List<BlogDict> blogDicts = new ArrayList<BlogDict>();
		for (Entry<String, Integer> entry : bloggerDicts.entrySet()) {
			if (!lDicts.containsKey(entry.getKey())) {
				iDicts.add(new Dict(entry.getKey()));
			} else {
				blogDicts.add(new BlogDict(bloggerId, lDicts
						.get(entry.getKey()), entry.getValue()));
			}
		}
		for (Dict i : iDicts) {
			session
					.insert(
							"com.wutianyi.study.discoverygroup.mapper.BloggerMapper.insertDict",
							i);
			lDicts.put(i.getWord(), i.getId());
			blogDicts.add(new BlogDict(bloggerId, i.getId(), bloggerDicts.get(i
					.getWord())));
		}
		for (BlogDict blogDict : blogDicts) {
			session
					.insert(
							"com.wutianyi.study.discoverygroup.mapper.BloggerMapper.insertBloggerDict",
							blogDict);
		}
	}

	private void addDictToLocators(Map<String, Integer> dicts, List<Dict> tDicts) {
		for (Dict d : tDicts) {
			dicts.put(d.getWord(), d.getId());
		}
	}

	public List<Dict> fetchDictsByWord(String[] dicts) {
		List<Dict> rs = null;

		SqlSession session = sessionFactory.openSession();

		try {
			rs = getDictsByWord(session, dicts);
		} finally {
			session.close();
		}

		return rs;
	}

	private List<Dict> getDictsByWord(SqlSession session, String[] dicts) {
		List<Dict> rs = session
				.selectList(
						"com.wutianyi.study.discoverygroup.mapper.BloggerMapper.fecthDicts",
						dicts);

		return rs;
	}

	public static void main(String[] args) throws IOException {
		BloggerMapperService mapperService = new BloggerMapperService();
		Map<String, Integer> words = new HashMap<String, Integer>();
		words.put("中国", 1);
		List<Dict> rs = mapperService.fetchDictsByWord(words.keySet().toArray(
				new String[] {}));

		for (Dict r : rs) {
			System.out.println(r);
		}

	}
}
