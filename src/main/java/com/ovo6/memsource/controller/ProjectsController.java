package com.ovo6.memsource.controller;

import com.ovo6.memsource.model.Config;
import com.ovo6.memsource.model.Project;
import com.ovo6.memsource.provider.ProjectsProvider;
import com.ovo6.memsource.provider.TokenProvider;
import com.ovo6.memsource.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/memsource/projects")
public class ProjectsController {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private ProjectsProvider projectsProvider;


    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Project>> getProjects(@CookieValue("username") String username,
                                              @CookieValue(value = "token", required = false) String token,
                                              HttpServletResponse response) {

        if (StringUtils.isEmpty(username)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Config config = configRepository.findOne(username);
        if (config == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if (StringUtils.isEmpty(token)) {
            try {
                token = tokenProvider.getToken(config.getUsername(), config.getPassword());
                if (StringUtils.isEmpty(token)) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
                // save token to cookie to avoid always asking for token
                response.addCookie(createCookie(token));
            }
            catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);

                } else {
                    throw e;
                }
            }

        }

        List<Project> projects = projectsProvider.getProjects(token);


        return new ResponseEntity(projects , HttpStatus.OK);
    }

    private Cookie createCookie(String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60*10);
        return cookie;
    }


}
