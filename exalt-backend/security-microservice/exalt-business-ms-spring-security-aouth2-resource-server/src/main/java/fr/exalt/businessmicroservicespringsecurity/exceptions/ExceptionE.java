package fr.exalt.businessmicroservicespringsecurity.exceptions;

public class ExceptionE {

    private ExceptionE() {
    }
    public static final String USER_INFO = "User information invalid Exception";
    public static final String USER_EXISTS = "User already exists Exception";
    public static final String USER_NOT_FOUND = "User not found Exception";
    public static final String ROLE_NOT_FOUND = "Role not found Exception";
    public static final String ROLE_INFO = "Role information invalid Exception";
    public static final String ROLE_EXISTS = "Role already exists Exception";
    public static final String PASSWORD_MATCH = "Password and confirm password not match Exception";
    public static final String USER_POSSESS_ROLE = "User already possesses this role Exception";
    public static final String USER_ROLE= "This role is not assigned to this user Exception";
    public static final String USER_AUTH_FAIL = "User authentication fails Exception";
    public static final String REFRESH_TOKEN = "Refresh token is missing Exception";
}
