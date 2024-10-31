package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper;

import fr.exalt.businessmicroserviceaccount.domain.avrobeans.CurrentBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.avrobeans.CustomerAvro;
import fr.exalt.businessmicroserviceaccount.domain.avrobeans.SavingBankAccountAvro;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.CurrentBankAccountModel;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.SavingBankAccountModel;
import org.springframework.beans.BeanUtils;


public class MapperService {
    private MapperService(){}

    public static CurrentBankAccountAvro mapCurrentAccountToAvro(CurrentBankAccount account){
        CustomerAvro customerAvro = CustomerAvro.newBuilder()
                        .setCustomerId(account.getCustomerId())
                        .setFirstname(account.getCustomer().getFirstname())
                        .setLastname(account.getCustomer().getLastname())
                        .setEmail(account.getCustomer().getEmail())
                        .setState(account.getCustomer().getState())
                        .build();

        return CurrentBankAccountAvro.newBuilder()
                .setAccountId(account.getAccountId())
                .setType(account.getType())
                .setState(account.getState())
                .setBalance(account.getBalance())
                .setOverdraft(account.getOverdraft())
                .setCreatedAt(account.getCreatedAt())
                .setCustomerId(account.getCustomerId())
                .setCustomer(customerAvro)
                .build();
    }
   public static CurrentBankAccount mapToCurrentAccount(BankAccountDto dto){
        CurrentBankAccount currentAccount = CurrentBankAccount.builder().build();
        BeanUtils.copyProperties(dto, currentAccount);
        return currentAccount;
    }
    public static CurrentBankAccount mapToCurrentAccount(CurrentBankAccountModel model){
        CurrentBankAccount currentAccount = CurrentBankAccount.builder().build();
        BeanUtils.copyProperties(model, currentAccount);
        return currentAccount;
    }
    public static CurrentBankAccountModel mapToCurrentAccountModel(CurrentBankAccount account){
        CurrentBankAccountModel model = new CurrentBankAccountModel();
        BeanUtils.copyProperties(account, model);
        return model;
    }
    public static CurrentBankAccountModel mapToCurrentAccountModel(CurrentBankAccountAvro avro){
        CurrentBankAccountModel model = new CurrentBankAccountModel();
        BeanUtils.copyProperties(avro, model);
        return model;
    }

    public static SavingBankAccountAvro mapSavingAccountToAvro(SavingBankAccount account){
        CustomerAvro customerAvro = CustomerAvro.newBuilder()
                        .setCustomerId(account.getCustomerId())
                        .setFirstname(account.getCustomer().getFirstname())
                        .setLastname(account.getCustomer().getLastname())
                        .setEmail(account.getCustomer().getEmail())
                        .setState(account.getCustomer().getState())
                        .build();

        return SavingBankAccountAvro.newBuilder()
                .setAccountId(account.getAccountId())
                .setType(account.getType())
                .setState(account.getState())
                .setBalance(account.getBalance())
                .setInterestRate(account.getInterestRate())
                .setCreatedAt(account.getCreatedAt())
                .setCustomerId(account.getCustomerId())
                .setCustomer(customerAvro)
                .build();
    }
    public static SavingBankAccount mapToSavingAccount(BankAccountDto dto){
        SavingBankAccount savingAccount = SavingBankAccount.builder().build();
        BeanUtils.copyProperties(dto, savingAccount);
        return savingAccount;
    }

    public static SavingBankAccount mapToSavingAccount(SavingBankAccountModel model) {
        SavingBankAccount account = SavingBankAccount.builder().build();
        BeanUtils.copyProperties(model, account);
        return account;
    }
    public static SavingBankAccountModel mapToSavingAccountModel(SavingBankAccount account){
        SavingBankAccountModel model = new SavingBankAccountModel();
        BeanUtils.copyProperties(account, model);
        return model;
    }
    public static SavingBankAccountModel mapToSavingAccountModel(SavingBankAccountAvro avro){
        SavingBankAccountModel model = new SavingBankAccountModel();
        BeanUtils.copyProperties(avro,model);
        return model;
    }

    public static Customer fromTo(CustomerModel model){
        Customer customer = new Customer.CustomerBuilder().build();
        BeanUtils.copyProperties(model, customer);
        return customer;
    }
}
