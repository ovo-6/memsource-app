package com.ovo6.memsource.provider;

/**
 * Provider auth tokens based on user credentials.
 */
public interface TokenProvider {

    /**
     * Return token based on username and password.
     * @param username username
     * @param password password
     * @return auth token as string
     */
    String getToken(String username, String password);
}
