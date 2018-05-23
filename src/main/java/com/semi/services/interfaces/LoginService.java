package com.semi.services.interfaces;

import com.semi.dto.CredentialsDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * Date 22.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface LoginService {
    String login(CredentialsDTO credentialsDTO);

    String login(CredentialsDTO credentialsDTO, HttpServletResponse response);
}
