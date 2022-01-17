import urllib.request

fp = urllib.request.urlopen("https://store.steampowered.com/hwsurvey/directx/")
mybytes = fp.read()

mystr = mybytes.decode("utf8")
fp.close()

#The index of the line where the list of DX12 cards begins on the hardware survey.
DX_12_CARDS_INDEX = 8988
SPAN_TAG_LENGTH = len('<span>')
#How many lines you have to skip to get to get to the relevent cards user %
LINES_TO_GPU_PERCENT = 3

strings = mystr.splitlines()
rtx = [s for s in strings[DX_12_CARDS_INDEX:] if 'RTX' in s]
gpus = [s[s.find('<span>')+SPAN_TAG_LENGTH:s.find('</span>')] for s in rtx]
percents = {}

for i, s in enumerate(strings[DX_12_CARDS_INDEX:], DX_12_CARDS_INDEX):
    for gpu in gpus:
        if s[s.find('<span>')+SPAN_TAG_LENGTH:s.find('</span>')] == gpu:
            print(gpu)
            percents[gpu] = strings[i+LINES_TO_GPU_PERCENT][strings[i+LINES_TO_GPU_PERCENT].find('>')+1:strings[i+LINES_TO_GPU_PERCENT].find('</')-1]

print(percents)

total = 0
for key in percents.keys():
    total += float(percents[key])

print(total)
