'''Prompt: You are given an array X of floating-point numbers x1, x2, ... xn. These can be rounded up or down to create a corresponding array Y of integers y1, y2, ... yn.

Write an algorithm that finds an appropriate Y array with the following properties:

    The rounded sums of both arrays should be equal.
    The absolute pairwise difference between elements is minimized. In other words, |x1- y1| + |x2- y2| + ... + |xn- yn| should be as small as possible.

For example, suppose your input is [1.3, 2.3, 4.4]. In this case you cannot do better than [1, 2, 5], which has an absolute difference of |1.3 - 1| + |2.3 - 2| + |4.4 - 5| = 1.

*Note that the prompt is incorrect. The absolute difference here is 1.2'''

import random

# Creates a random list of floats to use for testing purposes.
floats = [round(a, 1) for a in [random.uniform(0, 10) for b in range(10)]]

# Rounds the floats by casting them to ints. 
ints = [int(a) for a in floats]

# Finds the "round remainder" after truncating the floats. I.e (1.3, 2.3, 4.4 -> 1, 2, 4 | remainder = .3 + .3 + .4 = 1). We will add this back to the rounded
# numbers after to make sure the average is the same after rounding.
remainder = round(sum([a % 1 for a in floats]))

# Finds which numbers, after rounded, have the largest difference with their unroudned form. For example, if 6.8 was rounded down to 6 (after being cast to int)
# it's difference is .8. This forms a list from greatest to least of the numbers with the largest difference after rounding. 
largest_dif_pairs = sorted([[abs(floats[m] - ints[m]), m] for m in range(len(floats))], reverse=True)[:remainder]

# We add 1 to the numbers with the largest difference after rounding. This assures that the pair wise difference between rounded numbers and their 
# original floats are minimized. 

for i in range(remainder):
    ints[largest_dif_pairs[i][1]] += 1

# Dispalys the total absolute difference for all pairs.

sum_pairwise_difs = sum([abs(floats[m] - ints[m]) for m in range(len(floats))])

# Data output.
print(largest_dif_pairs)
print(remainder)
print(floats)
print(ints)
print(round(sum(floats)))
print(sum(ints))
print(sum_pairwise_difs)
