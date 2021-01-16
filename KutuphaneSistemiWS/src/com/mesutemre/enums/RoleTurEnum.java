package com.mesutemre.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleTurEnum {

	ADMIN("ROLE_ADMIN","Admin"),
    NORMAL_USER("NORMAL_USER","Normal User");

	private String value;
	private String label;

	private RoleTurEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static RoleTurEnum getEnumByValue(String value) {
		for (RoleTurEnum k : RoleTurEnum.values()) {
			if (k.value.equals(value)) {
				return k;
			}
		}
		return null;
	}

	public static RoleTurEnum getenumByLabel(String label) {
		for (RoleTurEnum k : RoleTurEnum.values()) {
			if (k.label.equals(label)) {
				return k;
			}
		}
		return null;
	}
}
