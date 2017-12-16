package com.ovo6.memsource.provider.impl.rest;

import com.ovo6.memsource.model.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MemsourceProjectsProviderTest {

    @InjectMocks MemsourceProjectsProvider projectsProvider;
    @Mock RestTemplate restTemplate;

    @Test
    public void getToken() throws URISyntaxException {
        Project project = new Project();
        Project[] projects = new Project[1];
        projects[0] = project;

        when(restTemplate.getForObject(
                new URI("https://cloud.memsource.com/web/api/v3/project/list?token=token"),
                Project[].class))
                .thenReturn(projects);

        List<Project> result = projectsProvider.getProjects("token");

        assertThat(result, equalTo(Arrays.asList(project)));
    }
}