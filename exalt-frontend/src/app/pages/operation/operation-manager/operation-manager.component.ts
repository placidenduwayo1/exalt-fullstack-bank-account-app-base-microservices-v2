import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { errorMsg, observableComplete, severity2, sticky, timeout } from 'src/app/const-variables';
import { Operation } from 'src/app/shared/models/operation/operation.model';

@Component({
  selector: 'app-operation-manager',
  templateUrl: './operation-manager.component.html',
  styleUrls: ['./operation-manager.component.css']
})
export class OperationManagerComponent implements OnInit {

  operations!: Operation[];
  activatedRoute = inject(ActivatedRoute);
  msgService = inject(MessageService);
  isLoading: boolean = true;

  ngOnInit(): void {

    setTimeout(()=>{
      this.activatedRoute.data.subscribe({
        next: ({ allOperations }) => {
          this.operations = allOperations;
          return this.operations;
        },
        error: () => {
          this.msgService.add({
            key: 'key2',
            severity: severity2,
            detail: errorMsg,
            sticky: sticky
          });
          return null;
        },
        complete: () => {
          console.log(observableComplete);
        }
      });
      this.isLoading = false;
    },timeout)
  }
}
