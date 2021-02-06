/**
 * @author benhurmarques
 * 
 * Feb 4, 2021
 */
package com.zallpy.testefull.util;

import java.util.UUID;

/**
 * @author benhurmarques
 *
 */
public class RandomStringUUID {

	private RandomStringUUID() {

	}

	public static UUID token() {
		return UUID.randomUUID();
	}

}
