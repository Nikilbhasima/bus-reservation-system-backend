    package com.sdcProject.busReservationSystem.oauth2;

    import com.sdcProject.busReservationSystem.custome.MyUserDetailsService;
    import com.sdcProject.busReservationSystem.jwtConfig.JwtService;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
    import org.springframework.stereotype.Component;
    import org.springframework.web.util.UriComponentsBuilder;

    import java.io.IOException;

    @Component
    @RequiredArgsConstructor
    public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        private final JwtService jwtService;
        private final MyUserDetailsService myUserDetailsService;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException {

            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // Load user details to generate JWT token
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(oAuth2User.getEmail());
            String jwtToken = jwtService.generateToken(userDetails);

            // Redirect to frontend with JWT token
            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/oauth2/redirect")
                    .queryParam("token", jwtToken)
                    .build().toUriString();

            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }