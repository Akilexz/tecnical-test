package com.ec.tt.security;

import com.ec.tt.person.user.repositories.IUserRepository;
import com.ec.tt.person.user.audit.IKeycloakUserInfo;
import com.ec.tt.security.audit.AuthSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * KeycloakUserInfo.
 *
 * @author Joel Castro
 * @version 1.0
 */
public class KeycloakUserInfo implements IKeycloakUserInfo {

    @Lazy
    @Autowired
    private IUserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getUserId() {
        return userRepository.getUserIdByUsername(getUserName()).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserName() {
        return AuthSecurityUtil.getUserLogin().getUserName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIp() {
        String ipAddress;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            ipAddress = request.getHeader("x-original-forwarded-for");
            if (StringUtils.isBlank(ipAddress)) {
                ipAddress = request.getHeader("X-FORWARDED-FOR");
            }
            if (StringUtils.isBlank(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            } else {
                ipAddress = ipAddress.split(",")[0];
            }
        } catch (Exception e) {
            ipAddress = "ND";
        }
        return StringUtils.isBlank(ipAddress) ? "ND" : ipAddress;
    }
}
