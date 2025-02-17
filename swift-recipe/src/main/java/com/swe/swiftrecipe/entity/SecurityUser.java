/**
 * SWIFTMARKET SECURITY USER ENTITY CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class represents the Security User entity and the UserDetails info.
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
    private User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    /**
     * Retrieves the username associated with the user
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Retrieves the password associated with the user
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Retrieves the authorities granted to the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
    }

    /**
     * Determines whether the user's account is expired or not
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Determines whether the user's account is enabled or disabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Determines whether the user's account is locked or unlocked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Determines whether the user's credentials are expired or not
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}