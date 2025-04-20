package br.edu.ifsp.spo.bike_integration.hardcode;

public enum RoleType {

    ADMIN("ADMIN"), PJ("PJ"), PF("PF");

    private String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoleType fromValue(String value) {
        for (RoleType role : RoleType.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        return null;
    }

}
