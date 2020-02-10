package ru.inno.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.repository.UserRepository;
import ru.inno.repository.UserRepositoryCustom;
import ru.inno.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserOperationServiceImpl implements UserOperationService {
  private final UserRepository userRepository;
  private final UserRepositoryCustom userRepositoryCustom;

  @Autowired
  public UserOperationServiceImpl(UserRepository userRepository, UserRepositoryCustom userRepositoryCustom) {
    this.userRepository = userRepository;
    this.userRepositoryCustom = userRepositoryCustom;
  }

  @Override public void addUsers(List<User> users) {
    for (User user : users) {
      userRepository.save(user);
    }
  }

  @Override public int deactivateUsers(LocalDate date) {
    userRepository.deactivateUsersNotLoggedInSince(date);
    return userRepository.deleteDeactivatedUsers();
  }

  @Override public List<User> findAllUserBy(Collection<Predicate<User>> predicates) {
    return userRepositoryCustom.findAllUsersByPredicates(predicates);
  }

  @Override public long getCountUsers() {
    return userRepository.count();
  }
}
