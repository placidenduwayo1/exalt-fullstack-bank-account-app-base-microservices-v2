package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.web;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.entities.TransferOperation;
import fr.exalt.businessmicroserviceoperation.domain.ports.input.InputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.dtos.OperationDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.dtos.TransferDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(value = "/api-operation")
@AllArgsConstructor
public class OperationController {
    // adapter inputOperationService comme interface entre le domain et l'entrée extérieure
    private final InputOperationService inputOperationService;

    @PostMapping(value = "/operations")
    public ResponseEntity<Operation> createOperation(@RequestBody OperationDto dto){
        return new ResponseEntity<>(inputOperationService.createOperation(dto), HttpStatus.OK);
    }

    @GetMapping(value = "/operations")
    public ResponseEntity<Collection<Operation>> getAllOperations() {
        return new ResponseEntity<>(inputOperationService.getAllOperations(), HttpStatus.OK);
    }
    @GetMapping(value = "/accounts/{accountId}/operations")
    public ResponseEntity<Collection<Operation>> getAccountOperations(@PathVariable(name = "accountId") String accountId){
        return new ResponseEntity<>(inputOperationService.getAccountOperations(accountId), HttpStatus.OK);
    }
    @PostMapping(value = "/operations/transfer")
    public Map<String, BankAccount> transferBetweenAccounts(@RequestBody TransferDto dto){
        return inputOperationService.transferBetweenAccounts(dto);
    }
    @GetMapping(value = "/operations/transfers")
    public ResponseEntity<Collection<TransferOperation>> getAllTransfer(){
        return new ResponseEntity<>(inputOperationService.getAllTransfer(), HttpStatus.OK);
    }
    @GetMapping(value = "/operations/deposit")
    public ResponseEntity<Collection<Operation>> getAllDepositOperation(){
        return new ResponseEntity<>(inputOperationService.getAllDepositOperation(), HttpStatus.OK);
    }
    @GetMapping(value = "/operations/withdrawals")
    public ResponseEntity<Collection<Operation>> getAllWithdrawalsOperation(){
        return new ResponseEntity<>(inputOperationService.getAllWithdrawalsOperation(), HttpStatus.OK);
    }
}
