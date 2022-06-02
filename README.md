### Gilded Rose Requirements Specification 


Hi and welcome to team Gilded Rose. As you know, we are a small inn with a prime location in a
prominent city ran by a friendly innkeeper named Allison. We also buy and sell only the finest goods.
Unfortunately, our goods are constantly degrading in quality as they approach their sell by date. We
have a system in place that updates our inventory for us. It was developed by a no-nonsense type named
Leeroy, who has moved on to new adventures. Your task is to add the new feature to our system so that
we can begin selling a new category of items. First an introduction to our system:

	- All items have a SellIn value which denotes the number of days we have to sell the item
	- All items have a Quality value which denotes how valuable the item is
	- All items have a Type value which denotes what kind of item it is, it could be: NORMAL,AGED,LEGENDARY or TICKETS
	- At the end of each day our system lowers both values (SellIn and Quality) for every item

Pretty simple, right? Well this is where it gets interesting:

	- Once the sell by date has passed, Quality degrades twice as fast
	- The Quality of an item is never negative
	- The items of type AGED actually increases in Quality the older it gets
	- The Quality of an item is never more than 50
	- The LEGENDARY items, never has to be sold or decreases in Quality
	- The items of type TICKETS , like the AGED ones, increases in Quality as its SellIn value approaches;
	   Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
	   Quality drops to 0 after the concert

We have recently signed a supplier of conjured items. This requires an update to our system:

	- CONJURED items degrade in Quality twice as fast as normal items

Feel free to make any changes to the UpdateQuality method and add any new code as long as everything
still works correctly. However, do not alter the Item class or Items property as those belong to the
goblin in the corner who will insta-rage and one-shot you as he doesn't believe in shared code
ownership (you can make the UpdateQuality method and Items property static if you like, we'll cover
for you).

Just for clarification, an item can never have its Quality increase above 50, however for LEGENDARY items
its Quality is 80 and it never alters.

https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/main/GildedRoseRequirements.txt


##### Homework 1
- Modify updateItem method of ItemService to validate if the item exists before calling the repository to update, It must throw an exception when item was not found.
- Implement necessary test cases to achieve 100% coverage. Each test case must be documented using BDD approach(Given, when, then).
- (Bonus) Implement Create batch Item endpoint, the endpoint may receive a list of items and save them to the database. You should validate there is no two items with the same values, if so you must throw an error.  
