package com.ovo6.memsource.controller;

import com.ovo6.memsource.model.Config;
import com.ovo6.memsource.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/memsource/config")
public class ConfigController {

    @Autowired
    private ConfigRepository configRepository;

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<Config> getConfiguration(@CookieValue("username") String username) {

        if (StringUtils.isEmpty(username)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Config config = configRepository.findOne(username);
        if (config == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(config , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<Config> updateConfiguration(@RequestBody Config config, HttpServletResponse response) {

        Config savedConfig = configRepository.save(config);

        response.addCookie(createCookie(config.getUsername()));
        return new ResponseEntity(savedConfig , HttpStatus.OK);
    }

    private Cookie createCookie(String username) {
        Cookie cookie = new Cookie("username", username);
        return cookie;
    }


}
