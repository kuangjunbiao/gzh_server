package com.gaoan.forever.model.weixin;

import java.util.List;

public class NewsDO extends BaseMessageDO {

	private String ArticleCount;
	private List<PictureDO> Articles;

	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}

	public List<PictureDO> getArticles() {
		return Articles;
	}

	public void setArticles(List<PictureDO> articles) {
		Articles = articles;
	}

}
