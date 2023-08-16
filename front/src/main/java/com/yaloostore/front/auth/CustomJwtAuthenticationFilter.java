package com.yaloostore.front.auth;

import com.yaloostore.front.auth.jwt.meta.AuthInformation;
import com.yaloostore.front.common.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.yalooStore.security_utils.util.AuthUtil.HEADER_UUID;
import static com.yalooStore.security_utils.util.AuthUtil.JWT;


@RequiredArgsConstructor
@Slf4j
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {


    private final CookieUtils cookieUtils;

    private final RedisTemplate redisTemplate;




    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String member = cookieUtils.getUuidFromCookie(request.getCookies(), HEADER_UUID.getValue());

        if (Objects.isNull(member)){
            filterChain.doFilter(request,response);
            return;
        }

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){

            AuthInformation authInformation = (AuthInformation) redisTemplate.opsForHash().get(member, JWT.getValue());

            log.info("auth loginId= ==== == {} ", authInformation.getLoginId());

            Collection<? extends GrantedAuthority> authorities = getGrantAuthority(authInformation.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authInformation.getLoginId(), authInformation.getAccessToken(),authorities);

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationToken);

            filterChain.doFilter(request,response);
        }


    }

    private Collection<? extends GrantedAuthority> getGrantAuthority(List<String> authorities) {

        if (Objects.isNull(authorities)){
            return null;

        }
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }
}
