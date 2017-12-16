package com.ovo6.memsource.provider;

import com.ovo6.memsource.model.Project;

import java.util.List;

/**
 * Provider for user projects.
 */
public interface ProjectsProvider {

    /**
     * Return token based on username and password.
     * @param authToken user's auth token
     * @return list of user's projects
     */
    List<Project> getProjects(String authToken);
}
