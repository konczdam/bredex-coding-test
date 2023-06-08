create table formula1_team(
 id int primary key,
 name varchar(255) not null,
 year_founded smallint not null,
 championships_won int not null,
 entry_fee_paid boolean not null
);