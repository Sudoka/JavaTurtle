grammar Turtle;

@header{
	import MSU.Turtle.Turtle;
}
@members {
	Turtle myTurtle = new Turtle("I Love Logo!", 500, 500);
	private int toInt(Token t) { return Integer.parseInt(t.getText()); }
	private float toFloat(Token t) { return Float.parseFloat(t.getText()); }
}
prog: stmt+;
stmt: // movement
	  ('forward'|'fw') INT { myTurtle.forward( toInt($INT) ); }
    | ('backward'|'bw') INT { myTurtle.backward( toInt($INT) ); }
    | ('left'|'lt') in=(FLOAT|INT) { myTurtle.left( toFloat($in) ); }
    | ('right'|'rt') in=(FLOAT|INT) { myTurtle.right( toFloat($in) ); }
      // screen stuff
    | ('clearscreen'|'cs') { myTurtle.clearScreen(); }
    | ('clean') { myTurtle.clean(); }
      // pen stuff
    | ('penup'|'pu') { myTurtle.setPenDown(false); }
    | ('pendown'|'pd') { myTurtle.setPenDown(true); }
    | ('setpencolor') INT { myTurtle.setPenColor(toInt($INT) ); }
    | ('setpenrgb') r=INT g=INT b=INT { myTurtle.setPenRGB(toInt($r), toInt($g), toInt($b) ); }
    | ('setbackgroundncolor') INT { myTurtle.setBackgroundColor(toInt($INT) ); }
    | ('setbackgroundrgb') r=INT g=INT b=INT { myTurtle.setBackgroundRGB(toInt($r), toInt($g), toInt($b) ); }
      // location & heading
    | ('setxy') x=INT y=INT { myTurtle.setXY(toInt($x), toInt($y) ); }
    | ('sethead') h=FLOAT { myTurtle.setHeading(toFloat($h) ); }
    ;



ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

INT :	'-'? '0'..'9'+
    ;

FLOAT
    :   '-'? ('0'..'9')+ '.' ('0'..'9')*
    |   '.' ('0'..'9')+
    ;

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

