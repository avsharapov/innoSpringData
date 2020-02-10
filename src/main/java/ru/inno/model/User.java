package ru.inno.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private LocalDate creationDate;
  private LocalDate lastLoginDate;
  private boolean active;
  private int age;
  @Column(unique = true, nullable = false)
  private String email;
  private Integer status;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> postList;

  public User() {
    super();
  }

  public User(String name, LocalDate creationDate, String email, Integer status) {
    this.name = name;
    this.creationDate = creationDate;
    this.email = email;
    this.status = status;
    this.active = true;
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public int getAge() {
    return age;
  }

  public void setAge(final int age) {
    this.age = age;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public List<Post> getPostList() {
    return postList;
  }

  public void setPostList(List<Post> postList) {
    this.postList = postList;
  }

  @Override public String toString() {
    return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("name='" + name + "'")
        .add("creationDate=" + creationDate)
        .add("lastLoginDate=" + lastLoginDate)
        .add("active=" + active)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id == user.id &&
        age == user.age &&
        Objects.equals(name, user.name) &&
        Objects.equals(creationDate, user.creationDate) &&
        Objects.equals(email, user.email) &&
        Objects.equals(status, user.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, creationDate, age, email, status);
  }

  public LocalDate getLastLoginDate() {
    return lastLoginDate;
  }

  public void setLastLoginDate(LocalDate lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

}