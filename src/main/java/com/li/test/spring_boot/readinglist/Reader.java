package com.li.test.spring_boot.readinglist;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;
@Entity
public class Reader implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    private String username;
    private String password;
    private String fullname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    // UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //授予READER角色
        return Arrays.asList(new SimpleGrantedAuthority("READER"));
    }
    @Override
    public boolean isAccountNonExpired() {
        //不过期
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        //不锁定
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        //不撤销
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
