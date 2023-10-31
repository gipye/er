package com.izi.er.security.jwt;

import com.nimbusds.common.contenttype.ContentType;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
public class JwtTokenResolver implements BearerTokenResolver {
    /// 요청 파라미터를 통해 토큰을 전달하는 경우 파라미터 변수명
    private static final String ACCESS_TOKEN_PARAMETER_NAME = "access_token";

    /// bearer 토큰 문자열의 정규 표현식, 이 표현식에서 벗어나면 잘못된 토큰이다.
    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);

    /// 헤더를 통해 토큰이 전달되는 경우 헤더의 키값 문자열을 나타내는 변수 (필요 시 setter 메소드로 원하는 문자열로 바꿀 수 있음)
    private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

    /**
     * 요청 헤더 또는 파라미터에 bearer 토큰이 있으면 해당 토큰을 반환한다.
     * 토큰으로 추정되는 문자열이 두 개 이상 발견되면 {@link OAuth2AuthenticationException} 예외를 던진다.
     * 토큰으로 추정되는 문자열이 발견되지 않으면 {@code null}을 반환한다.
     * @param request HttpServletRequest 객체
     * @return bearer 토큰 또는 null
     */
    @Override
    public String resolve(final HttpServletRequest request) {
        String authenticationHeaderToken = resolveFromAuthorizationHeader(request);
        String authenticationParameterToken = isSupportParameterRequest(request) ? resolveFromAuthorizationParameter(request) : null;

        if (authenticationHeaderToken != null) {
            if (authenticationParameterToken != null) {
                throw new OAuth2AuthenticationException("multiple bearer tokens in the request");
            }
            return authenticationHeaderToken;
        } else if (authenticationParameterToken != null) {
            return authenticationParameterToken;
        }
        return null;
    }

    /**
     * 헤더로부터 bearer 토큰에 해당하는 문자열을 반환한다.
     * 토큰의 형태가 bearer 토큰의 형태가 아닐 경우 {@code null}을 반환한다.
     * @param request {@link HttpServletRequest} 객체
     * @return String 토큰에 해당하는 문자열
     */
    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(this.bearerTokenHeaderName);

        // bearer로 시작하지 않으면 bearer 토큰이 아니다.
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        }

        // bearer 토큰의 정규 표현식과 다르면 bearer 토큰이 아니다.
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            return null;
        }
        return matcher.group();
    }

    /**
     * http 요청이 파라미터를 지원하는 요청인지 검사한다.
     * http 요청의 content-type이 폼 데이터거나, 요청 메소드가 get이면 파라미터를 지원하는 요청이다.
     * @param request {@link HttpServletRequest} 객체
     * @return {@code boolean}
     */
    private boolean isSupportParameterRequest(HttpServletRequest request) {
        return isContentTypeForm(request) || isGetRequest(request);
    }

    /**
     * 파라미터 변수명이 {@link ACCESS_TOKEN_PARAMETER_NAME}인 것의 값들은 모두 토큰으로 판단한다.
     * 없다면 null을 반환, 둘 이상이면 예외를 발생시킨다.
     * 정확히 하나라면 해당 문자열을 반환한다.
     * @param request {@link HttpServletRequest} 객체
     * @return String 토큰에 해당하는 문자열
     * @throws OAuth2AuthenticationException 토큰으로 추정되는 문자열이 둘 이상일 경우 예외 발생
     */
    private static String resolveFromAuthorizationParameter(HttpServletRequest request) throws OAuth2AuthenticationException {
        String[] values = request.getParameterValues(ACCESS_TOKEN_PARAMETER_NAME);
        if (values == null || values.length == 0) {
            return null;
        } else if (values.length == 1) {
            return values[0];
        }
        throw new OAuth2AuthenticationException("multiple bearer tokens in the request");
    }

    /**
     * http 요청의 body 데이터 타입이 application/x-www-form-urlencoded 인지 확인한다.
     * 이 body 데이터 타입은 post 요청 시 파라미터를 지원한다.
     * @param request {@link HttpServletRequest}
     * @return {@code boolean}
     */
    private boolean isContentTypeForm(HttpServletRequest request) {
        return MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType());
    }
    /**
     * http 요청이 get 메소드인지 확인한다.
     * get 요청은 uri를 통한 파라미터를 지원한다.
     * @param request {@link HttpServletRequest}
     * @return {@code boolean}
     */
    private boolean isGetRequest(HttpServletRequest request) {
        return HttpMethod.GET.name().equals(request.getMethod());
    }
}
