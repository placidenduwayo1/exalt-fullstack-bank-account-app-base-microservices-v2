package fr.exalt.businessmicroservicespringsecurity.entities.models;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("Admin"),
    USER("User"),
    HR("HR"),
    DRH("DHR"),
    BS_MANAGER("BS-Manager"),
    CTO("CTO"),
    CEO("CEO"),
    IR("Engineer"),
    WORKER("Worker"),
    TM("Talent-Manager"),
    DR("Director"),
    CDR("Co-Director");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

}
