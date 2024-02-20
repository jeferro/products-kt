package com.jeferro.lib.infrastructure.shared.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.jeferro.lib.domain.exceptions.UnauthorizedException
import com.jeferro.lib.domain.models.auth.Auth
import com.jeferro.lib.domain.models.auth.UserId
import com.jeferro.lib.infrastructure.shared.security.models.AuthRestUserDetails
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class JwtEncoder(
    private val jwtProperties: JwtProperties
) {
    companion object {
        const val BEARER_PREFIX = "Bearer "

        const val ROLES_CLAIM = "roles"
    }

    private val hmac512 = Algorithm.HMAC512(jwtProperties.password)

    private val jwtVerifier = JWT.require(hmac512).build()

    fun encode(auth: Auth): String {
        val issuedAt = Instant.now()
        val expiresAt = issuedAt.plusMillis(jwtProperties.durationInMillis)
        val subject = auth.userId?.value
            ?: throw UnauthorizedException.create()

        val token = JWT.create()
            .withIssuer(jwtProperties.issuer)
            .withIssuedAt(issuedAt)
            .withExpiresAt(expiresAt)
            .withSubject(subject)
            .withArrayClaim(ROLES_CLAIM, auth.roles.toTypedArray())
            .sign(hmac512)

        return BEARER_PREFIX + token
    }

    fun decode(request: HttpServletRequest): AuthRestUserDetails? {
        try {
            val header = request.getHeader(AUTHORIZATION) ?: return null

            if (!header.startsWith(BEARER_PREFIX)) {
                return null
            }

            val token = header.substring(BEARER_PREFIX.length)

            val jwt = jwtVerifier.verify(token)
            val userId = UserId(jwt.subject)

            val rolesClaim = jwt.claims[ROLES_CLAIM]
            val roles = rolesClaim?.asList(String::class.java) ?: emptyList()

            return AuthRestUserDetails(userId, roles)
        } catch (verificationEx: JWTVerificationException) {
            return null
        }
    }
}
