package fr.exalt.businessmicroservicecustomer.domain.finalvalues;

public class FinalValues {
    private FinalValues(){}
    public static final String CUSTOMER_STATE_INVALID= "Customer State invalid";
    public static final String CUSTOMER_FIELD_INVALID = "Customer fields, one or more fields invalid";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String CUSTOMER_ALREADY_EXISTS ="Customer already exists" ;
    public static final String ADDRESS_FIELDS = "Address one or more fields invalid";
    public static final String ADDRESS_NOT_FOUND = "Address not found";
    public static final String CUSTOMER_ALREADY_IN_STATE = "Customer is already in that state";
    public static final String INITIAL_STATE = "active";
    public static final String EMAIL_IS_USED="Email already used";
}
