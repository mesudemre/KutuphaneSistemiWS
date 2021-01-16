package com.mesutemre.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ParametreDurumEnum {

	AKTIF("AKTIF", "Aktif"),
	PASIF("PASIF","Pasif");

	private String value;
	private String label;

	private ParametreDurumEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static ParametreDurumEnum getEnumByValue(String value) {
		for (ParametreDurumEnum k : ParametreDurumEnum.values()) {
			if (k.value.equals(value)) {
				return k;
			}
		}
		return null;
	}

	public static ParametreDurumEnum getenumByLabel(String label) {
		for (ParametreDurumEnum k : ParametreDurumEnum.values()) {
			if (k.label.equals(label)) {
				return k;
			}
		}
		return null;
	}
	
}
