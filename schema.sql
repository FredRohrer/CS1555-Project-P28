--schema.sql

--drop tables if they exist up here
drop table Coffee cascade constraints;
drop table Promotion cascade constraints;
drop table MemberLevel cascade constraints;
drop table Customer cascade constraints;
drop table Purchase cascade constraints;

drop table HasPromotion cascade constraints;
drop table PromoteFor cascade constraints;
drop table BuyCoffee cascade constraints;

drop table Store cascade constraints;
drop table OfferCoffee cascade constraints;

create table Store(
	Store_ID int,
	Name varchar2(20),
	Address varchar2(20),
	Store_Type varchar2(20),
	GPS_Long float,
	GPS_Lat float,
	constraint Store_pk primary key (Store_ID)
);

create table Coffee (
	Coffee_ID int,
	Name varchar2(20),
	Description varchar2(20),
	Intensity int,
	Price float,
	Reward_Points float,
	Redeem_Points float,
	constraint Coffee_pk primary key (Coffee_ID)
);

create table Promotion (
	Promotion_ID int,
	Name varchar2(20),
	Start_Date date,
	End_Date date,
	constraint Promotion_pk primary key (Promotion_ID),
	constraint Promotion_Time_Period check (End_Date > Start_Date)
);

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
		references MemberLevel(MemberLevel_ID),
	constraint Customer_Points_Not_Neg check (Total_Points >= 0)
); 

create table Purchase (
	Purchase_ID int,
	Customer_ID int,
	Store_ID int,
	Purchase_Time date,
	constraint Purchase_pk primary key (Purchase_ID),
	constraint Purchase_fk_Customer foreign key (Customer_ID)
		references Customer(Customer_ID)--,
	constraint Purchase_fk_Store foreign key (Store_ID)
		references Store(Store_ID)
);

create table OfferCoffee (
	Store_ID int,
	Coffee_ID int,
	constraint OfferCoffee_pk primary key (Store_ID, Coffee_ID),
	constraint OfferCoffee_store_fk foreign key (Store_ID)
		references Store(Store_ID),
	constraint OfferCoffee_coffee_fk foreign key (Coffee_ID)
		references Coffee(Coffee_ID)
);

create table HasPromotion (
	Store_ID int,
	Promotion_ID int,
	constraint HasPromotion_pk primary key (Store_ID, Promotion_ID),
	constraint HasPromotion_Store_fk foreign key (Store_ID)
		references Store(Store_ID),
	constraint HasPromotion_Promo_fk foreign key (Promotion_ID)
		references Promotion(Promotion_ID)
);

create table PromoteFor (
	Promotion_ID int,
	Coffee_ID int, 
	constraint PromoteFor_pk primary key (Promotion_ID, Coffee_ID),
	constraint PromoteFor_Promo_fk foreign key (Promotion_ID)
		references Promotion(Promotion_ID)--,
	constraint PromoteFor_Cof_fk foreign key (Coffee_ID)
		references Coffee(Coffee_ID)
);

create table BuyCoffee (
	Purchase_ID int,
	Coffee_ID int,
	Purchase_Quantity int,
	Redeem_Quantity int,
	constraint BuyCoffee_pk primary key (Purchase_ID, Coffee_ID),
	constraint BuyCoffee_Purch_fk foreign key (Purchase_ID)
		references Purchase(Purchase_ID)--,
	constraint BuyCoffee_Cof_fk foreign key (Coffee_ID)
		references Coffee(Coffee_ID)
);

