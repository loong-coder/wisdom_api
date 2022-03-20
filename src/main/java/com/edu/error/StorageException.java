package com.edu.error;

import cn.hutool.http.HttpStatus;


public class StorageException extends BusinessException {

	public StorageException(String message) {
		super(HttpStatus.HTTP_UNAUTHORIZED, message);
	}
}