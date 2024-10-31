package fr.exalt.businessmicroserviceaccount.domain.usecase;

import fr.exalt.businessmicroserviceaccount.domain.avrobeans.CurrentBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.avrobeans.SavingBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.domain.finalvalues.FinalValues;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.KafkaProducerService;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountInterestRateDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountOverdraftDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountSwitchStatedDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
class InputBankAccountServiceImplTest {
    @Mock
    private OutputAccountService mock1;
    @Mock
    private KafkaProducerService mock2;
    @InjectMocks
    private InputBankAccountServiceImpl underTest;
    private static final String ID = "1";
    private static final String TYPE = "current";
    private static final String STATE = "active";
    private static final double BALANCE = 2000;
    private static final String CREATED_AT = "now";
    private static final String CUSTOMER_ID = "1";
    private final Customer customer = new Customer.CustomerBuilder()
            .customerId(CUSTOMER_ID)
            .firstname("placide")
            .lastname("nduwayo")
            .state("active")
            .email("placide.nd@gmail.com")
            .build();
    private final CurrentBankAccount currentBankAccount = new CurrentBankAccount.CurrentAccountBuilder()
            .build();
    private final SavingBankAccount savingBankAccount = new SavingBankAccount.SavingAccountBuilder()
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currentBankAccount.setAccountId(ID);
        currentBankAccount.setType(TYPE);
        currentBankAccount.setState(STATE);
        currentBankAccount.setBalance(BALANCE);
        currentBankAccount.setOverdraft(200);
        currentBankAccount.setCustomerId(CUSTOMER_ID);
        currentBankAccount.setCustomer(customer);
        currentBankAccount.setCreatedAt(CREATED_AT);

