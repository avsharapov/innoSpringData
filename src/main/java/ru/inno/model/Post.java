package ru.inno.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;
import java.util.StringJoiner;


@Entity
@Table(name = "post")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  Integer postId;

  @Column(name = "title")
  String title;

  @Column(name = "post_date")
  LocalDate postDate;


  public Integer getPostId() {
    return postId;
  }

  public void setPostId(Integer postId) {
    this.postId = postId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public LocalDate getPostDate() {
    return postDate;
  }

  public void setPostDate(LocalDate postDate) {
    this.postDate = postDate;
  }


  @Override public String toString() {
    return new StringJoiner(", ", Post.class.getSimpleName() + "[", "]")
        .add("postId=" + postId)
        .add("title='" + title + "'")
        .add("postDate=" + postDate)
        .toString();
  }
}
