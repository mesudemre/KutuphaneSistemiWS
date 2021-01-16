package com.mesutemre.enums;

public enum KitapIslemEnum {

	S("S","Kitap Kayıt"),
	D("D","Kitap Silme"),
    U("U","Kitap Güncelleme");

	private String value;
	private String label;

	private KitapIslemEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static KitapIslemEnum getEnumByValue(String value) {
		for (KitapIslemEnum k : KitapIslemEnum.values()) {
			if (k.value.equals(value)) {
				return k;
			}
		}
		return null;
	}

	public static KitapIslemEnum getenumByLabel(String label) {
		for (KitapIslemEnum k : KitapIslemEnum.values()) {
			if (k.label.equals(label)) {
				return k;
			}
		}
		return null;
	}
}
