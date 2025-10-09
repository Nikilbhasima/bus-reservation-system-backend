package com.sdcProject.busReservationSystem.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.AddressStandardClaim;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OidcUser {

    private final OidcUser oidcUser;
    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String nameAttributeKey;

    public CustomOAuth2User(OidcUser oidcUser, Collection<? extends GrantedAuthority> authorities) {
        this.oidcUser = oidcUser;
        this.attributes = oidcUser.getAttributes();
        this.authorities = authorities;
        this.nameAttributeKey = "sub";
    }

    @Override
    public Map<String, Object> getClaims() {
        return oidcUser.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return oidcUser.getUserInfo();
    }

    @Override
    public OidcIdToken getIdToken() {
        return oidcUser.getIdToken();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return getAttribute(nameAttributeKey);
    }

    public String getEmail() {
        return getAttribute("email");
    }

    public String getGivenName() {
        return getAttribute("given_name");
    }

    public String getFamilyName() {
        return getAttribute("family_name");
    }

    public String getPicture() {
        return getAttribute("picture");
    }

    public String getGender() {
        return getAttribute("gender");
    }

    public AddressStandardClaim getUserAddress() {
        return oidcUser.getAddress();
    }


    public String getPhoneNumber() {
        return getAttribute("phone_number");
    }

}