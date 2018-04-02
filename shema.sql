--schema.sql

--drop tables if they exist up here

drop table MemberLevel cascade constraints;
drop table Customer cascade constraints;
drop table Purchase cascade constraints;

create table MemberLevel (
	MemberLevel_ID int,
	Name varchar2(20),
	Booster_Factor float,
	constraint MemberLever_pk primary key (MemberLevel_ID)
);

create table Customer (
	Customer_ID int, 
	First_Name varchar2(20),
	Last_Name varchar2(20),
	Email varchar2(20),
	MemberLevel_ID int,
	Total_Points float,
	constraint Customer_pk primary key (Customer_ID),
	constraint Customer_fk_MemberLevel foreign key (MemberLevel_ID) 
		references MemberLevel(MemberLevel_ID)
); --I need to add a trigger

create table Purchase(
	Purchase_ID int,
	Customer_ID int,
	Store_ID int,
	Purchase_Time,
	constraint Purchase_pk primary key (Purchase_ID),
	constraint Purchase_fk_Customer foreign key (Customer_ID)
		references Customer(Customer_ID),
	constraint Purchase_fk_Store foreign key (Store_ID)
		references Store(Store_ID)
);