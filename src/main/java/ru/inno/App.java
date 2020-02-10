package ru.inno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.inno.model.Post;
import ru.inno.model.User;
import ru.inno.service.UserOperationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public final class App {
  private static final Logger logger = LoggerFactory.getLogger(App.class);

  private App() {
  }

  public static void main(String[] args) {
    ApplicationContext context =
        new AnnotationConfigApplicationContext(PersistenceConfig.class);
    final UserOperationService userOperationService = context.getBean(UserOperationService.class);
    userOperationService.addUsers(generateUsers());

    long countUsers = userOperationService.getCountUsers();
    List<User> allUserBy = userOperationService.findAllUserBy(Collections.emptyList());

    logger.info("==============={}================", countUsers);
    logger.info("{}", allUserBy);
    logger.info("===============================");

    userOperationService.deactivateUsers(LocalDate.now());

    countUsers = userOperationService.getCountUsers();
    allUserBy = userOperationService.findAllUserBy(Collections.emptyList());
    logger.info("==============={}================", countUsers);
    logger.info("{}", allUserBy);
    logger.info("===============================");
  }


  private static List<User> generateUsers() {
    List<User> lst = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      final LocalDate randomDate = createRandomDate(2015, 2025);
      final User user = new User("User" + i, randomDate, "im" + i + "@email.ru", i % 2);
      user.setLastLoginDate(randomDate);
      Post post = new Post();
      post.setTitle("post"+i);
      post.setPostDate(randomDate);
      user.setPostList(Collections.singletonList(post));
      lst.add(user);
    }
    return lst;
  }

  private static int createRandomIntBetween(int start, int end) {
    return start + (int) Math.round(Math.random() * (end - start));
  }

  private static LocalDate createRandomDate(int startYear, int endYear) {
    int day = createRandomIntBetween(1, 28);
    int month = createRandomIntBetween(1, 12);
    int year = createRandomIntBetween(startYear, endYear);
    return LocalDate.of(year, month, day);
  }
}
