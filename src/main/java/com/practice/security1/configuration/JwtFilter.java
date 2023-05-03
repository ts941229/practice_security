package com.practice.security1.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.practice.security1.service.MemberService;
import com.practice.security1.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter{
	
	private final MemberService memberService;
	private final String secretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info("authorization : {}", authorization);

		// authentication이 없거나, 잘못 보냈을 때
		if(authorization == null || !authorization.startsWith("Bearer ")) {
			log.error("Authorization을 잘못 보냈습니다.");
			filterChain.doFilter(request, response);
			return;
		}
		
		// Token 꺼내기
		String token = authorization.split(" ")[1];
		
		// Token 만료 되었는지 여부
		if(JwtUtil.isExpired(token, secretKey)) {
			log.error("Token이 만료되었습니다. 다시 로그인 해주세요.");
			
			filterChain.doFilter(request, response);
			return;
		}
		
		String memberName = JwtUtil.getMemberName(token, secretKey);
		log.info("memberName:{}", memberName);
		
		// 권한 부여
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(memberName, null, List.of(new SimpleGrantedAuthority("Member")));
		
		// detail
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
		
	}

}
