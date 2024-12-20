package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceaccount.domain.avrobeans.CurrentBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.avrobeans.SavingBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.BankAccountNotFoundException;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.proxy.RemoteCustomerServiceProxy;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.BankAccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.CurrentBankAccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.SavingBankAccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.repository.BankAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@Slf4j
class OutputAccountServiceImplTest {
    @Mock
    private BankAccountRepository mock1;
    @Mock
    private RemoteCustomerServiceProxy mock2;
    @InjectMocks
    private OutputAccountServiceImpl underTest;
    private static final String ID = "1";
    private static final String STATE = "active";
    private static final double BALANCE = 2000;
    private static final String CREATED_AT = "now";
    private static final String CUSTOMER_ID = "1";
    private final CurrentBankAccountModel currentBankAccount = new CurrentBankAccountModel();
    private final SavingBankAccountModel savingBankAccount = new SavingBankAccountModel();
    private final CustomerModel customer = CustomerModel.builder()
            .customerId("1")
            .firstname("placide")
            .lastname("nduwayo")
            .email("test@test.fr")
            .state("active")
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currentBankAccount.setAccountId(ID);
        currentBankAccount.setState(STATE);
        currentBankAccount.setBalance(BALANCE);
        currentBankAccount.setOverdraft(200);
        currentBankAccount.setCustomerId(CUSTOMER_ID);
        currentBankAccount.setCreatedAt(CREATED_AT);

