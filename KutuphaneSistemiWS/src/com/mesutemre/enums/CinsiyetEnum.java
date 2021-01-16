package com.mesutemre.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CinsiyetEnum {

	E("ERKEK", "Bay"),
	K("KADIN","Bayan");

	private String value;
	private String label;

	private CinsiyetEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static CinsiyetEnum getEnumByValue(String value) {
		for (CinsiyetEnum k : CinsiyetEnum.values()) {
			if (k.value.trim().equals(value.trim())) {
				return k;
			}
		}
		return null;
	}

	public static CinsiyetEnum getenumByLabel(String label) {
		for (CinsiyetEnum k : CinsiyetEnum.values()) {
			if (k.label.equals(label)) {
				return k;
			}
		}
		return null;
	}
	
}
