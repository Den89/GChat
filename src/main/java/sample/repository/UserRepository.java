package sample.repository;

import org.springframework.data.repository.CrudRepository;
import sample.model.User;

public interface UserRepository extends CrudRepository<User, String>{
}
