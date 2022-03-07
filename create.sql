create table address (id int4 generated by default as identity, address_line varchar(255), number varchar(255), postal_code varchar(255), region varchar(255), street_address varchar(255), city_id int4, client_id int4, primary key (id));
create table category (id int4 generated by default as identity, name varchar(255), primary key (id));
create table city (id int4 generated by default as identity, name varchar(255), estado_id int4, primary key (id));
create table client (id int4 generated by default as identity, email varchar(255), name varchar(255), ssn_or_ein varchar(255), type int4, primary key (id));
create table payment (purchase_id int4 not null, status int4, primary key (purchase_id));
create table payment_with_bill (due_date timestamp, payment_date timestamp, purchase_id int4 not null, primary key (purchase_id));
create table payment_with_card (installments int4, purchase_id int4 not null, primary key (purchase_id));
create table phone_number (client_id int4 not null, phone_numbers varchar(255));
create table product (id int4 generated by default as identity, name varchar(255), price float8, primary key (id));
create table product_category (product_id int4 not null, category_id int4 not null);
create table purchase (id int4 generated by default as identity, instant timestamp, client_id int4, delivery_address_id int4, primary key (id));
create table purchase_item (discount float8, price float8, quantity int4, purchase_id int4 not null, product_id int4 not null, primary key (product_id, purchase_id));
create table state (id int4 generated by default as identity, name varchar(255), primary key (id));
alter table if exists client add constraint UK_bfgjs3fem0hmjhvih80158x29 unique (email);
alter table if exists address add constraint FKpo044ng5x4gynb291cv24vtea foreign key (city_id) references city;
alter table if exists address add constraint FK7156ty2o5atyuy9f6kuup9dna foreign key (client_id) references client;
alter table if exists city add constraint FK5svj0bep69leo17f1vgy1eg44 foreign key (estado_id) references state;
alter table if exists payment add constraint FKmm2h9p8cpu4741lwxyn2fnpgg foreign key (purchase_id) references purchase;
alter table if exists payment_with_bill add constraint FKoejfe72oqgsbm46i55wegmggw foreign key (purchase_id) references payment;
alter table if exists payment_with_card add constraint FKt2kxk1ev12163vsqyfvn7xf0 foreign key (purchase_id) references payment;
alter table if exists phone_number add constraint FKkidesb3pyab44og7c42blvxbg foreign key (client_id) references client;
alter table if exists product_category add constraint FKkud35ls1d40wpjb5htpp14q4e foreign key (category_id) references category;
alter table if exists product_category add constraint FK2k3smhbruedlcrvu6clued06x foreign key (product_id) references product;
alter table if exists purchase add constraint FK72b3lvttv8rw80xc3orixyiml foreign key (client_id) references client;
alter table if exists purchase add constraint FKhu8d8vuae33k8op7ssujvlj36 foreign key (delivery_address_id) references address;
alter table if exists purchase_item add constraint FK1mncc5yaore1sibgpj3jc4a7u foreign key (purchase_id) references purchase;
alter table if exists purchase_item add constraint FKq69apam78dbi0cggl37ge6auf foreign key (product_id) references product;
