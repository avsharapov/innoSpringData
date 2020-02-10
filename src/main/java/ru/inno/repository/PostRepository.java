package ru.inno.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.inno.model.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

}
