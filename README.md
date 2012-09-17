# Turtle Java

This is an implementation of Turtle graphics in Java, created for CSE 450 at
Michigan State University.

## Turtle command list:

clearScreen, clean, showTurtle, hideTurtle

forward, backward, left, right

setXY, setX, setY, setHeading, home

setPenSize, setPenColor, setPenRGB, setBackgroundColor

arc, drawText, fill


## Native Turtle commands from Logo
The full command is listed first, followed by an alias in *italics*–if one exists,
any arguments follow the command name.  This is not a full list of the commands which
Logo defines, merely the ones upon which this library is based.

### Graphics context
clearscreen *cs*, clean, hideturtle  *ht*, showturtle  *st*, wrap, window, fence

#### Colors:

0. black
1. blue
2. green
3. cyan
4. red
5. magenta
6. yellow
7. white
8. brown
9. tan
10. forest
11. aqua
12. salmon
13. purple
14. orange
15. grey

### Drawing
penup *pu*, pendown *pd*, penpaint *ppt*, penerase *pe*

setpencolor *setpc* color, setpensize size, setbackgroundcolor *setbg* color, setpalette colornum [r, g, b]

fill, label text

setpos [x, y], setxy x y, setx x, sety y, setheading *seth* deg, home

forward *fd* distance, back *bk* distance, right *rt* degrees, left *lt* degrees, arc angle radius

## Aditional functions added by this library

### Drawing
setpenrgb *setrgb* [r, g, b]