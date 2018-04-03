--query.sql

--insert stores
INSERT INTO Store VALUES (Store_seq.NEXTVAL, "Store 1", "Dollar Store", 
			  10.4, 20.9);
INSERT INTO Store VALUES (Store_seq.NEXTVAL, "Store 2", "Supermaket", 
			   2342.34, 232.32);
INSERT INTO Store VALUES (Store_seq.NEXTVAL, "Store 3", "Coffee shop", 
			  15.88, 20.5);

--insert into customers
INSERT INTO Customer VALUES (Customer_seq.NEXTVAL, "John", "Smith", null, 
			     MemberLevel_seq.NEXTVAL, 0);
INSERT INTO Customer VALUES (Customer_seq.NEXTVAL, "Jane", "Doe", null, 
			     MemberLevel_seq.NEXTVAL, 0);
INSERT INTO Customer VALUES (Customer_seq.NEXTVAL, "John", "Doe", null,
			     MemberLevel_seq.NEXTVAL, 0);
INSERT INTO Customer VALUES (Custmer_seq.NEXTVAL, "Mary", "Jane", null, 
			     MemberLevel_seq.NEXTVAL, 0);
INSERT INTO Customer VALUES (Customer_seq.NEXTVAL, "Foo", "Bar", null,
			     MemberLevel_seq.NEXTVAL, 0);
INSERT INTO Customer VALUES (Customer_seq.NEXTVAL, "George", "Bush", null,
			     MemberLevel_seq.NEXTVAL, 0);
INSERT INTO Customer VALUES (Customer_seq.NEXTVAL, "Bill", "Clinton", null,
			     MemberLevel_seq.NEXTVAL, 0);
INSERT INTO Customer VALUES (Customer_seq.NEXTVAL, "Donald", "Trump", null, 
			     MemberLevel_seq.NEXTVAL, 1000000);
INSERT INTO Customer VALUES (Customer_seq.NEXTVAL, "John", "Kennedy", null,
			     MemberLevel_seq.NEXTVAL, 25);
INSERT INTO Customer VALUES (Customer_seq.NEXTVAL, "Bill", "Kennedy", null,
			     MemberLevel_seq.NEXTVAL, 99);
INSERT INTO Customer VALUES (Customer_seq.NEXTVAL, "Antonio", "Brown", null,
			     MemberLevel_seq.NEXTVAL, 101010);
