package com.ovo6.memsource.provider.impl.rest;

import com.ovo6.memsource.model.Project;
import com.ovo6.memsource.provider.ProjectsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * Loads user projects from Memsource REST API:
 *
 * api/v3/project/list
 */
@Service
public class MemsourceProjectsProvider implements ProjectsProvider {

    private static final String URL = "https://cloud.memsource.com/web/api/v3/project/list";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Project> getProjects(String authToken) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("token", authToken);

        Project[] projects = restTemplate.getForObject(builder.build().encode().toUri(), Project[].class);
        return Arrays.asList(projects);
    }
}
