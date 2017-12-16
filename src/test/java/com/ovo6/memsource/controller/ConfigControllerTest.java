package com.ovo6.memsource.controller;

import com.ovo6.memsource.model.Config;
import com.ovo6.memsource.repository.ConfigRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ConfigControllerTest {

    @InjectMocks ConfigController configController;

    @Mock ConfigRepository configRepository;
    @Mock HttpServletResponse response;

    @Test
    public void updateConfiguration() {
        Config config = new Config();
        config.setUsername("user");

        configController.updateConfiguration(config, response);

        verify(configRepository).save(config);
        verify(response).addCookie(any(Cookie.class));
    }
}