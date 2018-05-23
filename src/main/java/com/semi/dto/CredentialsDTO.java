package com.semi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date 22.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialsDTO {
    private String username;
    private String password;
}
