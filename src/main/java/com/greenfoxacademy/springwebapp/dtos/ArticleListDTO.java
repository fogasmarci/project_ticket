package com.greenfoxacademy.springwebapp.dtos;

import com.greenfoxacademy.springwebapp.models.Article;

import java.util.List;

public class ArticleListDTO {

  private List<Article> articles;

  public List<Article> getArticles() {
    return articles;
  }

  public void setArticles(List<Article> articles) {
    this.articles = articles;
  }
}
