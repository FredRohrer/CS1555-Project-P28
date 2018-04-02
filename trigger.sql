--trigger.sql

create or replace function Get_Coffee_Promoted
(coffeeID in Coffee.Coffee_ID%type, storeID in Store.Store_ID%type,
	cur_date in Purchase.Purchase_Time%type)
	return boolean
	declare 
		cursor rst = select *
					from Promotion natural join HasPromotion natural join PromoteFor
					where Store_ID = store_id and Coffee_ID = coffeeID and 
					(cur_date between Start_Date and End_Date)
	begin
		open rst;
		return rst%found
	end
/


create or replace function Get_Coffee_Reward
(coffeeID in Coffee.Coffee_ID%type, 
	storeID in Store.Store_ID%type, 
	cur_date in Purchase.Purchase_Time%type)
	return Customer.Total_Points%type
	reward Customer.Total_Points%type;
	begin
		select Reward_Points into reward
		from Coffee
		where Coffee_ID = coffeeID;
		
		
		if (Get_Coffee_Promoted(coffeeID, storeID, cur_date))
		then
			reward := reward * 2
		end if; 
		return reward;
	end
/
	


create or replace trigger Update_Customer_Points
	after insert
	on BuyCoffee
	for each row --Assuming a BuyCoffee will always have a purchase_Quantity or Redeem_Quantity
	declare
	pointsLost Customer.Total_Points%type,
	pointsGained  Customer.Total_Points%type
	begin
		points_gained := :new.Purchase_Quantity * Get_Coffee_Reward(:new.Coffee_ID, 
	end;
/
	