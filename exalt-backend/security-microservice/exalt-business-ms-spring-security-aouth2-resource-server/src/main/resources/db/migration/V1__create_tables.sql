create table if not exists roles_table
(
    role_id
    BIGINT
    PRIMARY
    KEY AUTO_INCREMENT,
    role_name
    VARCHAR
(
    255
) NOT NULL,
    created_date VARCHAR
(
    255
)
    );
create table if not exists users_table
(
    user_id
    BIGINT
    PRIMARY
    KEY AUTO_INCREMENT,
    username
    VARCHAR
(
    255
) NOT NULL,
    pwd VARCHAR
(
    255
) NOT NULL,
    firstname VARCHAR
(
    255
) NOT NULL,
    lastname VARCHAR
(
    255
) NOT NULL,
    email VARCHAR
(
    255
) NOT NULL,
    created_date VARCHAR
(
    255
) NOT NULL
    );