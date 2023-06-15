drop table if exists user_roles;
drop table if exists role;
drop table if exists userz;
drop table if exists formula1_team;

create table formula1_team(
 id int primary key auto_increment,
 name varchar(255) not null,
 year_founded smallint not null,
 championships_won int not null,
 entry_fee_paid boolean not null
);

create table userz(
    username varchar(127) primary key,
    password varchar(511) not null
);

create table role(
  id int primary key auto_increment,
  name varchar(127)
);

create table user_roles(
    user_name varchar(127) not null references userz(username),
    role_id int not null references role(id),
    primary key (user_name, role_id)
);

insert into role values (1, 'user');
insert into role values (2, 'admin');
