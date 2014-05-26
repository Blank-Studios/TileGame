Game Overview Idea - Will R

Idea to use arrows and enter key as a means of selecting tiles (using ActionListner):
{
if (*event variable* == *up arrow* && y - 1 > 0)
	*cursor moves up*
if (*event variable* == *down arrow* && y + 1 < *max tile height*)
	*cursor moves down*
if (*event variable* == *left arrow* && x - 1 > 0)
	*cursor moves left*
if (*event variable* == *right arrow* && x + 1 < *max tile height*)
	*cursor moves right*
}

Move option (in menu, when Unit is clicked):
exit menu, show movement distance, move to clicked tile.

Unit's stat array: [level, life, attack, defense, speed, spirit (used for magic stuff), critical hit chance, tiles able to move, whether or not the unit can fly]

To hit: Atk (of attacking Unit) > Def (of defending Unit).

Damage: Weapon damage. x2 if "super effective" (i.e. Bow-class weapon vs flying unit). x2 if Speed (of attacker) > Def (of defender). x2 for critical (Crit stat > random number from 1 - 100). 

--------------------------------------------------------------------

Update 1 - Matthew M

Updates were broad in scope and affected every class. FPS was added along with unit movement and updates. Several new constructors were created for the update/render cycle to have affect. An interface was added, along with an option menu to display unit options. Other details included adding new variables, methods, and algorithms

--------------------------------------------------------------------

Update 2 - Matthew M

Updates were broad and affected every class. Added a fixed update, and rearranged unit constructors for a more fine-tuned object creation. New classes were added that involve a dialogue box, a player class, and the option menu is in its own class. FPS is now displayed on the game's frame. Other major updates and details were applied to help increase effectiveness, and ease of readability and maintainability. Future updates that are needed will be committed soon.
