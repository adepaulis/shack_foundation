package it.keybiz.webapp.foundation.persistence.users;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.keybiz.webapp.foundation.model.User;

public interface UsersRepository extends MongoRepository<User, String>{
	
	public User findByUserName(String userName);
	public User findByEmail(String email);
}
