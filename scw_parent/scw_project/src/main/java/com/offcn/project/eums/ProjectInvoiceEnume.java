package com.offcn.project.eums;

public enum ProjectInvoiceEnume {

    NO_FF((byte)0,"不开发票"),
    HAVE_FF((byte)1,"开发票");

    private byte code;
    private String type;

    private ProjectInvoiceEnume(byte code, String type) {
        this.code = code;
        this.type = type;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
