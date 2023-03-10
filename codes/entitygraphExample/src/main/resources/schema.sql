create table address (id bigint generated by default as identity, house_number varchar(255), street varchar(255), zip_code varchar(255), primary key (id));

create table car (id bigint generated by default as identity, registration_number varchar(255), company_id bigint, primary key (id));

create table company (id bigint generated by default as identity, name varchar(255), primary key (id));
alter table car add constraint FKjqp14dkmi2lh5kjavv4t0dkqy foreign key (company_id) references company;

create table department (id bigint generated by default as identity, name varchar(255), company_id bigint, primary key (id));
alter table department add constraint FKh1m88q0f7sc0mk76kju4kcn6f foreign key (company_id) references company;


create table employee (id bigint generated by default as identity, name varchar(255), surname varchar(255), address_id bigint, department_id bigint, primary key (id));
alter table employee add constraint FKga73hdtpb67twlr9c1i337tyt foreign key (address_id) references address;
alter table employee add constraint FKbejtwvg9bxus2mffsm3swj3u9 foreign key (department_id) references department;

create table office (id bigint generated by default as identity, name varchar(255), address_id bigint, department_id bigint, primary key (id));
alter table office add constraint FKga73hdtpb67twlr9c35357tyt foreign key (address_id) references address;
