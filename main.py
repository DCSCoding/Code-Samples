import random
from collections import defaultdict


def default_val():
    return []


adjacents = defaultdict(default_val)
num_dead_ends = 30

# Note: Dead ends may be repeated. The start point may also be a dead end.
# In this case, it will return that the end point cannot be reached.

# Randomly generated dead ends.
deadends = [(random.randint(0, 9), random.randint(0, 9), random.randint(0,9)) for a in range(num_dead_ends)]

start_point = (0, 0, 0)

# Random end point
end_point = (random.randint(0, 9), random.randint(0, 9), random.randint(0, 9))


print("Dead ends: " + str(deadends))
print("Start point: " + str(start_point))
print("End point: " + str(end_point))

# Makes an adjacency list (+1 or -1 in any given dimension, if that point exists)
for x in range(10):
    for y in range(10):
        for z in range(10):
            if not (x, y, z) in deadends:
                if x + 1 < 10 and (x+1, y, z) not in deadends:
                    adjacents[(x, y, z)].append((x + 1, y, z))
                if x - 1 >= 0 and (x-1, y, z) not in deadends:
                    adjacents[(x, y, z)].append((x - 1, y, z))
                if y + 1 < 10 and (x, y+1, z) not in deadends:
                    adjacents[(x, y, z)].append((x, y + 1, z))
                if y - 1 >= 0 and (x, y-1, z) not in deadends:
                    adjacents[(x, y, z)].append((x, y - 1, z))
                if z + 1 < 10 and (x, y, z+1) not in deadends:
                    adjacents[(x, y, z)].append((x, y, z + 1))
                if z - 1 >= 0 and (x, y, z+-1) not in deadends:
                    adjacents[(x, y, z)].append((x, y, z - 1))

# Beginning of BFS
queue = [start_point]
searched = [start_point]

# Due to the nature of a BFS, where each node is only visited once, we can use a dictionary
# with key = destination and val = origin to make what is essentially a linked list traversable
# from the end point to the origin.

paths = defaultdict(default_val)
found = False

while (len(queue)) > 0:
    pos = queue.pop(0)
    for neighbor in adjacents[pos]:
        if neighbor not in searched:
            queue.append(neighbor)
            searched.append(neighbor)
            paths[neighbor] = pos

            # Once we've reached the end point, we break the loop and report our results.
            if neighbor == end_point:
                found = True
                break

    if found:
        break

count = 0

# If we've found the end point, we report the path we took and how many steps it took us.
# If it is not reachable, we report that instead.

if found:
    current = end_point
    print("---Path---")
    while current != start_point:
        print(paths[current])
        current = paths[current]
        count += 1
    print("Steps required: " + str(count))
else:
    print("Cannot reach.")





