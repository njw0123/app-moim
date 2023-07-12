package org.edupoll.securty.support;

import org.edupoll.model.entity.User;
import org.edupoll.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class AccountManager implements UserDetailsService {
	UserRepository userRepository;
	
	public AccountManager(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));
		return new Account(user);
	}

}
