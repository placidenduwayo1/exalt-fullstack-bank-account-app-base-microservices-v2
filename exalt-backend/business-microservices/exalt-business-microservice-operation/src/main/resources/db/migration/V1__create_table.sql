create table if not exists operations
(
    operation_id varchar(255) primary key not null,
    type varchar(255) not null,
    mount decimal not null,
    created_at varchar(255)
);