'''
This is a project I made for a D&D campaign which generates pages with random runes on it. 
I experimented with multi-threading here and image compoisiting with PIL. 

'''

from PIL import Image
import os
from random import seed
from random import randint
import random
import logging
import threading
import time

from test.sortperf import randfloats

def makeTiles():
    root_dir = r"C:/Users/Darren/Documents/PyxlEdit/Runes/"
    imgs = [Image.open(root_dir+img) for img in os.listdir(root_dir) if "png" in img]
    new_imgs = [];
    imgs = list(imgs)

    for img in imgs:
        width, height = img.size
        ratio = height/100;
        newsize = (int(width/ratio), int(height/ratio))
        new_imgs.append(img.resize(newsize))

        
    tiles = []
    i = 0
    
    for img in new_imgs:
        print(img.size)
        tile = Image.new('RGB', (80,100), color = "white")
        tile.putalpha(0)
        tile.paste(img, (0,0))
        tile.save(root_dir+"tiles/"+str(i)+".png")
        i += 1;
        tiles.append(tile)

def makePages(root_dir, n, width, height):
    
    pages_dir = r"C:/Users/Darren/Documents/PyxlEdit/Runes/Enchiridion/"
    parchment_dir = r"C:/Users/Darren/Documents/PyxlEdit/Runes/Parchment/parchment.png"
    numpages = len(os.listdir(pages_dir))
    tiles = [Image.open(root_dir+img) for img in os.listdir(root_dir) if "png" in img]
    runewidth = 80
    runeheight = 100
    runenum = len(tiles)-1
    page = Image.new('RGB', (720, 1280), color = "white")
    page.putalpha(0)
    parchment = Image.open(r"C:/Users/Darren/Documents/PyxlEdit/Runes/Parchment/parchment.png")
    parchment.putalpha(255)
    for line in range(height):
        for space in range(width):
                
            '''
            hScale = random.uniform(-.10, .10)
            wScale = random.uniform(-.10, .10)
            newsize = (runewidth+int(runewidth*wScale),  runeheight+int(runeheight*hScale))
            '''
                
            page.paste(tiles[randint(0, runenum)], (space*runewidth+50, line*runeheight+40))
           
    page = Image.alpha_composite(parchment, page)
    page.save(pages_dir+"page_"+str(n)+".png")

pages = 100
widthLetters = 8
widthHeight = 12

root_dir = r"C:/Users/Darren/Documents/PyxlEdit/Runes/tiles/"
pages_dir = r"C:/Users/Darren/Documents/PyxlEdit/Runes/Enchiridion/"
numpages = len(os.listdir(pages_dir))

for index in range(numpages, numpages+pages):
    x = threading.Thread(target=makePages, args=(root_dir, index, widthLetters, widthHeight))
    x.start();


'''makeTiles()'''
