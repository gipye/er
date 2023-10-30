create table User (
    id bigint not null auto_increment,
    createDate datetime(6) not null,
    password varchar(100) not null,
    role integer not null,
    username varchar(10) not null unique,
    primary key (id)
);

create table HospitalUser (
    id bigint not null,
    address varchar(30) not null unique,
    latitude double precision,
    longitude double precision,
    name varchar(20) not null,
    numberOfBed integer not null,
    telephone varchar(20) not null unique,
    primary key (id)
);

create table OrdinaryUser (
    id bigint not null,
    address varchar(30) not null unique,
    latitude double precision,
    longitude double precision,
    name varchar(20) not null,
    telephone varchar(20) not null unique,
    primary key (id)
);

create table HospitalDepartment (
    id bigint not null auto_increment,
    isRunning bit not null,
    name varchar(10) not null,
    hospital_id bigint,
    primary key (id)
);

alter table OrdinaryUser add constraint FK_OrdinaryUser_TO_User foreign key(id) references User(id);
alter table HospitalUser add constraint FK_HospitalUser_TO_User foreign key(id) references User(id);
alter table HospitalDepartment add constraint FK_HospitalDepartment_TO_HospitalUser foreign key(hospital_id) references HospitalUser(id);