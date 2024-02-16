package com.ishanitech.ipalika.model;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code Role} is the class which stores the role or authority of a principal object.
 * @author Umesh Bhujel
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class Role {
	private int id;
	private String role;
	private String roleNepali;
}
