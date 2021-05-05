create table if not exists users (
	user_id serial primary key,
	username varchar(20) unique not null,
	password varchar(10) not null,
	email varchar(255) unique not null,
	group_id int,
	foreign key (group_id)
		references usergroups (group_id)
		on delete cascade
);

create table if not exists usergroups (
	group_id serial primary key,
	group_name varchar( 20 ) unique not null
);

create table if not exists items (
	item_id serial primary key,
	item_name varchar( 50 ) not null,
	price numeric( 10, 2 ) not null
)

create table if not exists status (
	status_id serial primary key,
	status_name varchar ( 50 ) not null
)

create table if not exists offers (
	offer_id serial primary key,
	item_id int not null,
	quantity int not null check(quantity > 0),
	offer_price numeric(5, 2) check(offer_price > 0.00),
	offer_date timestamp default now(),
	user_id int not null,
	status_id int not null,
	ownership boolean not null,
	foreign key (user_id)
		references users (user_id)
		on delete cascade,
	foreign key (status_id)
		references status (status_id)
		on delete cascade,
	foreign key (item_id)
		references items (item_id)
		on delete cascade
)

create table if not exists payments (
	payment_id serial not null,
	offer_id int not null,
	amount numeric(5, 2) not null,
	payment_date timestamp default now(),
	user_id int not null,
	foreign key (offer_id)
		references offers (offer_id)
		on delete cascade,
	foreign key (user_id)
		references users (user_id)
		on delete cascade
)

insert into shop_schema."usergroups" (group_name) values('customer');
insert into shop_schema."usergroups" (group_name) values('manager');
insert into shop_schema."usergroups" (group_name) values('employee');

insert into shop_schema."status" (status_name) values('pending');
insert into shop_schema."status" (status_name) values('completed');
insert into shop_schema."status" (status_name) values('rejected');


insert into shop_schema."users" (username, "password", email, group_id) values ('firstTestUser', '12341234', 'firstTestUser@email.com', 1);
insert into shop_schema."users" (username, "password", email, group_id) values ('employee', '12341234', 'employee@company.com', 2);



drop table shop_schema.payments;
drop table shop_schema.offers;
drop table shop_schema.users;
drop table shop_schema.groups;

alter table shop_schema.users add column group_id int;
alter table shop_schema.items rename column price to item_price;
alter table shop_schema.offers add column installments int default 0;
alter table shop_schema.offers drop column installments;

alter table shop_schema.offers add column balance numeric(5, 2) default 0.00;


delete from shop_schema.users where email = 'email@co.com';
delete from shop_schema.users where email = 'emailtest3@mail.co';
insert into shop_schema.items (item_name, item_price) values('test item 2', 20);