        savingBankAccount.setAccountId(ID);
        savingBankAccount.setType("saving");
        savingBankAccount.setState(STATE);
        savingBankAccount.setBalance(BALANCE);
        savingBankAccount.setInterestRate(3.5);
        savingBankAccount.setCustomerId(CUSTOMER_ID);
        savingBankAccount.setCustomer(customer);
        savingBankAccount.setCreatedAt(CREATED_AT);
    }

    @Test
    void createCurrentAccount(){
        //PREPARE
        BankAccountDto dto = BankAccountDto.builder()
                .type(TYPE)
                .balance(BALANCE)
                .customerId(CUSTOMER_ID)
                .build();
        log.info("{}", dto);
        //EXECUTE
        when(mock1.loadRemoteCustomer(dto.getCustomerId())).thenReturn(customer);
        when(mock1.createCurrentAccountKafkaConsumer(any(CurrentBankAccountAvro.class)))
                .thenReturn(currentBankAccount);
        BankAccount actual = underTest.createAccountKafkaEvent(dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1))
                    .loadRemoteCustomer(dto.getCustomerId());
            assertNotNull(actual);
        });
    }

    @Test
    void getAllAccounts() {
        //PREPARE
        Collection<BankAccount> accounts = List.of(currentBankAccount, savingBankAccount);
        //EXECUTE
        when(mock1.getAllAccounts()).thenReturn(accounts);
        Collection<BankAccount> actual = underTest.getAllAccounts();
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getAllAccounts();
            assertFalse(actual.isEmpty());
            assertEquals(2, actual.size());
        });
    }

    @Test
    void getAccount(){
        //PREPARE
        final String id1 = "1";
        final String id2 = "2";
        //EXECUTE
        when(mock1.getAccount(id1)).thenReturn(currentBankAccount);
        when(mock1.getAccount(id2)).thenReturn(savingBankAccount);
        BankAccount actual1 = underTest.getAccount(id1);
        BankAccount actual2 = underTest.getAccount(id2);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getAccount(id1);
            verify(mock1, atLeast(1)).getAccount(id2);
            assertNotNull(actual1);
            assertNotNull(actual2);
        });
    }

    @Test
    void getAccountOfGivenCustomer() {
        //PREPARE
        Collection<BankAccount> accounts = List.of(currentBankAccount, savingBankAccount);
        //EXECUTE
        when(mock1.getAccountOfGivenCustomer(CUSTOMER_ID)).thenReturn(accounts);
        Collection<BankAccount> actual = underTest.getAccountOfGivenCustomer(CUSTOMER_ID);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).getAccountOfGivenCustomer(CUSTOMER_ID);
            assertEquals(2, actual.size());
        });
    }

    @Test
    void updateCurrentAccount(){
        //PREPARE
        BankAccountDto dto = BankAccountDto.builder()
                .type(TYPE)
                .balance(BALANCE)
                .customerId(CUSTOMER_ID)
                .build();
        //EXECUTE
        when(mock1.loadRemoteCustomer(dto.getCustomerId())).thenReturn(customer);
        when(mock1.getAccount(ID)).thenReturn(currentBankAccount);
        when(mock1.updateCurrentAccount(any(CurrentBankAccount.class)))
                .thenReturn(currentBankAccount);
        BankAccount actual = underTest.updateAccount(ID, dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1))
                    .updateCurrentAccount(any(CurrentBankAccount.class));
            assertNotNull(actual);
        });
    }

    @Test
    void updateSavingAccount(){
        //PREPARE
        BankAccountDto dto = BankAccountDto.builder()
                .type("saving")
                .balance(BALANCE)
                .customerId(CUSTOMER_ID)
                .build();
        //EXECUTE
        when(mock1.loadRemoteCustomer(dto.getCustomerId())).thenReturn(customer);
        when(mock1.getAccount(ID)).thenReturn(savingBankAccount);
        when(mock1.updateSavingAccount(any(SavingBankAccount.class)))
                .thenReturn(savingBankAccount);
        BankAccount actual = underTest.updateAccount(ID, dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1))
                    .updateSavingAccount(any(SavingBankAccount.class));
            assertNotNull(actual);
        });
    }

    @Test
    void switchAccountState(){
        //PREPARE
        BankAccountSwitchStatedDto dto = BankAccountSwitchStatedDto.builder()
                .accountId(ID)
                .state("suspended")
                .build();
        //EXECUTE
        when(mock1.loadRemoteCustomer(CUSTOMER_ID)).thenReturn(customer);
        when(mock1.getAccount(ID)).thenReturn(currentBankAccount);
        when(mock1.switchAccountStateKafkaConsumer(currentBankAccount)).thenReturn(currentBankAccount);
        BankAccount actual = underTest.switchAccountState(dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).loadRemoteCustomer(CUSTOMER_ID);
            verify(mock1, atLeast(1)).getAccount(ID);
            assertNotNull(actual);
            assertEquals("suspended", actual.getState());
        });
    }

    @Test
    void changeOverdraft(){
        //PREPARE
        BankAccountOverdraftDto dto = BankAccountOverdraftDto.builder()
                .accountId(ID)
                .overdraft(300)
                .build();
        //EXECUTE
        when(mock1.loadRemoteCustomer(CUSTOMER_ID)).thenReturn(customer);
        when(mock1.getAccount(ID)).thenReturn(currentBankAccount);
        when(mock1.changeOverdraftKafkaConsumer(any(CurrentBankAccountAvro.class))).thenReturn(currentBankAccount);
        BankAccount actual = underTest.changeOverdraft(dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).loadRemoteCustomer(CUSTOMER_ID);
            verify(mock1, atLeast(1)).getAccount(ID);
            assertNotNull(actual);
        });
    }

    @Test
    void changeInterestRate(){
        //PREPARE
        BankAccountInterestRateDto dto = BankAccountInterestRateDto.builder()
                .accountId(ID)
                .interestRate(4.0)
                .build();

        //EXECUTE
        when(mock1.loadRemoteCustomer(CUSTOMER_ID)).thenReturn(customer);
        when(mock1.getAccount(ID)).thenReturn(savingBankAccount);
        when(mock1.changeInterestRateKafkaConsumer(any(SavingBankAccountAvro.class))).thenReturn(savingBankAccount);
        BankAccount actual = underTest.changeInterestRate(dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).loadRemoteCustomer(CUSTOMER_ID);
            verify(mock1, atLeast(1)).getAccount(ID);
            assertNotNull(actual);
        });
    }
    @Test
    void testBankAccountExceptions(){
        BankAccountDto dto1 = BankAccountDto.builder()
                .customerId(ID)
                .type("")
                .build();
        RuntimeException exception1 = assertThrows(BankAccountFieldsInvalidException.class, ()->
                underTest.createAccountKafkaEvent(dto1)
        );
        BankAccountDto dto2 = BankAccountDto.builder()
                .customerId(ID)
                .type("unknown")
                .build();
        RuntimeException exception2 = assertThrows(BankAccountTypeInvalidException.class, ()->
                underTest.createAccountKafkaEvent(dto2)
        );
        BankAccountSwitchStatedDto dto3 = BankAccountSwitchStatedDto.builder()
                .accountId(ID)
                .state("unknown")
                .build();
        RuntimeException exception3 = assertThrows(BankAccountStateInvalidException.class,()->{
            when(mock1.getAccount(ID)).thenReturn(currentBankAccount);
            when(mock1.loadRemoteCustomer(currentBankAccount.getCustomerId())).thenReturn(customer);
            underTest.switchAccountState(dto3);
        });

        BankAccountSwitchStatedDto dto4 = BankAccountSwitchStatedDto.builder()
                .accountId(ID)
                .state("active")
                .build();
        RuntimeException exception4 = assertThrows(BankAccountSameStateException.class,()->{
            when(mock1.getAccount(ID)).thenReturn(currentBankAccount);
            when(mock1.loadRemoteCustomer(currentBankAccount.getCustomerId())).thenReturn(customer);
            underTest.switchAccountState(dto4);
        });

        BankAccountOverdraftDto dto5 = BankAccountOverdraftDto.builder()
                .accountId(ID)
                .overdraft(500)
                .build();
        BankAccount bankAccount1 = currentBankAccount;
        bankAccount1.setState("suspended");
        RuntimeException exception5 = assertThrows(BankAccountSuspendException.class,()->{
            when(mock1.loadRemoteCustomer(bankAccount1.getCustomerId())).thenReturn(customer);
            when(mock1.getAccount(ID)).thenReturn(bankAccount1);
            underTest.changeOverdraft(dto5);
        });
        BankAccount bankAccount2 = savingBankAccount;
        RuntimeException exception6 = assertThrows(BankAccountTypeNotAcceptedException.class,()->{
            when(mock1.loadRemoteCustomer(bankAccount2.getCustomerId())).thenReturn(customer);
            when(mock1.getAccount(ID)).thenReturn(bankAccount2);
            underTest.changeOverdraft(dto5);
        });

        BankAccountOverdraftDto dto6 = BankAccountOverdraftDto.builder()
                .accountId(ID)
                .overdraft(-1)
                .build();
        BankAccount currentBankAccount1 = new CurrentBankAccount.CurrentAccountBuilder()
                .overdraft(200)
                .build();
        currentBankAccount1.setAccountId(ID);
        currentBankAccount1.setState(STATE);
        currentBankAccount1.setType(TYPE);
        currentBankAccount1.setCustomer(customer);
        currentBankAccount1.setBalance(BALANCE);
        bankAccount2.setCreatedAt(CREATED_AT);
        RuntimeException exception7 = assertThrows(BankAccountOverdraftInvalidException.class,()->{
            when(mock1.loadRemoteCustomer(currentBankAccount1.getCustomerId())).thenReturn(customer);
            when(mock1.getAccount(ID)).thenReturn(currentBankAccount1);
            underTest.changeOverdraft(dto6);
        });
        Customer c1 = new Customer.CustomerBuilder()
                .customerId(FinalValues.REMOTE_CUSTOMER_API)
                .build();
        BankAccountDto dto7 = BankAccountDto.builder()
                .customerId(ID)
                .type(TYPE)
                .build();
        RuntimeException exception8 = assertThrows(RemoteCustomerApiUnreachableException.class, ()->{
            when(mock1.loadRemoteCustomer(dto7.getCustomerId())).thenReturn(c1);
            underTest.createAccountKafkaEvent(dto7);
        });
        Customer c2 = new Customer.CustomerBuilder()
                .customerId(ID)
                .state("archive")
                .build();
        RuntimeException exception9 = assertThrows(RemoteCustomerStateInvalidException.class,()->{
            when(mock1.loadRemoteCustomer(dto7.getCustomerId())).thenReturn(c2);
            underTest.createAccountKafkaEvent(dto7);
        });

        BankAccountInterestRateDto rateDto = BankAccountInterestRateDto.builder()
                .accountId(ID)
                .interestRate(5.0)
                .build();
        BankAccount bankAccount3 = savingBankAccount;
        bankAccount3.setState("suspended");
        RuntimeException exception10 = assertThrows(BankAccountSuspendException.class,()->{
            when(mock1.loadRemoteCustomer(bankAccount3.getCustomerId())).thenReturn(customer);
            when(mock1.getAccount(rateDto.getAccountId())).thenReturn(bankAccount3);
            underTest.changeInterestRate(rateDto);
        });

        RuntimeException exception11 = assertThrows(BankAccountTypeNotAcceptedException.class, ()->{
            when(mock1.loadRemoteCustomer(currentBankAccount1.getCustomerId())).thenReturn(customer);
            when(mock1.getAccount(rateDto.getAccountId())).thenReturn(currentBankAccount1);
            underTest.changeInterestRate(rateDto);
        });

        assertAll("exceptions",()->{
            assertNotNull(exception1);
            assertNotNull(exception2);
            assertNotNull(exception3);
            assertNotNull(exception4);
            assertNotNull(exception5);
            assertNotNull(exception6);
            assertNotNull(exception7);
            assertNotNull(exception8);
            assertNotNull(exception9);
            assertNotNull(exception10);
            assertNotNull(exception11);
        });
    }
}