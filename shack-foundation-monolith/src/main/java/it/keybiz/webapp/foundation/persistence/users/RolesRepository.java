package it.keybiz.webapp.foundation.persistence.users;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.keybiz.webapp.foundation.model.Role;

public interface RolesRepository extends MongoRepository<Role, String>{
	Role findByRoleName(String roleName);
}
