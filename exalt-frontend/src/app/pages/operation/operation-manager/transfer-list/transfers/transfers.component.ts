import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-transfers',
  templateUrl: './transfers.component.html',
  styleUrls: ['./transfers.component.css']
})
export class TransfersComponent {
  @Input() transfers!: any[];
  @Input() isLoading!: boolean;

  getAccountTypeSeverity(type: string): string {
    if (type == 'saving') {
      return 'success'
    }
    else if (type == 'current') {
      return 'primary'
    }
    else return 'danger'
  }
  first: number = Number(window.localStorage.getItem('transfersPage'))
  onTablePageChange($event: any) {
    this.first= $event.first;
    window.localStorage.setItem('transfersPage', this.first.toString());
  }
}
