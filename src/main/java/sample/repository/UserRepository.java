package sample.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sample.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
}
