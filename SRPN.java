/**
 * The main SRPN calculater.
 * it allows for calculations to be inputted with the operands before the operator.
 * Methods include : main, splitUpCommand and processCommand
 */


import java.io.*;
import java.lang.Math;
import java.util.*;


public class SRPN {
    /*
     Create a new stack from the stack class and use it to store all the operands in.
     We use 23 as the stack in the origional program is of size 23
     srpnStack is an instance of the class stack.
    */
    public Stack srpnStack = new Stack(23);
    //create a new randomNumber object from the class random
    public Random randomNumber = new Random();
    //boolean comment is used as a flag to tell if the inputted text is a comment or not
    boolean comment = false;

    /*
    Splits up inputted command before it is to be processed
    Method is mainly used to implement comments and do allow multiple operands and operators to be inputted on one line.
    @param tempString
     */
    public void splitUpCommand(String tempString) {
        //stringArray is used to hold the split up strings when multiple operands and operators are inputted on the same line.
        //the split function splits the string in the tempString by spaces and stores it into the array
        String[] stringArray = tempString.split(" ");
        //checkIfLetter is used to determine whether items entered after a comment is a number or a word
        boolean checkIfLetter = false;
        //characterAsString is a temporary string used to convert single characters to strings
        // this means that they can be processed by processCommand method.
        String characterAsString = "";

        //for loop runs for each item in the stringArray. it runs for every item entered on the same line.
        for (int i = 0; i < stringArray.length; i++) {
            //checks if inputted text is a comment or not
            if (stringArray[i].equals("#") && comment == false ) {
                comment = true;
                //checks if the comment is being closed or not
            } else if (comment == true && stringArray[i].equals("#") ) {
                comment = false;
                //this gets rid of the # and saves the rest as the string array.
                // This is needed for the rest of the input to be processed by processCommand
                stringArray[i] = stringArray[i].substring(1);

                //check if contents in array is string or not
                try {
                    Integer.parseInt(stringArray[i]);
                } catch (NumberFormatException e) {
                    checkIfLetter = true;
                }
            }

            //code runs if either comment has never been activated or comment has been closed
            if (comment == false) {
                //this splits up any words into individual characters so that they are sent to processCommand individually.
                // this is needed as it is how the origional SRPN code runs
                if (checkIfLetter == true) {
                    for (int j = 0; j < stringArray[i].length(); j++) {
                        //converts character to string
                        characterAsString = String.valueOf(stringArray[i].charAt(j));
                        processCommand(characterAsString);
                    }
                } else {
                    //sends the item in the array to be processed
                    processCommand(stringArray[i]);
                }
            }
        }
    }


    /*
    Method processes the inputted data
    it performs the main SRPN calculations.
    @param s (this holds the item to be processed)
     */
    public void processCommand(String s) {
        /*
        retrieve input from the program
        s.charAt(s.length()-1) looks for last character in the line and stores it in input.
        This would mean if "1 2 +" is written then + would be recognized
        */
        char input;
        try{
            input = s.charAt(s.length()-1);
        } catch (StringIndexOutOfBoundsException e){
            //this catches any blank input
           input = ' ';
        }

        // run code if inputted command is one of the recognised inputted command
        if (input == '+' || input == '-' || input == '*' || input == '/' || input == '%' || input == '^'){
            /* declare the two variables to store the operands
             set these operands to 0 so that if there is a single operand and an operator
             such as 8 + then the answer would still be 8.
            */
            int operand_1 = 0;
            int operand_2 = 0;

            //try and set operands to the value in the stack.
            try {
                operand_2 = srpnStack.pop();
                operand_1 = srpnStack.pop();
            } catch (Exception e){
                // this exception is left blank as the operand is previously set to 0
            }

            // create a variable to store the result of the calculations
            int calculationResult = 0;

            //perform switch case to perform the calculation
            switch (input){
                case '+' :
                    /*
                    Origionally i used + to add the numbers but struggled to deal with saturation using this.
                    I researched for an alternative to addition and found the Exact maths operatiors
                    I learnt about addExact from https://www.mkyong.com/java8/java-8-math-exact-examples/
                    I am not certain whether i need to reference a google search but thought i'd prefer to be on the safe side.
                    This reference also helped with saturation relating to subtaction
                    */
                    try {
                        calculationResult = Math.addExact(operand_1, operand_2);
                        //push calculation result to stack
                        srpnStack.push(calculationResult);
                    }catch (ArithmeticException e){
                        //this deals with saturation
                        srpnStack.push(2147483647);
                    }
                    break;

                case '-' :
                    try{
                        calculationResult = Math.subtractExact(operand_1, operand_2);
                        srpnStack.push(calculationResult);
                    } catch (ArithmeticException e){
                        //this deals with negative saturation
                        srpnStack.push(-2147483648);
                     }
                    break;

                case '*' :
                    //multiply operand 1 and operand 2 together
                    calculationResult = operand_1 * operand_2;
                    srpnStack.push(calculationResult);
                    break;

                case '/' :
                    //only divide operand 1 by operand 2 if operand 2 is not equal to 0.
                    if (operand_2 == 0){
                        System.out.println("Divide by 0.");
                    } else {
                        calculationResult = operand_1 / operand_2;
                        srpnStack.push(calculationResult);
                    }
                    break;

                case '%' :
                    calculationResult = operand_1 % operand_2;
                    srpnStack.push(calculationResult);
                    break;

                case '^' :
                    //return items to stack if exponent is negative
                    if (operand_2 < 0){
                        srpnStack.push(operand_1);
                        srpnStack.push(operand_2);
                        System.out.println("Negative power.");
                    }else{
                        //perform power calculation where operand 1 is the number and operand 2 is the exponent
                        calculationResult =(int) Math.pow(operand_1, operand_2);
                        srpnStack.push(calculationResult);
                    }
                    break;
            }
        }
        // this displays the stack if d is entered into the program
        else if (input == 'd') {
            srpnStack.displayStack();
        }
        // this shows the top value of the stack ie the result of the calculation
        else if(input == '='){
            System.out.println(srpnStack.viewTopValue());
        }
        // this gets a random number
        else if (input == 'r'){
            //calls the retrieveRandom method from the Random Class.
            srpnStack.push(randomNumber.retrieveRandom());
        }
        // only other possibility is if the inputted data is numbers or an unknown letter
        else{
            try {
                //needed to use Integer.parseInt(s) as when i used (input) a different number would be passed in
                // for example push(1) would make 49 be added to the stack.
                // this will only run if a number is inputted
                srpnStack.push(Integer.parseInt(s));
            } catch (Exception e){
                //notifies user if the command is not recognised
                System.out.println("Unrecognised operator or operand \"" + s + "\".");
            }
        }
    }

/*
Main method of the class
 */
    public static void main(String[] args) {
        //this is used to create an instance of the class so that the main method can be static.
        SRPN sprn = new SRPN();
        //a class that is used to read the data. It reads everything and stores it in reader.
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //tries to process the command
        try {
            //Keep on accepting input from the command-line
            while(true) {
                //reads the line and stores it as command
                String command = reader.readLine();
                //Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                if(command == null) {
                  //Exit code 0 for a graceful exit
                  System.exit(0);
                }
                //Otherwise, (attempt to) process the character
                sprn.splitUpCommand(command);
            }
        } catch(IOException e) {
          System.err.println(e.getMessage());
          System.exit(1);
        }
    }
}
