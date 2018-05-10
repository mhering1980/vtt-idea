package com.blackhawk.vtt2.security;

import com.blackhawk.vtt2.dao.VolunteerCredentialRepository;
import com.blackhawk.vtt2.model.Volunteer;
import com.blackhawk.vtt2.model.VolunteerCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final VolunteerCredentialRepository volunteerCredentialRepository;

    @Autowired
    public UserDetailsServiceImpl(VolunteerCredentialRepository volunteerCredentialRepository) {
        this.volunteerCredentialRepository = volunteerCredentialRepository;
    }
//can only return getAuthorities, getPassword, getUsername, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        VolunteerCredential vc = volunteerCredentialRepository.findByUsername(username);
        if (vc == null) {
            throw new UsernameNotFoundException(username);
        }
        VttUser vttUser = new VttUser(vc.getUsername(), vc.getPassword(), emptyList());
        vttUser.setVolunteer(vc.getVolunteer());
        return vttUser;
    }
}
