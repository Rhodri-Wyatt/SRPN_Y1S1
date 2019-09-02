/**
 * Class is used for template of stack.
 * stack will be used to store the operands used in the Reverse Polish Notation Calculator
 * Methods in stack are : push, pop, viewTopValue and displayStack
 */

public class Stack{
    // define stack as array of type integer.
    private int[] arrayStack;
    // define a pointer to store the current position of the stack.
    private int pointer;

    /*
    Constructor for stack.
    Creates the ability to instatiates the object in a different class so that it can be called in that class.
    @param stackSize
   */
    public Stack(int stackSize){
        //make array of stackSize
        arrayStack = new int[stackSize];
        //sets pointer to -1 to show stack is empty
        pointer = -1;
    }

    //Accessor to view value of pointer
    public int getPointer(){
        return pointer;
    }

    /*
    Push number to stack
    This method is used to add a value to the top of the stack.
    @param number
    */
    public void push(int number){
        // first check that there is room to add data to the stack
        // remember to include -1 when finding the length as it not take into account the array starting at 0
        if (pointer != (arrayStack.length -1)){
            //increment pointer
            pointer ++;
            // add the new number to the top of the stack
            arrayStack[pointer] = number;
        } else {
            //prints out stac onderflow if the stack is full
            System.out.println("Stack overflow.");
        }
    }


    /*
    Pop the Stack
    Method retrieves a value to top of stack
    @return topValue or 0
    */
    public int pop(){
        // checks that the stack contains a data item.
        // it is checked that the first item isn't a 0 as when an item is removed it is set to 0.
        if (arrayStack[0] != 0) {
            //topValue holds the value of the item being removed from the stack.
            int topValue = arrayStack[pointer];
            arrayStack[pointer] = 0;
            //decrements stack Pointer
            pointer--;
            return topValue;
        } else {
            //This is used to show that there is no data to be removed from the stack.
            // Stack is empty but you are trying to pop something out.
            System.out.println("Stack underflow.");
            return 0;
        }
    }


    /*
    Method to view the top value of the stack.
    @return arrayStack[pointer] or 0
    */
    public int viewTopValue(){
        if (pointer > -1){
            return arrayStack[pointer];
        } else {
            return 0;
        }
    }

    //Method to display all the items in the stack. this is activated in the program by typing d
    public void displayStack(){
        if (pointer == -1){
            System.out.println("-2147483648");
        }
        //for the values in the pointer, item in array is outputted
        for (int counter = 0; counter <= pointer; counter++){
            System.out.println(arrayStack[counter]);
        }
    }
}