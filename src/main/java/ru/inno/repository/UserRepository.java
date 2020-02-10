package ru.inno.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.inno.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Stream<User> findAllByName(String name);

  List<User> findFirst5ByEmailStartsWithOrderByName(String email);

  void deleteAllByCreationDateAfter(LocalDate date);

  List<User> findAllByEmailLike(String email);

  @Query("SELECT u FROM User u WHERE u.status = 1")
  Collection<User> findAllActiveUsers();

  @Query("select u from User u where u.email like '%@gmail.com'")
  List<User> findUsersWithGmailAddress();

  @Query("SELECT u FROM User u WHERE u.name like ?1%")
  User findUserByNameLike(String name);

  @Query("SELECT u FROM User u WHERE u.name like :name%")
  User findUserByNameLikeNamedParam(@Param("name") String name);

  @Query(value = "SELECT * FROM Users u WHERE u.status = 1", nativeQuery = true)
  Collection<User> findAllActiveUsersNative();

  @Query("SELECT u FROM User u WHERE u.status = ?1")
  User findUserByStatus(Integer status);

  @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
  User findUserByStatusAndNameNamedParams(@Param("status") Integer status, @Param("name") String name);

  @Query(value = "SELECT u FROM User u")
  List<User> findAllUsers(Sort sort);

  @Query(value = "SELECT u FROM User u ORDER BY id")
  Page<User> findAllUsersWithPagination(Pageable pageable);

  @Modifying
  @Query("update User u set u.status = :status where u.name = :name")
  int updateUserSetStatusForName(@Param("status") Integer status, @Param("name") String name);

  @Query(value = "SELECT u FROM User u WHERE u.name IN :names")
  List<User> findUserByNameList(@Param("names") Collection<String> names);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("update User u set u.active = false where u.lastLoginDate < :date")
  void deactivateUsersNotLoggedInSince(@Param("date") LocalDate date);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("delete User u where u.active = false")
  int deleteDeactivatedUsers();

}
