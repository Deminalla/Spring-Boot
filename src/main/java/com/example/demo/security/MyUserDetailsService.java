package com.example.demo.security;

import com.example.demo.business.mapper.UserEntityMapStruct;
import com.example.demo.business.repository.UserRepository;
import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserEntityMapStruct userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        if(userEntity.isEmpty()){
            throw new UsernameNotFoundException(username + " not found");
        }

        UserDto userDto = userMapper.entityToDto(userEntity.get());
        return new MyUserDetails(userDto);
    }
}
