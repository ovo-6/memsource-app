package com.ovo6.memsource.controller;


import com.ovo6.memsource.model.Config;
import com.ovo6.memsource.model.Project;
import com.ovo6.memsource.provider.ProjectsProvider;
import com.ovo6.memsource.provider.TokenProvider;
import com.ovo6.memsource.repository.ConfigRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjectsControllerTest {

    @InjectMocks ProjectsController controller;

    @Mock ConfigRepository configRepository;
    @Mock TokenProvider tokenProvider;
    @Mock ProjectsProvider projectsProvider;
    @Mock HttpServletResponse response;

    @Test
    public void whenNoUsernameReturnBadRequest() {
        ResponseEntity<List<Project>> projects = controller.getProjects("", "", response);

        assertThat(projects.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void whenUnknownUsernameReturnUnauthorized() {

        when(configRepository.findOne("user")).thenReturn(null);

        ResponseEntity<List<Project>> projects = controller.getProjects("user", "", response);

        assertThat(projects.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void whenTokenAuthFailsReturnUnauthorized() {

        doThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED))
                .when(tokenProvider)
                .getToken(anyString(), anyString());

        when(configRepository.findOne("user")).thenReturn(new Config());

        ResponseEntity<List<Project>> projects = controller.getProjects("user", "", response);

        assertThat(projects.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void whenTokenNotEmptyItShouldBePassedToProjectsProviderAndTokeProviderNotCalled() {
        when(configRepository.findOne("user")).thenReturn(new Config());
        Project project = new Project();
        when(projectsProvider.getProjects("token")).thenReturn(Arrays.asList(project));

        ResponseEntity<List<Project>> projects = controller.getProjects("user", "token", response);


        verify(tokenProvider, never()).getToken(anyString(), anyString());
        assertThat(projects.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(projects.getBody().size(), equalTo(1));
        assertThat(projects.getBody().get(0), equalTo(project));
    }

    @Test
    public void whenTokenEmptyItShouldCallTokenProviderAndCookieSet() {

        Config config = new Config();
        config.setUsername("user");
        config.setPassword("pass");

        when(configRepository.findOne("user")).thenReturn(config);
        when(tokenProvider.getToken("user", "pass")).thenReturn("token");

        Project project = new Project();
        when(projectsProvider.getProjects("token")).thenReturn(Arrays.asList(project));

        ResponseEntity<List<Project>> projects = controller.getProjects("user", "", response);

        verify(response).addCookie(any(Cookie.class));
        assertThat(projects.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(projects.getBody().size(), equalTo(1));
        assertThat(projects.getBody().get(0), equalTo(project));
    }
}