'''Prompt: We say a number is sparse if there are no adjacent ones in its binary representation. For example, 21 (10101) is sparse, but 22 (10110) is not. For a given input N, find the smallest sparse number greater than or equal to N.

Do this in faster than O(N log N) time.'''

# Stores a representation of a binary number in a list.
num = [0, 0, 0, 1, 1, 0, 1, 0]
# Makes a copy of that number of later (just used to make comparing the numbers side by side easy)
numcopy = list(num)

i = len(num)-1

# The algorithm works as follows: starts at the end of the number, and checks if that digit and the digit in the column 
# to the left are both equal to 1. If they are, it checks if the digit two columns to the left is a 0.
# If it is, it sets num[i] and num[i-1] to 0, and num[i-2] to 1. It then decrement i and continues.
# Otherwise, it sests only num[i] to 0, decrements i and continues.
# If it reaches the end of the number and the number still is not sparse, (there is a 1 in the two largest order digits)
# it sets i (the second largest order digit) to 0, and adds a 0 to the end of the array. 
# This solves the problem in linear time. 

while i > 0:
	if num[i] == 1 and num[i-1] == 1:
		if i > 1:
			if num[i-2] == 0:
				num[i-2] = 1
				num[i-1] = 0
				num[i] = 0
				i -= 1
			else:
				num[i] = 0
				i -= 1
		else:
			num[i] = 0
			num += 0
			i = 0
	else:
		i -= 1


print(numcopy)
print(num)
