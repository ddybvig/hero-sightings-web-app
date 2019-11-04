drop database if exists heroSightingsTest;
create database heroSightingsTest;

use heroSightingsTest;

create table hero(
id int primary key auto_increment,
`name` varchar(30) not null,
`description` varchar(200),
superpowers varchar(100) not null
);

create table organization(
id int primary key auto_increment,
`name` varchar(50) not null,
`description` varchar(200),
address varchar(50),
city varchar(30),
state char(2),
zip char(5),
phone varchar(12)
);

create table sighting(
id int primary key auto_increment,
`name` varchar(50) not null,
`description` varchar(200),
dateOfSighting date not null
);

create table hero_organization(
heroId int not null,
organizationId int not null,
primary key(heroId, organizationId),
foreign key (heroId) references hero(id),
foreign key (organizationId) references organization(id)
);

create table hero_sighting(
heroId int not null,
sightingId int not null,
primary key(heroId, sightingId),
foreign key (heroId) references hero(id),
foreign key (sightingId) references sighting(id)
);

create table location(
id int primary key auto_increment,
address varchar(50) not null,
city varchar(30) not null,
state char(2) not null,
zip char(5),
latitude decimal(8,6) not null,
longitude decimal(9,6) not null);

alter table sighting
	add column (locationId int not null), add constraint fk_Sighting_Location foreign key (locationId) references location(id);