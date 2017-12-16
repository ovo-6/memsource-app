package com.ovo6.memsource.provider.impl.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MemsourceTokenProviderTest {

    @InjectMocks MemsourceTokenProvider tokenProvider;
    @Mock RestTemplate restTemplate;

    @Test
    public void getToken() throws URISyntaxException {
        TokenResponse response = new TokenResponse();
        response.setToken("token");
        when(restTemplate.getForObject(
                new URI("https://cloud.memsource.com/web/api/v3/auth/login?userName=user1&password=pass1"),
                TokenResponse.class))
                .thenReturn(response);

        String token = tokenProvider.getToken("user1", "pass1");

        assertThat(token, equalTo("token"));
    }
}