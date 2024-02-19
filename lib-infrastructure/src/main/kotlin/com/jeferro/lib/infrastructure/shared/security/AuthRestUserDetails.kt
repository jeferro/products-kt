package com.jeferro.lib.infrastructure.shared.security

import com.jeferro.lib.domain.models.auth.UserId
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class AuthRestUserDetails(
    val userId: UserId,
    val roles: List<String>
) : UserDetails {

    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return roles.map { role -> SimpleGrantedAuthority(role) }
            .toList()
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String? {
        return null
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
