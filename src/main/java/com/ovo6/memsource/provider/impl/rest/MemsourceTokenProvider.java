package com.ovo6.memsource.provider.impl.rest;

import com.ovo6.memsource.provider.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Token provider that is getting tokens via Memsource REST API.
 *
 * https://cloud.memsource.com/web/api/v3/auth/login?userName=USERNAME&password=PASSWORD
 *
 */
@Service
public class MemsourceTokenProvider implements TokenProvider {

    private static final String URL = "https://cloud.memsource.com/web/api/v3/auth/login";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getToken(String username, String password) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("userName", username)
                .queryParam("password", password);

        TokenResponse response = restTemplate.getForObject(builder.build().encode().toUri(), TokenResponse.class);
        return response.getToken();
    }
}
