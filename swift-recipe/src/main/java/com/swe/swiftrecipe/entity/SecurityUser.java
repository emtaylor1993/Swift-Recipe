/**
 * SWIFTMARKET SECURITY USER ENTITY CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents a Secure User in the system. This class implements Spring 
 *    Security's {@link UserDetails} interface to allow for user authentication and authorization.
 *    It wraps an instance of the User entity and provides necessary methods for Spring
 *    Security to determine the user's authentication details.
 * 
 * @packages
 *    Java Utilities (Collection, Collections)
 *    Spring Framework Security Core (GrantedAuthority)
 *    Spring Framework Security Core Authority (SimpleGrantedAuthority)
 *    Spring Framework Security Core User Details (UserDetails)
 */

package com.swe.swiftrecipe.entity;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails {

    /**
     * Represents the wrapped User entity containing user information.
     */
    private final User user;

    /**
     * Constructs a Secure User with the provided User entity.
     * @param user (User)
     */
    public SecurityUser(User user) {
        this.user = user;
    }

    /**
     * Retrieves the username associated with the User entity.
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Retrieves the password associated with the User entity.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Retrieves the authorities granted to the User entity. In this implementation, all
     * Users are granted the "ADMIN" role.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
    }

    /**
     * Determines whether the User's account has expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Determines whether the User's account has been disabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Determines whether the User's account has been locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Determines whether the User's credentials are expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}