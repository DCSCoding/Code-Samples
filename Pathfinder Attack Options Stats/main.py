"""Calculates average damage of different configurations of Power Attack, weapons, weapon sizes, and improved critical
vs. various armor classes"""

import random

# Samples 10,000 rolls for each case.
def average_damage(dice, sides, hit_mod, crit_mult, crit_range, damage_mod, ac):
    total_damage = 0
    hit = 0
    # Roll a d20 10,000 times
    for i in range(10000):
        damage = 0
        roll = random.randint(1, 20)
        # If the result is a 1, this is an automatic miss.
        if roll != 1:
            # Checks if the attack is a critical hit
            if (roll >= crit_range and roll + hit_mod > ac) or roll == 20:
                # Checks to confrim the crit
                if random.randint(1, 20) + hit_mod > ac:
                    # Rolls dice, adds the damage mod, multiples by crit multiplier.
                    for d in range(dice):
                        damage += random.randint(1, sides)
                    damage += damage_mod
                    damage = damage * crit_mult
                else:
                    # If the crit is not confirmed, rolls normal damage.
                    for d in range(dice):
                        damage += random.randint(1, sides)
                    damage += damage_mod
            # If not a crit, rolls normal damage.
            elif roll + hit_mod > ac:
                for d in range(dice):
                    damage += random.randint(1, sides)
                damage += damage_mod
        total_damage += damage
        # Any attack that deals damage counts as a hit.
        if damage > 0:
            hit += 1
    # Return the average damage, and number of hits.
    return total_damage/10000, hit


no_pa = dict()
pa = dict()
big_falcata = dict()
big_falchion = dict()
big_bastard = dict()

great_sword_improved_crit = dict()
big_falcata_improved_crit = dict()
big_falchion_improved_crit = dict()
big_bastard_improved_crit = dict()

'''for ac in range(30):
    no_pa[ac] = average_damage(2, 6, 8, 2, 19, 7, ac)'''

for ac in range(30):
    pa[ac] = average_damage(2, 6, 7, 2, 19, 10, ac)

for ac in range(30):
    big_falcata[ac] = average_damage(2, 6, 5, 3, 19, 10, ac)

for ac in range(30):
    big_falchion[ac] = average_damage(2, 6, 5, 2, 18, 10, ac)

for ac in range(30):
    big_bastard[ac] = average_damage(2, 8, 5, 2, 19, 10, ac)

for ac in range(30):
    great_sword_improved_crit[ac] = average_damage(2, 6, 7, 2, 17, 10, ac)

for ac in range(30):
    big_falcata_improved_crit[ac] = average_damage(2, 6, 5, 3, 17, 10, ac)

for ac in range(30):
    big_falchion_improved_crit[ac] = average_damage(2, 6, 5, 2, 15, 10, ac)

for ac in range(30):
    big_bastard_improved_crit[ac] = average_damage(2, 8, 5, 2, 17, 10, ac)

print("Greatsword " + str(pa))
print("Oversized Falcata " + str(big_falcata))
print("Oversized Falchion " + str(big_falchion))
print("Oversized Bastard Sword " + str(big_bastard))

print("Improved Crit Greatsword " + str(great_sword_improved_crit))
print("Improved Crit Oversized Falcata " + str(big_falcata_improved_crit))
print("Improved Crit Oversized Falchion " + str(big_falchion_improved_crit))
print("Improved Crit Oversized Bastard Sword " + str(big_bastard_improved_crit))

