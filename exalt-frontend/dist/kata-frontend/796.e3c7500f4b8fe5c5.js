"use strict";(self.webpackChunkkata_frontend=self.webpackChunkkata_frontend||[]).push([[796],{7796:(Le,y,l)=>{l.r(y),l.d(y,{CustomerModule:()=>qe});var m=l(6814),g=l(180),e=l(4946),u=l(5219),r=l(8247),p=l(2513),Z=l(2496),f=l(5927),c=l(95);class N{}var q=l(1520),L=l(8445),d=l(6980),h=l(4532),C=l(3714),A=l(707),v=l(6263),I=l(5043),x=l(515),b=l(3904),M=l(3259),k=l(9246),S=l(2352);function F(n,a){if(1&n){const t=e.EpF();e.TgZ(0,"tr")(1,"th"),e._uU(2,"Customer Id"),e._UZ(3,"br"),e.TgZ(4,"span",11),e._UZ(5,"i",12),e.TgZ(6,"input",13),e.NdJ("input",function(i){e.CHM(t),e.oxw();const s=e.MAs(8);return e.KtG(s.filterGlobal(i.target.value,"contains"))}),e.qZA()()(),e.TgZ(7,"th",14),e._uU(8,"Firstname "),e._UZ(9,"p-sortIcon",15),e.qZA(),e.TgZ(10,"th",16),e._uU(11,"Lastname"),e._UZ(12,"p-sortIcon",17)(13,"br"),e.TgZ(14,"span",11),e._UZ(15,"i",12),e.TgZ(16,"input",13),e.NdJ("input",function(i){e.CHM(t),e.oxw();const s=e.MAs(8);return e.KtG(s.filterGlobal(i.target.value,"contains"))}),e.qZA()()(),e.TgZ(17,"th"),e._uU(18,"Email"),e.qZA(),e.TgZ(19,"th"),e._uU(20,"State"),e.qZA(),e.TgZ(21,"th",18),e._uU(22,"Creation date "),e._UZ(23,"p-sortIcon",19),e.qZA(),e.TgZ(24,"th"),e._uU(25,"Address"),e.qZA()()}}function J(n,a){if(1&n){const t=e.EpF();e.TgZ(0,"p-button",24),e.NdJ("onClick",function(){e.CHM(t);const i=e.oxw().$implicit,s=e.oxw();return s.sidebarVisible1=!0,e.KtG(s.loadCustomer(i))}),e.qZA()}2&n&&e.Q6J("rounded",!0)("text",!0)}function O(n,a){if(1&n){const t=e.EpF();e.TgZ(0,"p-button",25),e.NdJ("onClick",function(){e.CHM(t);const i=e.oxw().$implicit,s=e.oxw();return s.sidebarVisible2=!0,e.KtG(s.loadCustomer(i))}),e.qZA()}2&n&&e.Q6J("rounded",!0)("text",!0)}const U=function(){return{width:"15%"}};function D(n,a){if(1&n){const t=e.EpF();e.TgZ(0,"tr")(1,"td"),e._uU(2),e.qZA(),e.TgZ(3,"td"),e._uU(4),e.qZA(),e.TgZ(5,"td"),e._uU(6),e.qZA(),e.TgZ(7,"td"),e._uU(8),e.qZA(),e.TgZ(9,"td"),e._UZ(10,"p-tag",20),e.qZA(),e.TgZ(11,"td"),e._uU(12),e.ALo(13,"date"),e.qZA(),e.TgZ(14,"td"),e._uU(15),e.qZA(),e.TgZ(16,"td"),e.YNc(17,J,1,2,"p-button",21),e.YNc(18,O,1,2,"p-button",22),e.TgZ(19,"p-button",23),e.NdJ("onClick",function(){const s=e.CHM(t).$implicit,_=e.oxw();return e.KtG(_.onCustomerSwitchState(s))}),e.qZA()()()}if(2&n){const t=a.$implicit,o=e.oxw();e.xp6(1),e.Akn(e.DdM(23,U)),e.xp6(1),e.Oqu(t.customerId),e.xp6(2),e.Oqu(t.firstname),e.xp6(2),e.hij(" ",t.lastname,""),e.xp6(2),e.Oqu(t.email),e.xp6(2),e.Q6J("value",t.state)("severity",o.getCustomerStateServerity(t.state)),e.xp6(2),e.Oqu(e.xi3(13,20,t.createdAt,"medium")),e.xp6(2),e.Akn(e.DdM(24,U)),e.xp6(1),e.xDo("",t.address.streetNum," ",t.address.streetName,", ",t.address.poBox," ",t.address.city,", ",t.address.country," "),e.xp6(2),e.Q6J("ngIf","active"==t.state),e.xp6(1),e.Q6J("ngIf","active"==t.state),e.xp6(1),e.Q6J("rounded",!0)("text",!0)}}function Q(n,a){if(1&n&&(e.TgZ(0,"div",26),e._uU(1),e.qZA()),2&n){const t=e.oxw();e.xp6(1),e.hij(" ",t.customers?t.customers.length:0," customers ")}}function H(n,a){if(1&n){const t=e.EpF();e.TgZ(0,"p-card",29)(1,"form",30)(2,"div")(3,"label",31),e._uU(4,"Customer ID"),e.qZA(),e._UZ(5,"br"),e.TgZ(6,"input",32),e.NdJ("ngModelChange",function(i){e.CHM(t);const s=e.oxw(2);return e.KtG(s.selectedCustomer.customerId=i)}),e.qZA()(),e._UZ(7,"br"),e.TgZ(8,"div",33),e._UZ(9,"p-dropdown",34),e.TgZ(10,"label",31),e._uU(11,"select operation type"),e.qZA()(),e._UZ(12,"br"),e.TgZ(13,"div",33),e._UZ(14,"input",35),e.TgZ(15,"label",36),e._uU(16,"input account init mount"),e.qZA()(),e._UZ(17,"br")(18,"br"),e.TgZ(19,"div",37)(20,"p-button",38),e.NdJ("click",function(){e.CHM(t);const i=e.oxw(2);return e.KtG(i.onCreateCustomerAccount())}),e.qZA(),e._uU(21,"\xa0 "),e._UZ(22,"p-button",39),e.qZA()()()}if(2&n){const t=e.oxw(2);e.xp6(1),e.Q6J("formGroup",t.accountFormRequest),e.xp6(5),e.Q6J("ngModel",t.selectedCustomer.customerId),e.xp6(3),e.Q6J("options",t.accountTypes),e.xp6(11),e.Q6J("text",!0)("raised",!0)("disabled",t.accountFormRequest.invalid),e.xp6(2),e.Q6J("text",!0)("raised",!0)}}const T=function(){return{width:"fit-content"}};function R(n,a){if(1&n){const t=e.EpF();e.TgZ(0,"p-sidebar",27),e.NdJ("visibleChange",function(i){e.CHM(t);const s=e.oxw();return e.KtG(s.sidebarVisible1=i)}),e.YNc(1,H,23,8,"p-card",28),e.qZA()}if(2&n){const t=e.oxw();e.Akn(e.DdM(5,T)),e.Q6J("visible",t.sidebarVisible1)("position",t.sidebarPosition),e.xp6(1),e.Q6J("ngIf",t.selectedCustomer)}}function z(n,a){if(1&n){const t=e.EpF();e.TgZ(0,"p-card",45)(1,"div",46)(2,"div",47)(3,"div")(4,"label",48),e._uU(5,"Firstname"),e.qZA(),e._UZ(6,"br"),e.TgZ(7,"input",49),e.NdJ("ngModelChange",function(i){e.CHM(t);const s=e.oxw(2);return e.KtG(s.selectedCustomer.firstname=i)}),e.qZA()(),e.TgZ(8,"div")(9,"label",50),e._uU(10,"Lastname"),e.qZA(),e._UZ(11,"br"),e.TgZ(12,"input",51),e.NdJ("ngModelChange",function(i){e.CHM(t);const s=e.oxw(2);return e.KtG(s.selectedCustomer.lastname=i)}),e.qZA()(),e.TgZ(13,"div")(14,"label",52),e._uU(15,"Email"),e.qZA(),e._UZ(16,"br"),e.TgZ(17,"input",53),e.NdJ("ngModelChange",function(i){e.CHM(t);const s=e.oxw(2);return e.KtG(s.selectedCustomer.email=i)}),e.qZA()()()()()}if(2&n){const t=e.oxw(2);e.xp6(7),e.Q6J("ngModel",t.selectedCustomer.firstname),e.xp6(5),e.Q6J("ngModel",t.selectedCustomer.lastname),e.xp6(5),e.Q6J("ngModel",t.selectedCustomer.email)}}function j(n,a){if(1&n){const t=e.EpF();e.TgZ(0,"p-card",54)(1,"div",46)(2,"div",55)(3,"div")(4,"label",56),e._uU(5,"street number"),e.qZA(),e._UZ(6,"br"),e.TgZ(7,"input",57),e.NdJ("ngModelChange",function(i){e.CHM(t);const s=e.oxw(2);return e.KtG(s.selectedCustomer.address.streetNum=i)}),e.qZA()(),e.TgZ(8,"div")(9,"label",58),e._uU(10,"street name"),e.qZA(),e._UZ(11,"br"),e.TgZ(12,"input",59),e.NdJ("ngModelChange",function(i){e.CHM(t);const s=e.oxw(2);return e.KtG(s.selectedCustomer.address.streetName=i)}),e.qZA()(),e.TgZ(13,"div")(14,"label",60),e._uU(15,"po box"),e.qZA(),e._UZ(16,"br"),e.TgZ(17,"input",61),e.NdJ("ngModelChange",function(i){e.CHM(t);const s=e.oxw(2);return e.KtG(s.selectedCustomer.address.poBox=i)}),e.qZA()(),e.TgZ(18,"div")(19,"label",62),e._uU(20,"city name"),e.qZA(),e._UZ(21,"br"),e.TgZ(22,"input",63),e.NdJ("ngModelChange",function(i){e.CHM(t);const s=e.oxw(2);return e.KtG(s.selectedCustomer.address.city=i)}),e.qZA()(),e.TgZ(23,"div")(24,"label",64),e._uU(25,"country name"),e.qZA(),e._UZ(26,"br"),e.TgZ(27,"input",65),e.NdJ("ngModelChange",function(i){e.CHM(t);const s=e.oxw(2);return e.KtG(s.selectedCustomer.address.country=i)}),e.qZA()()()()()}if(2&n){const t=e.oxw(2);e.xp6(7),e.Q6J("ngModel",t.selectedCustomer.address.streetNum),e.xp6(5),e.Q6J("ngModel",t.selectedCustomer.address.streetName),e.xp6(5),e.Q6J("ngModel",t.selectedCustomer.address.poBox),e.xp6(5),e.Q6J("ngModel",t.selectedCustomer.address.city),e.xp6(5),e.Q6J("ngModel",t.selectedCustomer.address.country)}}const V=function(){return{height:"fit-content"}};function G(n,a){if(1&n){const t=e.EpF();e.TgZ(0,"p-sidebar",27),e.NdJ("visibleChange",function(i){e.CHM(t);const s=e.oxw();return e.KtG(s.sidebarVisible2=i)}),e.TgZ(1,"p-card",40)(2,"form",30)(3,"p-splitter",41),e.YNc(4,z,18,3,"ng-template",42),e.YNc(5,j,28,5,"ng-template",42),e.qZA(),e.TgZ(6,"div",43)(7,"p-button",44),e.NdJ("onClick",function(){e.CHM(t);const i=e.oxw();return e.KtG(i.onUpdateCustomer())}),e.qZA(),e._uU(8,"\xa0 "),e._UZ(9,"p-button",39),e.qZA()()()()}if(2&n){const t=e.oxw();e.Akn(e.DdM(12,T)),e.Q6J("visible",t.sidebarVisible2)("position",t.sidebarPosition),e.xp6(2),e.Q6J("formGroup",t.customerRequest),e.xp6(1),e.Akn(e.DdM(13,V)),e.xp6(4),e.Q6J("text",!0)("raised",!0)("disabled",t.customerRequest.invalid),e.xp6(2),e.Q6J("text",!0)("raised",!0)}}const K=function(){return{width:"20vw"}},B=function(){return[10,20,30,50]},Y=function(){return["customerId","lastname","firstname","createdAt"]};let $=(()=>{class n{constructor(){this.router=(0,e.f3M)(g.F0),this.accountTypes=[{type:"current"},{type:"saving"}],this.sidebarVisible1=!1,this.sidebarVisible2=!1,this.sidebarPosition="right",this.fb=(0,e.f3M)(c.qu),this.confirmSevice=(0,e.f3M)(u.YP),this.messageService=(0,e.f3M)(u.ez),this.bankAccountService=(0,e.f3M)(L.A),this.customerService=(0,e.f3M)(Z.v),this.customerEventService=(0,e.f3M)(f.VY),this.bankAccountEventService=(0,e.f3M)(f.as),this.minLen=2,this.min=1,this.isCreating=!0,this.first=Number(localStorage.getItem("customerPage"))}getCustomerStateServerity(t){return"archive"==t?"danger":"active"==t?"success":"danger"}ngOnInit(){this.accountFormRequest=this.fb.group({customer:[{value:"",disabled:!0}],type:["",c.kI.required],balance:["",c.kI.required]}),this.customerRequest=this.fb.group({customer:this.fb.group({firstname:["",[c.kI.required,c.kI.minLength(this.minLen)]],lastname:["",[c.kI.required,c.kI.minLength(this.minLen)]],email:["",[c.kI.required,c.kI.minLength(4)]]}),address:this.fb.group({streetNum:["",[c.kI.required,c.kI.min(this.min)]],streetName:["",[c.kI.required,c.kI.minLength(this.minLen)]],poBox:["",[c.kI.required,c.kI.min(this.min)]],city:["",[c.kI.required,c.kI.minLength(this.minLen)]],country:["",[c.kI.required,c.kI.minLength(this.minLen)]]})}),this.bankAccountEventService.bankAccountEventObservable.subscribe({next:t=>{t===p.uQ.BANK_ACCOUNT_CREATE&&this.confirmSevice.confirm({acceptLabel:r.Z7,rejectLabel:r.QK,message:r.mt,accept:()=>{let o=this.accountFormRequest.value;o.customerId=this.selectedCustomer.customerId,o.type=this.accountFormRequest.get("type")?.value.type,setTimeout(()=>{this.bankAccountService.create(o).subscribe({next:i=>(console.log(i),this.messageService.add({key:"key1",severity:r.Am,detail:r.n1,sticky:!0}),this.bankAccountEventService.publishEvent(p.uQ.REFRESH),i),error:()=>(this.messageService.add({key:"key2",severity:r.aO,detail:r.Ti,sticky:!0}),null),complete:()=>{console.log(r.DI)}}),this.isCreating=!1},r.Vs)},reject:()=>{this.messageService.add({key:"key2",severity:r.aO,detail:r.IH,sticky:!0})}})}}),this.customerEventService.customerEventObservable.subscribe({next:t=>{switch(t){case p.F9.CUSTOMER_STATE_SWITCH:this.confirmSevice.confirm({acceptLabel:r.Z7,rejectLabel:r.QK,message:r.mt,accept:()=>{let o=new N;o.customerId=this.selectedCustomer.customerId,"active"==this.selectedCustomer.state?(o.state="archive",this.customerService.switchState(o).subscribe({next:i=>(this.messageService.add({key:"key1",severity:r.Am,detail:r.n1,sticky:!0}),this.customerEventService.publishEvent(p.F9.REFRESH),i)})):"archive"==this.selectedCustomer.state&&(o.state="active",this.customerService.switchState(o).subscribe({next:i=>(this.messageService.add({key:"key1",severity:r.Am,detail:r.n1,sticky:!0}),this.customerEventService.publishEvent(p.F9.REFRESH),i)}))},reject:()=>(this.messageService.add({key:"key2",severity:r.aO,detail:r.IH,sticky:!0}),null)});break;case p.F9.CUSTOMER_UPDATE:this.confirmSevice.confirm({acceptLabel:r.Z7,rejectLabel:r.QK,message:r.mt,accept:()=>{let o=new q.c;o.addressDto=this.customerRequest.value.address,o.customerDto=this.customerRequest.value.customer,this.customerService.updateCustomer(this.selectedCustomer.customerId,o).subscribe({next:i=>(this.messageService.add({key:"key1",severity:r.Am,detail:r.n1,sticky:!0}),this.customerEventService.publishEvent(p.F9.REFRESH),i),error:()=>(this.messageService.add({key:"key2",severity:r.aO,detail:r.Ti,sticky:!0}),null),complete:()=>{console.log(r.DI)}})},reject:()=>{this.messageService.add({key:"key2",severity:r.aO,detail:r.IH,sticky:!0})}})}},error:()=>(this.messageService.add({key:"key2",severity:r.aO,detail:r.Ti,sticky:r.CA}),null),complete:()=>{console.log(r.DI)}})}loadCustomer(t){this.selectedCustomer=t}onCreateCustomerAccount(){this.bankAccountEventService.publishEvent(p.uQ.BANK_ACCOUNT_CREATE)}onCustomerSwitchState(t){this.selectedCustomer=t,this.customerEventService.publishEvent(p.F9.CUSTOMER_STATE_SWITCH)}onUpdateCustomer(){this.customerEventService.publishEvent(p.F9.CUSTOMER_UPDATE)}onTablePageChange(t){this.first=t.first,localStorage.setItem("customerPage",this.first.toString())}static#e=this.\u0275fac=function(o){return new(o||n)};static#t=this.\u0275cmp=e.Xpm({type:n,selectors:[["app-customers-list"]],inputs:{customers:"customers",isLoading:"isLoading"},decls:14,vars:17,consts:[[2,"width","80%"],[2,"color","green","text-align","center"],["key","key1"],["key","key2"],["loadingIcon","pi pi-star pi-spin",3,"value","paginator","rows","rowsPerPageOptions","globalFilterFields","loading","first","onPage"],["dt",""],["pTemplate","header"],["pTemplate","body"],["pTemplate","summary"],[3,"visible","position","style","visibleChange",4,"ngIf"],[3,"visible","style","position","visibleChange",4,"ngIf"],[1,"p-input-icon-left","ml-auto"],[1,"pi","pi-search"],["pInputText","","placeholder","search","size","5",3,"input"],["pSortableColumn","firstname"],["field","firstname"],["pSortableColumn","lastname"],["field","lastname"],["pSortableColumn","createdAt"],["field","createdAt"],[3,"value","severity"],["icon","pi pi-window-maximize","size","small","severity","success","pTooltip","create account","tooltipPosition","left",3,"rounded","text","onClick",4,"ngIf"],["icon","pi pi-window-maximize","size","small","severity","info","pTooltip","edit customer","tooltipPosition","left",3,"rounded","text","onClick",4,"ngIf"],["icon","pi pi-pencil","size","small","severity","danger","pTooltip","active/archive","tooltipPosition","left",3,"rounded","text","onClick"],["icon","pi pi-window-maximize","size","small","severity","success","pTooltip","create account","tooltipPosition","left",3,"rounded","text","onClick"],["icon","pi pi-window-maximize","size","small","severity","info","pTooltip","edit customer","tooltipPosition","left",3,"rounded","text","onClick"],[1,"flex","align-items-center","justify-content-between"],[3,"visible","position","visibleChange"],["header","Create Bank Account",4,"ngIf"],["header","Create Bank Account"],[3,"formGroup"],["for","type"],["type","text","pInputText","","formControlName","customer","id","customer",3,"ngModel","ngModelChange"],[1,"p-float-label"],["optionLabel","type","placeholder","Select an account type","formControlName","type",3,"options"],["type","number","formControlName","balance","id","balance","pInputText",""],["for","balance"],[1,"buttons"],["label","Create",3,"text","raised","disabled","click"],["label","Cancel","severity","danger",3,"text","raised"],["header","Edit customer"],["styleClass","mb-5"],["pTemplate",""],[2,"margin-left","30%","margin-top","2%"],["label","Save","severity","primary",3,"text","raised","disabled","onClick"],["header","customer"],[1,"col","flex","align-items-center","justify-content-center"],["formGroupName","customer"],["for","firstname"],["pInputText","","type","text","id","firstname","formControlName","firstname",1,"form-control",3,"ngModel","ngModelChange"],["for","lastname"],["pInputText","","type","text","id","lastname","formControlName","lastname",1,"form-control",3,"ngModel","ngModelChange"],["for","email"],["pInputText","","type","text","id","email","formControlName","email",1,"form-control",3,"ngModel","ngModelChange"],["header","address"],["formGroupName","address"],["for","streetNum"],["pInputText","","type","number","id","streetNum","formControlName","streetNum",1,"form-control",3,"ngModel","ngModelChange"],["for","streetName"],["pInputText","","type","text","id","streetName","formControlName","streetName",1,"form-control",3,"ngModel","ngModelChange"],["for","poBox"],["pInputText","","type","number","id","poBox","formControlName","poBox",1,"form-control",3,"ngModel","ngModelChange"],["for","city"],["pInputText","","type","text","id","city","formControlName","city",1,"form-control",3,"ngModel","ngModelChange"],["for","country"],["pInputText","","type","text","id","country","formControlName","country",1,"form-control",3,"ngModel","ngModelChange"]],template:function(o,i){1&o&&(e.TgZ(0,"div",0)(1,"h1",1),e._uU(2," customers and their addresses "),e.qZA()(),e.TgZ(3,"p-card"),e._UZ(4,"p-messages",2)(5,"p-messages",3)(6,"p-confirmDialog"),e.TgZ(7,"p-table",4,5),e.NdJ("onPage",function(_){return i.onTablePageChange(_)}),e.YNc(9,F,26,0,"ng-template",6),e.YNc(10,D,20,25,"ng-template",7),e.YNc(11,Q,2,1,"ng-template",8),e.qZA()(),e.YNc(12,R,2,6,"p-sidebar",9),e.YNc(13,G,10,14,"p-sidebar",10)),2&o&&(e.xp6(3),e.Akn(e.DdM(13,T)),e.xp6(3),e.Akn(e.DdM(14,K)),e.xp6(1),e.Q6J("value",i.customers)("paginator",!0)("rows",10)("rowsPerPageOptions",e.DdM(15,B))("globalFilterFields",e.DdM(16,Y))("loading",i.isLoading)("first",i.first),e.xp6(5),e.Q6J("ngIf",i.selectedCustomer),e.xp6(1),e.Q6J("ngIf",i.selectedCustomer))},dependencies:[m.O5,d.iA,u.jx,d.lQ,d.fz,c._Y,c.Fj,c.wV,c.JJ,c.JL,c.sg,c.u,c.x0,h.Z,C.o,A.zx,v.V,I.q,x.V,b.Q,M.u,k.Y,S.Lt,m.uU]})}return n})(),W=(()=>{class n{constructor(){this.activatedRoute=(0,e.f3M)(g.gz),this.customers=[],this.customerEventService=(0,e.f3M)(f.VY),this.customerService=(0,e.f3M)(Z.v),this.isLoading=!0,this.msgService=(0,e.f3M)(u.ez)}ngOnInit(){setTimeout(()=>{this.activatedRoute.data.subscribe({next:({allCustomers:t})=>{this.customers=t,console.log(this.customers)},error:t=>(this.msgService.add({key:"key2",severity:r.aO,detail:r.Ti,sticky:r.CA}),null),complete:()=>{console.log(r.DI)}}),this.isLoading=!1},r.Vs),this.customerEventService.customerEventObservable.subscribe({next:t=>{t==p.F9.REFRESH&&this.customerService.getAll().subscribe({next:o=>{this.customers=o},error:o=>{alert(`an error occured ${o}`)},complete:()=>{console.log("obserable completed")}})}})}static#e=this.\u0275fac=function(o){return new(o||n)};static#t=this.\u0275cmp=e.Xpm({type:n,selectors:[["app-customer-manager"]],decls:1,vars:2,consts:[[3,"customers","isLoading"]],template:function(o,i){1&o&&e._UZ(0,"app-customers-list",0),2&o&&e.Q6J("customers",i.customers)("isLoading",i.isLoading)},dependencies:[$]})}return n})();var w=l(6556);function X(n,a){1&n&&e._UZ(0,"div")}function ee(n,a){1&n&&(e.TgZ(0,"div",3)(1,"h1",4),e._uU(2," No archived customers "),e.qZA()())}function te(n,a){if(1&n){const t=e.EpF();e.TgZ(0,"tr")(1,"th"),e._uU(2,"Customer Id"),e._UZ(3,"br"),e.TgZ(4,"span",13),e._UZ(5,"i",14),e.TgZ(6,"input",15),e.NdJ("input",function(i){e.CHM(t),e.oxw();const s=e.MAs(8);return e.KtG(s.filterGlobal(i.target.value,"contains"))}),e.qZA()()(),e.TgZ(7,"th",16),e._uU(8,"Firstname "),e._UZ(9,"p-sortIcon",17),e.qZA(),e.TgZ(10,"th"),e._uU(11,"Lastname"),e._UZ(12,"br"),e.TgZ(13,"span",13),e._UZ(14,"i",14),e.TgZ(15,"input",15),e.NdJ("input",function(i){e.CHM(t),e.oxw();const s=e.MAs(8);return e.KtG(s.filterGlobal(i.target.value,"contains"))}),e.qZA()()(),e.TgZ(16,"th"),e._uU(17,"Email"),e.qZA(),e.TgZ(18,"th"),e._uU(19,"State"),e.qZA(),e.TgZ(20,"th",18),e._uU(21,"Creation date "),e._UZ(22,"p-sortIcon",19),e.qZA(),e.TgZ(23,"th"),e._uU(24,"Address"),e.qZA()()}}const E=function(){return{width:"15%"}};function ne(n,a){if(1&n&&(e.TgZ(0,"tr")(1,"td"),e._uU(2),e.qZA(),e.TgZ(3,"td"),e._uU(4),e.qZA(),e.TgZ(5,"td"),e._uU(6),e.qZA(),e.TgZ(7,"td"),e._uU(8),e.qZA(),e.TgZ(9,"td"),e._UZ(10,"p-tag",20),e.qZA(),e.TgZ(11,"td"),e._uU(12),e.ALo(13,"date"),e.qZA(),e.TgZ(14,"td"),e._uU(15),e.qZA()()),2&n){const t=a.$implicit,o=e.oxw(2);e.xp6(1),e.Akn(e.DdM(19,E)),e.xp6(1),e.Oqu(t.customerId),e.xp6(2),e.Oqu(t.firstname),e.xp6(2),e.hij(" ",t.lastname,""),e.xp6(2),e.Oqu(t.email),e.xp6(2),e.Q6J("value",t.state)("severity",o.getCustomerStateServerity(t.state)),e.xp6(2),e.Oqu(e.xi3(13,16,t.createdAt,"medium")),e.xp6(2),e.Akn(e.DdM(20,E)),e.xp6(1),e.xDo("",t.address.streetNum," ",t.address.streetName,", ",t.address.poBox," ",t.address.city,", ",t.address.country," ")}}function ie(n,a){if(1&n&&(e.TgZ(0,"div",21),e._uU(1),e.qZA()),2&n){const t=e.oxw(2);e.xp6(1),e.hij(" ",t.archivedCustomers?t.archivedCustomers.length:0," customers ")}}const oe=function(){return{width:"fit-content"}},se=function(){return{width:"20vw"}},re=function(){return[10,20,30,50]},ae=function(){return["customerId","lastname","firstname","createdAt"]};function le(n,a){if(1&n&&(e.TgZ(0,"div",3)(1,"h1",5),e._uU(2," archived customers "),e.qZA()(),e.TgZ(3,"p-card"),e._UZ(4,"p-messages",6)(5,"p-messages",7)(6,"p-confirmDialog"),e.TgZ(7,"p-table",8,9),e.YNc(9,te,25,0,"ng-template",10),e.YNc(10,ne,16,21,"ng-template",11),e.YNc(11,ie,2,1,"ng-template",12),e.qZA()()),2&n){const t=e.oxw();e.xp6(3),e.Akn(e.DdM(10,oe)),e.xp6(3),e.Akn(e.DdM(11,se)),e.xp6(1),e.Q6J("value",t.archivedCustomers)("paginator",!0)("rows",10)("rowsPerPageOptions",e.DdM(12,re))("globalFilterFields",e.DdM(13,ae))("loading",t.isLoading)}}const pe=[{path:"",component:W,resolve:{allCustomers:w.tW}},{path:"archived",component:(()=>{class n{constructor(){this.activatedRoute=(0,e.f3M)(g.gz),this.isLoading=!0,this.msgService=(0,e.f3M)(u.ez),this.tableIsmpty=!1}ngOnInit(){setTimeout(()=>{this.activatedRoute.data.subscribe({next:({allArchivedCustomers:t})=>(this.archivedCustomers=t,0==this.archivedCustomers.length&&(this.tableIsmpty=!0),this.archivedCustomers),error:()=>(this.msgService.add({key:"key2",severity:r.aO,detail:r.Ti,sticky:r.CA}),null),complete:()=>{console.log(r.DI)}}),this.isLoading=!1},r.Vs)}getCustomerStateServerity(t){return"danger"}static#e=this.\u0275fac=function(o){return new(o||n)};static#t=this.\u0275cmp=e.Xpm({type:n,selectors:[["app-archived-customer"]],decls:5,vars:3,consts:[[4,"ngIf","ngIfThen","ngIfElse"],["tableEmpty",""],["tableNotEmpty",""],[2,"width","80%"],[2,"color","green","text-align","center"],[2,"color","red","text-align","center"],["key","key1"],["key","key2"],["loadingIcon","pi pi-star pi-spin",3,"value","paginator","rows","rowsPerPageOptions","globalFilterFields","loading"],["dt",""],["pTemplate","header"],["pTemplate","body"],["pTemplate","summary"],[1,"p-input-icon-left","ml-auto"],[1,"pi","pi-search"],["pInputText","","placeholder","search","size","5",3,"input"],["pSortableColumn","firstname"],["field","firstname"],["pSortableColumn","createdAt"],["field","createdAt"],[3,"value","severity"],[1,"flex","align-items-center","justify-content-between"]],template:function(o,i){if(1&o&&(e.YNc(0,X,1,0,"div",0),e.YNc(1,ee,3,0,"ng-template",null,1,e.W1O),e.YNc(3,le,12,14,"ng-template",null,2,e.W1O)),2&o){const s=e.MAs(2),_=e.MAs(4);e.Q6J("ngIf",i.tableIsmpty)("ngIfThen",s)("ngIfElse",_)}},dependencies:[m.O5,d.iA,u.jx,d.lQ,d.fz,h.Z,C.o,v.V,x.V,b.Q,m.uU]})}return n})(),resolve:{allArchivedCustomers:w.Fq}}];let ue=(()=>{class n{static#e=this.\u0275fac=function(o){return new(o||n)};static#t=this.\u0275mod=e.oAB({type:n});static#n=this.\u0275inj=e.cJS({imports:[g.Bz.forChild(pe),g.Bz]})}return n})();var me=l(6128),P=l(4713);let de=(()=>{class n extends P.s{static \u0275fac=function(){let t;return function(i){return(t||(t=e.n5z(n)))(i||n)}}();static \u0275cmp=e.Xpm({type:n,selectors:[["MinusIcon"]],standalone:!0,features:[e.qOj,e.jDz],decls:2,vars:5,consts:[["width","14","height","14","viewBox","0 0 14 14","fill","none","xmlns","http://www.w3.org/2000/svg"],["d","M13.2222 7.77778H0.777778C0.571498 7.77778 0.373667 7.69584 0.227806 7.54998C0.0819442 7.40412 0 7.20629 0 7.00001C0 6.79373 0.0819442 6.5959 0.227806 6.45003C0.373667 6.30417 0.571498 6.22223 0.777778 6.22223H13.2222C13.4285 6.22223 13.6263 6.30417 13.7722 6.45003C13.9181 6.5959 14 6.79373 14 7.00001C14 7.20629 13.9181 7.40412 13.7722 7.54998C13.6263 7.69584 13.4285 7.77778 13.2222 7.77778Z","fill","currentColor"]],template:function(o,i){1&o&&(e.O4$(),e.TgZ(0,"svg",0),e._UZ(1,"path",1),e.qZA()),2&o&&(e.Tol(i.getClassNames()),e.uIk("aria-label",i.ariaLabel)("aria-hidden",i.ariaHidden)("role",i.role))},dependencies:[m.ez],encapsulation:2})}return n})();var _e=l(2332);let ge=(()=>{class n extends P.s{pathId;ngOnInit(){this.pathId="url(#"+(0,_e.Th)()+")"}static \u0275fac=function(){let t;return function(i){return(t||(t=e.n5z(n)))(i||n)}}();static \u0275cmp=e.Xpm({type:n,selectors:[["PlusIcon"]],standalone:!0,features:[e.qOj,e.jDz],decls:6,vars:7,consts:[["width","14","height","14","viewBox","0 0 14 14","fill","none","xmlns","http://www.w3.org/2000/svg"],["d","M7.67742 6.32258V0.677419C7.67742 0.497757 7.60605 0.325452 7.47901 0.198411C7.35197 0.0713707 7.17966 0 7 0C6.82034 0 6.64803 0.0713707 6.52099 0.198411C6.39395 0.325452 6.32258 0.497757 6.32258 0.677419V6.32258H0.677419C0.497757 6.32258 0.325452 6.39395 0.198411 6.52099C0.0713707 6.64803 0 6.82034 0 7C0 7.17966 0.0713707 7.35197 0.198411 7.47901C0.325452 7.60605 0.497757 7.67742 0.677419 7.67742H6.32258V13.3226C6.32492 13.5015 6.39704 13.6725 6.52358 13.799C6.65012 13.9255 6.82106 13.9977 7 14C7.17966 14 7.35197 13.9286 7.47901 13.8016C7.60605 13.6745 7.67742 13.5022 7.67742 13.3226V7.67742H13.3226C13.5022 7.67742 13.6745 7.60605 13.8016 7.47901C13.9286 7.35197 14 7.17966 14 7C13.9977 6.82106 13.9255 6.65012 13.799 6.52358C13.6725 6.39704 13.5015 6.32492 13.3226 6.32258H7.67742Z","fill","currentColor"],[3,"id"],["width","14","height","14","fill","white"]],template:function(o,i){1&o&&(e.O4$(),e.TgZ(0,"svg",0)(1,"g"),e._UZ(2,"path",1),e.qZA(),e.TgZ(3,"defs")(4,"clipPath",2),e._UZ(5,"rect",3),e.qZA()()()),2&o&&(e.Tol(i.getClassNames()),e.uIk("aria-label",i.ariaLabel)("aria-hidden",i.ariaHidden)("role",i.role),e.xp6(1),e.uIk("clip-path",i.pathId),e.xp6(3),e.Q6J("id",i.pathId))},encapsulation:2})}return n})();var fe=l(4480);let Ne=(()=>{class n{static \u0275fac=function(o){return new(o||n)};static \u0275mod=e.oAB({type:n});static \u0275inj=e.cJS({imports:[m.ez,u.m8,fe.T,ge,de,u.m8]})}return n})(),qe=(()=>{class n{static#e=this.\u0275fac=function(o){return new(o||n)};static#t=this.\u0275mod=e.oAB({type:n});static#n=this.\u0275inj=e.cJS({providers:[u.YP,u.ez],imports:[m.ez,ue,d.U$,me.Qy,c.u5,c.UX,h.d,C.j,Ne,A.hJ,v.W,I.t,x.$,b.D,M.z,k.l,S.kW]})}return n})()}}]);