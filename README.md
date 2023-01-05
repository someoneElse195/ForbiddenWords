# ForbiddenWords

First try making a spigot plugin for the QueerCoded community Minecraft server!

My initial intentions were to create a plugin that kills a player when they say a specific word with said word changing after a week.
The words would be unknown and the plugin would never directly tell you what the word is, only that you saying it caused your death. 
At the end of each week it will tell you what the previous week's word was.

Also btw I suck absolute ass at coding so don't make fun of me for anything stupid I did just tell me what I did wrong lmfao...

In order to actually be able to use the plugin you need to put words.txt into /plugins/ForbiddenWords/ 
Put 1 word per line and the plugin will pick a new word after each week as long as the server is up at 12am Sunday local server time.
The words go through in a linear manner and the amount that you have progressed through the total list is saved in progressed.txt.
If you wish to set back to a previous word simply change progressed to the index of the word you would like. 

There is no jar file download for this yet but I will include it at some point.
