import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-menu-accueil',
  templateUrl: './menu-accueil.component.html',
  styleUrls: ['./menu-accueil.component.css']
})
export class MenuAccueilComponent implements OnInit {
  menu!: MenuItem[];
  router = inject(Router);

  ngOnInit(): void {
    this.menu = [
      {
        label: 'Home', icon: 'pi pi-fw pi-home',
        items: [
          {
            label: '<-k8s',
            command: () => this.router.navigateByUrl('/index1'),
          },
          {
            label: '->k8s - <-ingress',
            command: () => this.router.navigateByUrl('/index2'),
          },
          {
            label: '->ingress',
            command: () => this.router.navigateByUrl("/index3")
          }
        ]
      },
      {
        label: 'Login',
        command:()=> {
          this.router.navigateByUrl("/login");
        },
      }
    ];
  }
}
