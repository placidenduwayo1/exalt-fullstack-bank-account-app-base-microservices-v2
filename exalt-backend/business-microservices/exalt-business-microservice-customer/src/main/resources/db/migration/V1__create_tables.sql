create table if not exists addresses(
    address_id varchar(255) not null primary key,
    street_num int not null,
    street_name varchar(255) not null,
    po_box int not null,
    city varchar(255) not null,
    country varchar(255) not null
);

create table if not exists customers(
    customer_id varchar(255) not null primary key,
    firstname varchar(255) not null,
    lastname varchar(255) not null,
    state varchar(255) not null,
    email varchar(255) not null unique,
    created_at varchar(255) not null,
    address_id varchar(255) not null ,
    constraint address_fk foreign key (address_id) references addresses(address_id)
);