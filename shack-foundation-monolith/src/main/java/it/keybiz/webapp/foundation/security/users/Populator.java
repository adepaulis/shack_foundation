package it.keybiz.webapp.foundation.security.users;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.keybiz.webapp.foundation.model.Role;
import it.keybiz.webapp.foundation.model.User;
import it.keybiz.webapp.foundation.persistence.users.UsersRepository;

@Component
public class Populator implements ApplicationListener<ApplicationReadyEvent> {

	public Populator(){
		System.out.println("Populator ctor");
	}
	
    @Autowired
    private UsersRepository userRepository;

    
    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
    	System.out.println("onApplicationEvent");
    	User admin = userRepository.findByUserName("bingo");
    	
    	if(admin == null){
	    	
	        final Role adminRole = new Role("ROLE_ADMIN");
	        
	        final User user = new User();
	        user.setUserName("bingo");
	        user.setPassword("bongo");
	        user.setEmail("bingo@bongo.com");
	        user.setRoles(Arrays.asList(adminRole));
	        userRepository.save(user);
    	}

    }
}
