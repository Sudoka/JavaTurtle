/*
 *      Simple Turtle parser
 */
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

import java.io.*;

public class TurtleTest {
    public static void main(String[] args) throws Exception {
        /** Try an interpreter line by line
            // create a CharStream that reads from standard input
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String cmd = in.readLine();

            while(!cmd.equals("exit") && !cmd.equals("bye") && !cmd.equals("quit")) {
                ANTLRInputStream input = new ANTLRInputStream(new StringReader(cmd));

                // // create a lexer that feeds off of input CharStream
                TurtleLexer lexer = new TurtleLexer(input);

                // // create a buffer of tokens pulled from the lexer
                CommonTokenStream tokens = new CommonTokenStream(lexer);

                // // create a parser that feeds off the tokens buffer
                // TurtleParser parser = new TurtleParser(tokens);
                // parser.prog();
                // System.out.println(cmd);
                cmd = in.readLine();
            }
        */
        
        // get the input
        ANTLRInputStream input = new ANTLRInputStream(System.in);

        // create a lexer that feeds off of input CharStream
        TurtleLexer lexer = new TurtleLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        TurtleParser parser = new TurtleParser(tokens);
        parser.prog();

        // // Walk the resulting tree
        // CommonTree t = (CommonTree)r.getTree();
        // // System.out.println(t.toStringTree());

        // CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
    }
}