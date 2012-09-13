# Turtle Java

This is an implementation of Turtle graphics in Java, created for CSE 450 at
Michigan State University.  This is a working spec.

## Native Turtle commands in Logo
The full command is listed first, followed by an alias in *italics*–if one exists,
any arguments follow the command name.  This is not a full list of the commands which
Logo defines, merely the ones that seem relevant.

### Graphics context
clearscreen *cs* <br/>
clean <br/>
hideturtle  *ht* <br/>
showturtle  *st* <br/>
wrap <br/>
window <br/>

Colors:

* 0 black 
* 1 blue 
* 2 green 
* 3 cyan
* 4 red 
* 5 magenta 
* 6 yellow 
* 7 white
* 8 brown 
* 9 tan 
* 10 forest 
* 11 aqua
* 12 salmon 
* 13 purple 
* 14 orange 
* 15 grey

### Drawing
penup *pu* <br/>
pendown *pd* <br/>
penpaint *ppt* <br/>
penerase *pe* <br/>

setpencolor *setpc* color <br/>
setpensize size <br/>
setbackgroundcolor *setbg* color <br/>
setpalette colornum [r, g, b] <br/>

fill <br/>
label text <br/>

setpos [x, y] <br/>
setxy x y <br/>
setx x <br/>
sety y <br/>
setheading *seth* deg <br/>
home <br/>

forward *fd* distance <br/>
back *bk* distance <br/>
right *rt* degrees <br/>
left *lt* degrees <br/>
arc angle radius <br/>

## Aditional functions added by this library

### Drawing
setpenrgb *setrgb* [r, g, b]