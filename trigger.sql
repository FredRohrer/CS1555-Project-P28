--trigger.sql
--done?

create or replace function Get_Coffee_Promoted
(coffeeID in int, storeID in int, cur_date in date)
	return boolean
	is	
		cursor rst is (select *
						from Promotion natural join HasPromotion natural join PromoteFor
						where Store_ID = store_id and Coffee_ID = coffeeID and 
						(cur_date between Start_Date and End_Date));
	begin
		
		open rst;
		return rst%found;
	end;
/


create or replace function Get_Coffee_Reward
(purchID in int, coffeeID in int)
	return float
	is
	reward float;
	cusID int;
	storeID int;
	cur_date date;
	begin
		select Reward_Points into reward
		from Coffee
		where Coffee_ID = coffeeID;
		--reward := 0;
		
		
		select Customer_ID, Store_ID, Purchase_Time
		into cusID, storeID, cur_date
		from Purchase
		where Purchase_ID = purchID;
		
		select Booster_Factor*reward
		into reward
		from Customer natural join MemberLevel
		where Customer_ID = cusID;
		
		
		if (Get_Coffee_Promoted(coffeeID, storeID, cur_date))
		then
			reward := reward * 2;
		end if; 
		return reward;
	end;
/


create or replace trigger Update_Customer_Points
	after insert
	on BuyCoffee
	for each row
	declare 
		pointsGained float;
		pointsLost float;
	begin
		pointsGained := :new.Purchase_Quantity * 
			Get_Coffee_Reward(:new.Purchase_ID, :new.Coffee_ID);
		
		select Redeem_Points*:new.Redeem_Quantity
		into pointsLost
		from Coffee
		where Coffee_ID = :new.Coffee_ID;
		--pointsLost := 1;
		
		update Customer
		set Total_Points = Total_Points + (pointsGained - pointsLost)
		where Customer_ID = (select Customer_ID
							from Purchase
							where Purchase_ID = :new.Purchase_ID);
		
	end;
/


	