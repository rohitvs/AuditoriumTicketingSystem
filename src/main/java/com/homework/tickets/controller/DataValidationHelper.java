package com.homework.tickets.controller;

public class DataValidationHelper {
	public static void checkEmptyValueForIntegerAndString(Object value, String message)
			throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException(message);
		} else {
			if (value instanceof Integer && value.equals(new Integer(0))) {
				throw new IllegalArgumentException(message);
			} else {
				if (value instanceof String && value.toString().trim().length() == 0) {
					throw new IllegalArgumentException(message);
				}
			}

		}
	}
}
