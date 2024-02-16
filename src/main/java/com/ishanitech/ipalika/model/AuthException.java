package com.ishanitech.ipalika.model;
import org.springframework.security.core.AuthenticationException;

/**
 * A common {@code Exception} class which holds the exception related to authentication failure for
 * whatever reasons.
 * @author Umesh Bhujel
 * @since 1.0
 */
public class AuthException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	/**
	 * @param error message or detail that explains the cause of exception.
	 * @since 1.0
	 */
	public AuthException(String msg) {
		super(msg);
	}

}
