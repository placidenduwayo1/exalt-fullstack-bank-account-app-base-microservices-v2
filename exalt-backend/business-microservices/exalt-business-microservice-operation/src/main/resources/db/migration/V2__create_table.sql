create table if not exists transfers
(
    transfer_id varchar(255) primary key not null,
    origin varchar(255) not null,
    destination varchar(255) not null,
    mount decimal not null,
    created_at varchar(255) not null
);