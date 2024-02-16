package com.ishanitech.ipalika.utils;


import com.ishanitech.ipalika.config.properties.RestBaseProperty;

public class ImageUtilService {
	public static String makeFullImageurl(RestBaseProperty restBaseProperty, String imageName) {
		String fullImageUrl = String.format("%s://%s:%s/%s/%s",
				restBaseProperty.getProtocol(),
				restBaseProperty.getDomain(),
				restBaseProperty.getPort(),
				restBaseProperty.getResourceLocation(),
				imageName);
		return fullImageUrl;
	}
	
	public static String makeFullResizedImageurl(RestBaseProperty restBaseProperty, String imageName) {
		String fullImageUrl = String.format("%s://%s:%s/%s/resized/%s",
				restBaseProperty.getProtocol(),
				restBaseProperty.getDomain(),
				restBaseProperty.getPort(),
				restBaseProperty.getResourceLocation(),
				imageName);
		return fullImageUrl;
	}
}
