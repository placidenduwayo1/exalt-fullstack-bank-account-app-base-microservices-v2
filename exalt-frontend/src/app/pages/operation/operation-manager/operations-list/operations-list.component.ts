import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Operation } from 'src/app/shared/models/operation/operation.model';

@Component({
  selector: 'app-operations-list',
  templateUrl: './operations-list.component.html',
  styleUrls: ['./operations-list.component.css']
})
export class OperationsListComponent {

  @Input() opartionsList!: Operation[];
  @Input() isLoading!: boolean

  getOperationTypeSeverity(type: string): string {
    if (type == 'depot') {
      return 'success'
    }
    else if (type == 'retrait') {
      return 'warning'
    }
    else {
      return 'danger'
    }
  }

  first: number = Number(window.localStorage.getItem('operationsPage'));
  onTablePageChange($event: any){
    this.first = $event.first;
    window.localStorage.setItem('operationsPage',this.first.toString());
  }
}
