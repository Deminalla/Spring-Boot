package com.example.demo.security;

import com.example.demo.model.CompanyDto;
import com.example.demo.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class MyUserDetails implements UserDetails {
    private String username, password;
//    private boolean active;
    private List<GrantedAuthority> authorityList;

    public MyUserDetails (UserDto userDto){
        this.username = userDto.getUsername();
        this.password = new BCryptPasswordEncoder().encode(userDto.getPassword());
        this.authorityList = Arrays.asList(new SimpleGrantedAuthority(("ROLE_USER")));
    }

    public MyUserDetails (CompanyDto companyDto){
        this.username = String.valueOf(companyDto.getId());
        this.password = new BCryptPasswordEncoder().encode(companyDto.getPassword());
        this.authorityList = Arrays.asList(new SimpleGrantedAuthority(("ROLE_COMPANY"))); //since an account can have more than 1 role
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
