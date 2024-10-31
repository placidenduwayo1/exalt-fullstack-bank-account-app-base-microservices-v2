package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.web;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.ports.input.InputBankAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountInterestRateDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountOverdraftDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountSwitchStatedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api-bank-account")
@RequiredArgsConstructor
public class BankAccountController {
    //input adapter
    private final InputBankAccountService inputBankAccountService;

    @PostMapping(value = "/accounts")
    public BankAccount createAccount(@RequestBody BankAccountDto bankAccountDto) {
        return inputBankAccountService.createAccountKafkaEvent(bankAccountDto);
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<Collection<BankAccount>> getAllAccounts() {
        return new ResponseEntity<>(inputBankAccountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping(value = "/accounts/{accountId}")
    public BankAccount getAccount(@PathVariable(name = "accountId") String accountId){
        return inputBankAccountService.getAccount(accountId);
    }

    @GetMapping(value = "/customers/{customerId}/accounts")
    public ResponseEntity<Collection<BankAccount>> getAccountOfGivenCustomer(@PathVariable(name = "customerId") String customerId) {
        return new ResponseEntity<>(inputBankAccountService.getAccountOfGivenCustomer(customerId), HttpStatus.OK);
    }

    @PutMapping(value = "/accounts/{accountId}")
    public BankAccount updateAccount(@PathVariable(name = "accountId") String accountId, @RequestBody BankAccountDto dto) {
        return inputBankAccountService.updateAccount(accountId, dto);
    }

    @PostMapping(value = "/accounts/switch-state")
    public BankAccount switchAccountState(@RequestBody BankAccountSwitchStatedDto dto) {
        return inputBankAccountService.switchAccountState(dto);
    }

    @PostMapping(value = "/accounts/overdraft")
    public BankAccount changeOverdraft(@RequestBody BankAccountOverdraftDto dto) {
        return inputBankAccountService.changeOverdraft(dto);
    }

    @PostMapping(value = "/accounts/interest-rate")
    public BankAccount changeInterestRate(@RequestBody BankAccountInterestRateDto dto) {
        return inputBankAccountService.changeInterestRate(dto);
    }
    @GetMapping(value = "/accounts/suspended")
    public ResponseEntity<Collection<BankAccount>> getAllSuspendedAccounts(){
        return new ResponseEntity<>(inputBankAccountService.getAllSuspendedAccounts(), HttpStatus.OK);
    }
    @GetMapping(value = "/accounts/to-risk")
    public ResponseEntity<Collection<BankAccount>> getAllRiskAccounts(){
        return new ResponseEntity<>(inputBankAccountService.getAllRiskAccounts(), HttpStatus.OK);
    }
}