        savingBankAccount.setAccountId(ID);
        savingBankAccount.setState(STATE);
        savingBankAccount.setBalance(BALANCE);
        savingBankAccount.setInterestRate(3.5);
        savingBankAccount.setCustomerId(CUSTOMER_ID);
        savingBankAccount.setCreatedAt(CREATED_AT);
    }

    @Test
    void createCurrentAccount() {
        //PREPARE
        CurrentBankAccount account = MapperService.mapToCurrentAccount(currentBankAccount);
        account.setType("current");
        Customer bean = MapperService.fromTo(customer);
        account.setCustomer(bean);
        CurrentBankAccountAvro avro = MapperService.mapCurrentAccountToAvro(account);
        //EXECUTE
        when(mock1.save(any(CurrentBankAccountModel.class))).thenReturn(currentBankAccount);
        BankAccount actual = underTest.createCurrentAccountKafkaConsumer(avro);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).save(any(CurrentBankAccountModel.class));
            assertNotNull(actual);
        });
    }


    @Test
    void getAllAccounts() {
        //PREPARE
        List<BankAccountModel> models = List.of(savingBankAccount, currentBankAccount);
        //EXECUTE
        when(mock1.findAllAccounts()).thenReturn(models);
        Collection<BankAccount> actual = underTest.getAllAccounts();
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).findAllAccounts();
            assertFalse(actual.isEmpty());
            assertEquals(2, actual.size());
        });
    }

    @Test
    void getAccount() throws BankAccountNotFoundException {
        //PREPARE
        final String id1 = "1";
        final String id2 = "2";
        //EXECUTE
        when(mock1.findById(id1)).thenReturn(Optional.of(currentBankAccount));
        when(mock1.findById(id2)).thenReturn(Optional.of(savingBankAccount));

        BankAccount actual1 = underTest.getAccount(id1);
        BankAccount actual2 = underTest.getAccount(id2);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).findById(id1);
            verify(mock1, atLeast(1)).findById(id2);
            assertNotNull(actual1);
            assertNotNull(actual2);
        });
    }

    @Test
    void getAccountOfGivenCustomer() {
        //PREPARE
        List<BankAccountModel> models = List.of(savingBankAccount, currentBankAccount);
        //EXECUTE
        when(mock2.loadRemoteCustomer(CUSTOMER_ID)).thenReturn(customer);
        when(mock1.findByCustomerId(CUSTOMER_ID)).thenReturn(models);
        Collection<BankAccount> actual = underTest.getAccountOfGivenCustomer(CUSTOMER_ID);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).findByCustomerId(CUSTOMER_ID);
            assertFalse(actual.isEmpty());
            assertEquals(2, actual.size());
        });
    }

    @Test
    void loadRemoteCustomer() {
        //PREPARE
        final String customerId = "2";
        final CustomerModel model = CustomerModel.builder()
                .customerId(customerId)
                .firstname("customer firstname")
                .lastname("customer lastname")
                .email("customer email")
                .state("customer state")
                .build();
        //EXECUTE
        when(mock2.loadRemoteCustomer(customerId)).thenReturn(model);
        Customer actual = underTest.loadRemoteCustomer(customerId);
        //VERIFY
        assertAll("", () -> {
            verify(mock2, atLeast(1)).loadRemoteCustomer(customerId);
            assertNotNull(actual);
        });
    }

    @Test
    void updateCurrentAccount() {
        //PREPARE
        CurrentBankAccount account = MapperService.mapToCurrentAccount(currentBankAccount);
        //EXECUTE
        when(mock1.save(any(CurrentBankAccountModel.class))).thenReturn(currentBankAccount);
        CurrentBankAccount actual = underTest.updateCurrentAccount(account);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).save(any(CurrentBankAccountModel.class));
            assertNotNull(actual);
        });
    }

    @Test
    void updateSavingAccount() {
        //PREPARE
        SavingBankAccount account = MapperService.mapToSavingAccount(savingBankAccount);
        //EXECUTE
        when(mock1.save(any(SavingBankAccountModel.class))).thenReturn(savingBankAccount);
        SavingBankAccount actual = underTest.updateSavingAccount(account);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).save(any(SavingBankAccountModel.class));
            assertNotNull(actual);
        });
    }

    @Test
    void switchAccountState() {
        //PREPARE
        final String state = "suspended";
        currentBankAccount.setState(state);
        savingBankAccount.setState(state);
        BankAccount currentAccount = MapperService.mapToCurrentAccount(currentBankAccount);
        BankAccount savingAccount = MapperService.mapToSavingAccount(savingBankAccount);
        //EXECUTE
        when(mock1.save(any(CurrentBankAccountModel.class))).thenReturn(currentBankAccount);
        when(mock1.save(any(SavingBankAccountModel.class))).thenReturn(savingBankAccount);
        BankAccount actual1 = underTest.switchAccountStateKafkaConsumer(currentAccount);
        BankAccount actual2 = underTest.switchAccountStateKafkaConsumer(savingAccount);
        //VERIFY
        assertAll("", () -> {
            verify(mock1, atLeast(1)).save(any(CurrentBankAccountModel.class));
            verify(mock1, atLeast(1)).save(any(SavingBankAccountModel.class));
            assertEquals("suspended", actual1.getState());
            assertEquals("suspended", actual2.getState());
        });
    }

    @Test
    void changeOverdraft() {
        //PREPARE
        final double newOverdraft = 350;
        currentBankAccount.setOverdraft(newOverdraft);
        currentBankAccount.setCustomerModel(customer);
        log.info("{}",currentBankAccount);
        CurrentBankAccount bankAccount = MapperService.mapToCurrentAccount(currentBankAccount);
        Customer bean = MapperService.fromTo(customer);
        bankAccount.setCustomer(bean);
        bankAccount.setType("current");
        CurrentBankAccountAvro avro = MapperService.mapCurrentAccountToAvro(bankAccount);
        //EXECUTE
        when(mock1.save(any(CurrentBankAccountModel.class))).thenReturn(currentBankAccount);
        BankAccount actual = underTest.changeOverdraftKafkaConsumer(avro);
        //VERIFY
        assertAll("",()->{
            verify(mock1, atLeast(1)).save(any(CurrentBankAccountModel.class));
            assertNotNull(actual);
        });
    }

    @Test
    void changeInterestRate() {
        //PREPARE
        final double newInterestRate = 3.8;
        savingBankAccount.setInterestRate(newInterestRate);
        SavingBankAccount bankAccount = MapperService.mapToSavingAccount(savingBankAccount);
        Customer bean = MapperService.fromTo(customer);
        bankAccount.setCustomer(bean);
        bankAccount.setType("saving");
        SavingBankAccountAvro avro = MapperService.mapSavingAccountToAvro(bankAccount);
        //EXECUTE
        when(mock1.save(any(SavingBankAccountModel.class))).thenReturn(savingBankAccount);
        BankAccount actual = underTest.changeInterestRateKafkaConsumer(avro);
        //VERIFY
        assertAll("",()->{
            verify(mock1, atLeast(1)).save(any(SavingBankAccountModel.class));
            assertNotNull(actual);
        });
    }
}