create table baby
(
    human_id           int        not null,
    parent_id          int        not null,
    is_breast_milk     tinyint(1) not null,
    is_immunized       tinyint(1) not null,
    stunting_potention int        not null,
    constraint baby_ibfk_1
        foreign key (human_id) references human (id),
    constraint baby_ibfk_2
        foreign key (parent_id) references pregnant_mother (human_id)
);

create index human_id
    on baby (human_id);

create index parent_id
    on baby (parent_id);

create table human
(
    id        int auto_increment
        primary key,
    name      varchar(200) not null,
    birthdate date         not null,
    weight    int          not null,
    height    int          not null
);

create table pregnant_mother
(
    human_id       int        null,
    is_smoking      tinyint(1) not null,
    is_vitamin      tinyint(1) not null,
    recommendation int(1)     not null,
    constraint pregnantmother_ibfk_1
        foreign key (human_id) references human (id)
);

create index human_id
    on pregnant_mother (human_id);

-- auto-generated definition
create table user
(
    id         int auto_increment
        primary key,
    username   varchar(200) not null,
    password   varchar(200) not null,
    role       varchar(50)  not null,
    is_enabled tinyint(1)   not null
);