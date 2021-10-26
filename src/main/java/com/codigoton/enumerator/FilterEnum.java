package com.codigoton.enumerator;

public enum FilterEnum {

    TIPO_DE_CLIENTE("TC"),
    COD_UBICACION_GEO("UG"),
    RANGO_INI_BALANCE("RI"),
    RANGO_FIN_BALANCE("RF");

    private String code;

    FilterEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
