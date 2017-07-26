package it.keybiz.webapp.foundation.security.users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.keybiz.webapp.foundation.model.Role;
import it.keybiz.webapp.foundation.model.User;
import it.keybiz.webapp.foundation.persistence.users.UsersRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UsersRepository usersRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User u = usersRepo.findByUserName(username);
		
		if(u == null){
			throw new UsernameNotFoundException("User not found in UsersRepository");
		}
		
		Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(u.getRoles());
		
		return new org.springframework.security.core.userdetails.
			User(u.getUserName(), u.getPassword(), grantedAuthorities);
	
	}

	private Collection<GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
		Collection<GrantedAuthority> authorities = 
			roles.stream().
				map(Role::getRoleName).
					map(SimpleGrantedAuthority::new).
						collect(Collectors.toList());
		
		return authorities;
	}

}
