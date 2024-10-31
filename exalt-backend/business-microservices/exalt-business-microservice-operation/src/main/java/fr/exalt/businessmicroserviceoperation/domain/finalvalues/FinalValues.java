package fr.exalt.businessmicroserviceoperation.domain.finalvalues;

public class FinalValues {
    private FinalValues(){}
    public static final String REMOTE_ACCOUNT_UNREACHABLE = "it may be possible that remote bank account is unreachable Exception";
    public static final String OPERATION_REQUEST_FIELDS ="Operation one or more fields invalid Exception";
    public static final String OPERATION_TYPE = "Operation type invalid Exception";
    public static final String REMOTE_ACCOUNT_BALANCE = "Remote bank account Balance not enough Exception";
    public static final String REMOTE_CUSTOMER_UNREACHABLE ="it may be possible that remote customer is unreachable Exception";
    public static final String REMOTE_ACCOUNT_NOT_ACCESSIBLE_FROM_OUTSIDE = "Remote bank account type is inaccessible " +
            "from outside Exception";
    public static final String REMOTE_CUSTOMER_STATE ="Remote customer state Invalid Exception";
    public static final String REMOTE_BANK_ACCOUNT_SUSPENDED = "Remote bank account suspended Exception";
    public static final String REMOTE_ACCOUNT_SUSPENDED = "Remote Bank Account is suspended Exception";
    public static final String FORMATTER = "%s,%n%s";
    public static final String BANK_ACCOUNT_TYPE_SAVING = "saving";
    public static final String BANK_ACCOUNT_STATE_SUSPEND = "suspended";
    public static final String CUSTOMER_STATE_ARCHIVE = "archive";
    public static final String OPERATION_TYPE_DEPOSIT = "depot";
    public static final String OPERATION_TYPE_RETRAIT = "retrait";
}
