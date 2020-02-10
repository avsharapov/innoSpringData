package ru.inno.service;

import ru.inno.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface UserOperationService {

    void addUsers(List<User> users);

    int deactivateUsers(LocalDate date);

    List<User> findAllUserBy(Collection<Predicate<User>> predicates);

  long getCountUsers();
}
