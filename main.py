import random

floats = [round(a, 1) for a in [random.uniform(0, 10) for b in range(10)]]

ints = [int(a) for a in floats]

remainder = round(sum([a % 1 for a in floats]))

largest_dif_pairs = sorted([[abs(floats[m] - ints[m]), m] for m in range(len(floats))], reverse=True)[:remainder]

for i in range(remainder):
    ints[largest_dif_pairs[i][1]] += 1

sum_pairwise_difs = sum([abs(floats[m] - ints[m]) for m in range(len(floats))])

print(largest_dif_pairs)
print(remainder)
print(floats)
print(ints)
print(round(sum(floats)))
print(sum(ints))
print(sum_pairwise_difs)