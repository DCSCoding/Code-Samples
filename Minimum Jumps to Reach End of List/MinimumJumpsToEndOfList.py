'''Prompt taken from 'Daily Coding Problem':
You are given an array of integers, where each element represents the maximum number of steps that can be jumped going forward from that element. Write a function to return the minimum number of jumps you must take in order to get from the start to the end of the array.
For example, given [6, 2, 4, 0, 5, 1, 1, 4, 2, 9], you should return 2, as the optimal solution involves jumping from 6 to 5, and then from 5 to 9.
'''

# This is a solution to the above prompt using an adjacency list and a breadth first search.
# Basically, we'll build an adjacency list, then do a breadth first search to find the shortest way out.
# A is adjacent to b if b is in the range a.pos +- a.val

# Another solution would be look for all adjacent positions and see which one allows you to reach the next farthest position. 

integers = [6, 2, 4, 0, 5, 1, 1, 4, 2, 9]

# Creates a dictionary where the key is the point, and the value is a list of adjacent points.

adjacents = {}
for i, num in enumerate(integers):
    adjacents[i] = []
    for m in range(len(integers)):
        if i > m >= i - integers[i]:
            adjacents[i].append(m)
        if i < m <= i + integers[i]:
            adjacents[i].append(m)

print(adjacents)
queue = [0]
searched = [0]
count = 0

# BFS. Prev keeps track of which points we followed to reach the end. Technically not necessary for the prompt, but
# nice to see for testing purposes (and we can also get the 'minimum number of steps' by print the length of the list -
# None types.

prev = [None for a in adjacents.keys()]

while len(queue) != 0:
    s = queue.pop(0)

    for node in adjacents[s]:
        if node not in searched:
            prev[s] = node
            queue.append(node)
            searched.append(node)


def optimal_path(prev):
    return [k for k in prev if k is not None]


opt = optimal_path(prev)
print(opt)
print(len(opt))
