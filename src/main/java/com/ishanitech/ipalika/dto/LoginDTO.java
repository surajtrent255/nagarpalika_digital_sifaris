/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Dec 13, 2019
 */
package com.ishanitech.ipalika.dto;

import lombok.NoArgsConstructor;

import lombok.Data;

/**
 * LoginRequestDTO
 */
@Data
@NoArgsConstructor
public class LoginDTO {
	private String username;
	private String password;
}
