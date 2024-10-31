package fr.exalt.businessmicroservicecustomer.domain.finalvalues;

public class FinalValues {
    private FinalValues(){}
    public static final String CUSTOMER_STATE_INVALID= "Customer State invalid Exception";
    public static final String CUSTOMER_FIELD_INVALID = "Customer fields, one or more fields invalid Exception";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found Exception";
    public static final String CUSTOMER_ALREADY_EXISTS ="Customer already exists Exception" ;
    public static final String ADDRESS_FIELDS = "Address one or more fields invalid Exception";
    public static final String ADDRESS_NOT_FOUND = "Address not found Exception";
    public static final String CUSTOMER_EMAIL_INVALID = "Customer Email invalid Exception";
    public static final String CUSTOMER_ALREADY_IN_STATE = "Customer is already in that state Exception";
    public static final String INITIAL_STATE = "active";
}
