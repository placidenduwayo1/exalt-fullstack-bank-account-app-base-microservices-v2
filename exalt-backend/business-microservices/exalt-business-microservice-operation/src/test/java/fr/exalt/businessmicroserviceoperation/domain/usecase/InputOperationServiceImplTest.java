package fr.exalt.businessmicroserviceoperation.domain.usecase;

import fr.exalt.businessmicroserviceoperation.domain.avromodels.operation.OperationAvro;
import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.*;
import fr.exalt.businessmicroserviceoperation.domain.finalvalues.FinalValues;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.KafkaProducerService;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.OutputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.dtos.OperationDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.dtos.TransferDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@Slf4j
class InputOperationServiceImplTest {
    @Mock
    private OutputOperationService mock1;
    @Mock
    private KafkaProducerService mock2;
    @InjectMocks
    private InputOperationServiceImpl underTest;
    private static final String ID = "1";
    private static final String TYPE = "depot";
    private static final String STATE = "active";
    private static final double MOUNT = 2500;
    private static final String ACCOUNT_ID = "1";
    private static final String CREATED_AT = "now";
    private final Customer customer = new Customer.CustomerBuilder()
            .firstname("placide")
            .lastname("nduwayo")
            .customerId(ID)
            .email("placide.nd@gmail.com")
            .state(STATE)
            .build();
    private final BankAccount bankAccount = new BankAccount.AccountBuilder()
            .accountId(ID)
            .type("current")
            .state(STATE)
            .balance(10000)
            .overdraft(200)
            .customerId(ID)
            .customer(customer)
            .build();
    private final OperationDto dtoDepot = OperationDto.builder()
            .type(TYPE)
            .mount(MOUNT)
            .accountId(ACCOUNT_ID)
            .build();
    private final OperationDto dtoRetrait = OperationDto.builder()
            .type("retrait")
            .mount(MOUNT)
            .accountId(ACCOUNT_ID)
            .build();
    private final Operation operationDepot = MapperService.fromTo(dtoDepot);

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createOperationDepot(){
        //PREPARE
        operationDepot.setOperationId(ID);
        operationDepot.setCreatedAt(CREATED_AT);
        final String customerId = "1";
        //EXECUTE
        when(mock1.loadRemoteCustomer(customerId)).thenReturn(customer);
        when(mock1.loadRemoteAccount(dtoDepot.getAccountId())).thenReturn(bankAccount);
        when(mock1.updateRemoteAccount(anyString(), any(BankAccountDto.class)))
                .thenReturn(bankAccount);
        when(mock1.createOperationKafkaConsumer(any(OperationAvro.class))).thenReturn(operationDepot);
        Operation actual = underTest.createOperation(dtoDepot);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).loadRemoteCustomer(customerId);
            verify(mock1, atLeast(1)).loadRemoteAccount(dtoDepot.getAccountId());
            verify(mock1, atLeast(1))
                    .updateRemoteAccount(anyString(), any(BankAccountDto.class));
            assertNotNull(actual);
            log.info("{}", actual);
        });
    }

    @Test
    void createOperationRetrait(){
        //PREPARE
        final Operation operationRetrait = MapperService.fromTo(dtoRetrait);
        operationRetrait.setCreatedAt(CREATED_AT);
        final String customerId = "1";
        //EXECUTE
        when(mock1.loadRemoteCustomer(customerId)).thenReturn(customer);
        when(mock1.loadRemoteAccount(dtoDepot.getAccountId())).thenReturn(bankAccount);
        when(mock1.updateRemoteAccount(anyString(), any(BankAccountDto.class)))
                .thenReturn(bankAccount);
        when(mock1.createOperationKafkaConsumer(any(OperationAvro.class))).thenReturn(operationRetrait);
        Operation actual = underTest.createOperation(dtoRetrait);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).loadRemoteCustomer(customerId);
            assertNotNull(actual);
            log.info("{}", actual);
        });
    }

    @Test
    void getAllOperations() {
        //PREPARE
        final Collection<Operation> operations = List.of(operationDepot);
        //EXECUTE
        operations.forEach(operation1 ->
            when(mock1.loadRemoteAccount(operation1.getAccountId())).thenReturn(bankAccount));
        when(mock1.getAllOperations()).thenReturn(operations);
        Collection<Operation> actual = underTest.getAllOperations();
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getAllOperations();
            assertNotNull(actual);
            assertFalse(actual.isEmpty());
            log.info("{}", actual);
        });
    }

    @Test
    void getAccountOperations(){
        //PREPARE
        final Collection<Operation> operations = List.of(operationDepot);
        //EXECUTE
        when(mock1.loadRemoteAccount(ID)).thenReturn(bankAccount);
        when(mock1.getAccountOperations(ID)).thenReturn(operations);
        Collection<Operation> actual = underTest.getAccountOperations(ID);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getAccountOperations(ID);
            assertEquals(1, actual.size());
            log.info("{}", actual);
        });
    }

    @Test
    void transferBetweenAccounts(){
        //PREPARE
        BankAccount origin = bankAccount;
        final BankAccount destination = new BankAccount.AccountBuilder()
                .accountId("2")
                .type("saving")
                .state(STATE)
                .balance(10000)
                .customerId(ID)
                .customer(customer)
                .build();
        final TransferDto transferDto = TransferDto.builder()
                .origin(ID)
                .destination(destination.getAccountId())
                .mount(1000)
                .build();
        final BankAccount updatedOrigin = new BankAccount.AccountBuilder()
                .accountId(ID)
                .customer(customer)
                .customerId(ID)
                .balance(9000)
                .type(TYPE)
                .state(STATE)
                .build();
        final BankAccount updatedDestination = new BankAccount.AccountBuilder()
                .accountId("2")
                .customer(customer)
                .customerId(ID)
                .balance(11000)
                .type(TYPE)
                .state(STATE)
                .build();
        log.info("{}", updatedDestination);
        log.info("{}", updatedOrigin);
        //EXECUTE
        when(mock1.loadRemoteAccount(transferDto.getOrigin())).thenReturn(origin);
        when(mock1.loadRemoteAccount(transferDto.getDestination())).thenReturn(destination);
        when(mock1.loadRemoteCustomer(origin.getCustomerId())).thenReturn(customer);
        when(mock1.loadRemoteCustomer(destination.getCustomerId())).thenReturn(customer);
        when(mock1.updateRemoteAccount(anyString(), any(BankAccountDto.class)))
                .thenReturn(updatedOrigin);
        when(mock1.updateRemoteAccount(anyString(), any(BankAccountDto.class)))
                .thenReturn(updatedDestination);
        Map<String, BankAccount> actual = underTest.transferBetweenAccounts(transferDto);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).loadRemoteAccount(transferDto.getOrigin());
            verify(mock1, atLeast(1)).loadRemoteAccount(transferDto.getDestination());
            verify(mock1, atLeast(1)).loadRemoteCustomer(origin.getCustomerId());
            verify(mock1, atLeast(1)).loadRemoteCustomer(destination.getCustomerId());
            verify(mock1, atLeast(1))
                    .updateRemoteAccount(anyString(), any(BankAccountDto.class));
            verify(mock1, atLeast(1))
                    .updateRemoteAccount(anyString(), any(BankAccountDto.class));
            assertNotNull(actual);
        });
    }

    @Test
    void testOperationBusinessExceptionsCreateOperation() {
        OperationDto dto1 = OperationDto.builder()
                .type("")
                .mount(5)
                .accountId("")
                .build();
        RuntimeException exception1 = assertThrows(OperationRequestFieldsInvalidException.class, () ->
            underTest.createOperation(dto1));

        OperationDto dto2 = OperationDto.builder()
                .type("unknown")
                .mount(2000)
                .accountId(ID)
                .build();
        RuntimeException exception2 = assertThrows(OperationTypeInvalidException.class, () ->
            underTest.createOperation(dto2));

        OperationDto dto3 = OperationDto.builder()
                .type("depot")
                .mount(2000)
                .accountId(ID)
                .build();
        BankAccount remoteBankAccount1 = new BankAccount.AccountBuilder()
                .customerId(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)
                .accountId(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)
                .type(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)
                .state(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)
                .build();
        RuntimeException exception3 = assertThrows(RemoteBankAccountApiUnreachableException.class, () -> {
            when(mock1.loadRemoteAccount(dto3.getAccountId())).thenReturn(remoteBankAccount1);
            underTest.createOperation(dto3);
        });
        BankAccount remoteBankAccount2 = new BankAccount.AccountBuilder()
                .customerId(bankAccount.getCustomerId())
                .accountId(bankAccount.getAccountId())
                .type(bankAccount.getType())
                .state("suspended")
                .build();
        RuntimeException exception4 = assertThrows(RemoteBankAccountSuspendedException.class, () -> {
            when(mock1.loadRemoteAccount(dto3.getAccountId())).thenReturn(remoteBankAccount2);
            underTest.createOperation(dto3);
        });


        OperationDto dto5 = OperationDto.builder()
                .type("depot")
                .mount(2000)
                .accountId(ID)
                .build();
        Customer c1 = new Customer.CustomerBuilder()
                .customerId(FinalValues.REMOTE_CUSTOMER_UNREACHABLE)
                .build();
        RuntimeException exception5 = assertThrows(RemoteCustomerApiUnreachableException.class, () -> {
            when(mock1.loadRemoteAccount(dto5.getAccountId())).thenReturn(bankAccount);
            when(mock1.loadRemoteCustomer(bankAccount.getCustomerId())).thenReturn(c1);
            underTest.createOperation(dto5);
        });

        Customer c2 = new Customer.CustomerBuilder()
                .customerId(customer.getCustomerId())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .state("archive")
                .build();
        RuntimeException exception6 = assertThrows(RemoteCustomerStateInvalidException.class, () -> {
            when(mock1.loadRemoteAccount(dto5.getAccountId())).thenReturn(bankAccount);
            when(mock1.loadRemoteCustomer(bankAccount.getCustomerId())).thenReturn(c2);
            underTest.createOperation(dto5);
        });
        BankAccount remoteBankAccount3 = new BankAccount.AccountBuilder()
                .customerId(bankAccount.getCustomerId())
                .accountId(bankAccount.getAccountId())
                .type("saving")
                .state(STATE)
                .build();
        RuntimeException exception7 = assertThrows(RemoteBankAccountTypeInaccessibleFromOutsideException.class, () -> {
            when(mock1.loadRemoteCustomer(bankAccount.getCustomerId())).thenReturn(customer);
            when(mock1.loadRemoteAccount(dto5.getAccountId())).thenReturn(remoteBankAccount3);
            underTest.createOperation(dto5);
        });

        OperationDto dto6 = OperationDto.builder()
                .type("retrait")
                .mount(13000)
                .accountId(ID)
                .build();
        Exception exception8 = assertThrows(RemoteBankAccountBalanceException.class, () -> {
            when(mock1.loadRemoteAccount(dto6.getAccountId())).thenReturn(bankAccount);
            underTest.createOperation(dto6);
        });
        assertAll("bs-exceptions", () -> {
            assertNotNull(exception1);
            assertNotNull(exception2);
            assertNotNull(exception3);
            assertNotNull(exception4);
            assertNotNull(exception5);
            assertNotNull(exception6);
            assertNotNull(exception7);
            assertNotNull(exception8);
        });
    }

    @Test
    void testOperationBusinessExceptionsGetAccountOperations() {
        InputOperationServiceImpl inputOperationBusinessException = new InputOperationServiceImpl(mock1, mock2);
        BankAccount remoteBankAccount1 = new BankAccount.AccountBuilder()
                .customerId(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)
                .accountId(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)
                .type(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)
                .build();
        RuntimeException exception1 = assertThrows(RemoteBankAccountApiUnreachableException.class, () -> {
            when(mock1.loadRemoteAccount(ID)).thenReturn(remoteBankAccount1);
            inputOperationBusinessException.getAccountOperations(ID);
        });

        BankAccount remoteBankAccount2 = new BankAccount.AccountBuilder()
                .customerId(bankAccount.getCustomerId())
                .accountId(bankAccount.getAccountId())
                .type("saving")
                .state(STATE)
                .build();
        RuntimeException exception2 = assertThrows(RemoteBankAccountTypeInaccessibleFromOutsideException.class, () -> {
            when(mock1.loadRemoteAccount(ID)).thenReturn(remoteBankAccount2);
            inputOperationBusinessException.getAccountOperations(ID);
        });

        assertAll("exceptions", () -> {
            assertNotNull(exception1);
            assertNotNull(exception2);
        });
    }

    @Test
    void testOperationBusinessExceptionsTransferBetweenAccounts() {
        TransferDto dto1 = TransferDto.builder()
                .origin("id1")
                .destination("id2")
                .mount(2000)
                .build();
        BankAccount origin1 = new BankAccount.AccountBuilder()
                .accountId(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)
                .build();
        BankAccount destination1 = new BankAccount.AccountBuilder()
                .accountId(FinalValues.REMOTE_ACCOUNT_UNREACHABLE)
                .build();
        RuntimeException exception1 = assertThrows(RemoteBankAccountApiUnreachableException.class, () -> {
            when(mock1.loadRemoteAccount(dto1.getOrigin())).thenReturn(origin1);
            when(mock1.loadRemoteAccount(dto1.getDestination())).thenReturn(destination1);
            underTest.transferBetweenAccounts(dto1);
        });
        BankAccount origin2 = new BankAccount.AccountBuilder()
                .accountId(ID)
                .type(TYPE)
                .state("suspended")
                .build();
        BankAccount destination2 = new BankAccount.AccountBuilder()
                .accountId(ID)
                .type(TYPE)
                .state(STATE)
                .build();
        RuntimeException exception2 = assertThrows(RemoteAccountSuspendedException.class, () -> {
            when(mock1.loadRemoteAccount(dto1.getOrigin())).thenReturn(origin2);
            when(mock1.loadRemoteAccount(dto1.getDestination())).thenReturn(destination2);
            underTest.transferBetweenAccounts(dto1);
        });
        BankAccount origin3 = new BankAccount.AccountBuilder()
                .accountId(ID)
                .type(TYPE)
                .state(STATE)
                .customerId(ID)
                .customer(customer)
                .build();
        BankAccount destination3 = new BankAccount.AccountBuilder()
                .accountId(ID)
                .type(TYPE)
                .state(STATE)
                .customerId(ID)
                .customer(customer)
                .build();
        Customer c1 = new Customer.CustomerBuilder()
                .customerId(FinalValues.REMOTE_CUSTOMER_UNREACHABLE)
                .build();
        RuntimeException exception3 = assertThrows(RemoteCustomerApiUnreachableException.class, () -> {
            when(mock1.loadRemoteAccount(dto1.getOrigin())).thenReturn(origin3);
            when(mock1.loadRemoteCustomer(origin3.getCustomerId())).thenReturn(c1);
            when(mock1.loadRemoteAccount(dto1.getDestination())).thenReturn(destination3);
            underTest.transferBetweenAccounts(dto1);
        });

        BankAccount destination4 = new BankAccount.AccountBuilder()
                .accountId(ID)
                .type(TYPE)
                .state(STATE)
                .customerId(ID)
                .customer(customer)
                .build();
        Customer c2 = new Customer.CustomerBuilder()
                .customerId(ID)
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .state("archive")
                .build();
        RuntimeException exception4 = assertThrows(RemoteCustomerStateInvalidException.class, ()->{
            when(mock1.loadRemoteAccount(dto1.getOrigin())).thenReturn(origin3);
            when(mock1.loadRemoteAccount(dto1.getDestination())).thenReturn(destination3);
            when(mock1.loadRemoteCustomer(destination4.getCustomerId())).thenReturn(c2);
            underTest.transferBetweenAccounts(dto1);
        });

        BankAccount origin5 = new BankAccount.AccountBuilder()
                .accountId(ID)
                .type(TYPE)
                .state(STATE)
                .customerId(ID)
                .customer(customer)
                .balance(200)
                .build();
        RuntimeException exception5 = assertThrows(RemoteBankAccountBalanceException.class,()->{
            when(mock1.loadRemoteAccount(dto1.getOrigin())).thenReturn(origin5);
            when(mock1.loadRemoteCustomer(origin5.getCustomerId())).thenReturn(customer);
            underTest.transferBetweenAccounts(dto1);
        });
        BankAccount origin6 = new BankAccount.AccountBuilder()
                .accountId(ID)
                .type("saving")
                .state(STATE)
                .customerId(ID)
                .customer(customer)
                .balance(2500)
                .build();
        BankAccount destination6 = new BankAccount.AccountBuilder()
                .accountId(ID)
                .type("current")
                .state(STATE)
                .customerId(ID)
                .customer(customer)
                .balance(2500)
                .build();
        Customer customer1 = new Customer.CustomerBuilder()
                .customerId("1")
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .state(customer.getState())
                .build();
        Customer customer2 = new Customer.CustomerBuilder()
                .customerId("2")
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .state(customer.getState())
                .build();

        RuntimeException exception6 = assertThrows(RemoteBankAccountTypeInaccessibleFromOutsideException.class, ()->{
            when(mock1.loadRemoteAccount(dto1.getOrigin())).thenReturn(origin6);
            when(mock1.loadRemoteCustomer(origin6.getCustomerId())).thenReturn(customer1);
            when(mock1.loadRemoteAccount(dto1.getDestination())).thenReturn(destination6);
            when(mock1.loadRemoteCustomer(destination6.getCustomerId())).thenReturn(customer2);
            underTest.transferBetweenAccounts(dto1);
        });

        assertAll("exceptions", () -> {
            assertNotNull(exception1);
            assertNotNull(exception2);
            assertNotNull(exception3);
            assertNotNull(exception4);
            assertNotNull(exception5);
            assertNotNull(exception6);
        });
    }
}