package it.keybiz.webapp.foundation.persistence.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import it.keybiz.webapp.foundation.model.User;

public interface UsersRepository extends MongoRepository<User, String>{
	
	public User findByUserName(@Param(value="userName")String userName);
	
	public User findByEmail(@Param(value="email")String email);
}
