package com.project.workflow_approval_management_system.Service;

import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.project.workflow_approval_management_system.DTO.LoginDTO;

import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private HttpServletResponse response;

    @Test
    void login_shouldReturnAccessToken_whenAuthenticationIsSuccessful() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("testuser", "password");

        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authManager.authenticate(
                Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);

        Mockito.when(jwtService.generateRefreshToken("testuser"))
                .thenReturn("refresh-token");

        Mockito.when(jwtService.generateToken("testuser"))
                .thenReturn("access-token");

        // Act
        String result = loginService.login(loginDTO, response);

        // Assert
        Assertions.assertEquals("access-token", result);

        Mockito.verify(authManager)
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));

        Mockito.verify(response)
                .addHeader(Mockito.eq(HttpHeaders.SET_COOKIE), Mockito.anyString());

        Mockito.verify(jwtService).generateRefreshToken("testuser");
        Mockito.verify(jwtService).generateToken("testuser");
    }
}
