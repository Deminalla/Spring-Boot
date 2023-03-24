package com.example.demo.security;

import com.example.demo.business.mapper.CompanyEntityMapStruct;
import com.example.demo.business.mapper.UserEntityMapStruct;
import com.example.demo.business.repository.CompanyRepository;
import com.example.demo.business.repository.UserRepository;
import com.example.demo.business.repository.model.CompanyEntity;
import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.model.CompanyDto;
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
    private final CompanyRepository companyRepository;
    private final UserEntityMapStruct userMapper;
    private final CompanyEntityMapStruct companyMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        if(userEntity.isPresent()){
            UserDto userDto = userMapper.entityToDto(userEntity.get());
            return new MyUserDetails(userDto);
        }

        try {
           long companyId = Long.parseLong(username);
            Optional<CompanyEntity> companyEntity = companyRepository.findById(companyId);
            if(companyEntity.isPresent()){
                CompanyDto companyDto = companyMapper.entityToDto(companyEntity.get());
                return new MyUserDetails(companyDto);
            }
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
        }
        throw new UsernameNotFoundException(username + " not found");
    }
}
