import java.util.Scanner;

// This program will not convert doubles such as 2.0 or 3.0 into integers. User is required to enter an integer value
public class Main {

	public static void main(String[] args) {
		
		System.out.println("Welcome to this hash table. What operation would you like to do? ");
		
		int user; 				// variable to hold user input
		int key;
		int payLoad;
		MyHashTable table = new MyHashTable();
		MyHashTable temp = new MyHashTable();
		
		table.allocateArray(table.getSize(), table.getProbe(), table.getCount());
		
		//sets the initial table size to 11
		//table.setSize(11);
		
		/*
		 * This do-while function continuously prompts the user to input a value that is correct:
		 * (0 - 7). If the value input is an integer but not one of the 8 options, it will register
		 * as an incorrect input and re-prompt the user for correct input until this problem is solved.
		 * If the user enters a 0, the program will terminate.
		 */
		do{
			menu();
			Scanner input = new Scanner(System.in);
			
			
			//TESTING**************************************************************
			System.out.print(table.getSize() + "\n"
					+ table.getCount() + "\n"
					+ table.getProbe());
			
			while(!input.hasNextInt()){
				System.out.println("Invalid input. You must enter an integer that corresponds to"
					+ "the operation desired: (1-7, press 0 to exit the function): ");
				menu();
				input = new Scanner(System.in);
			}
			
			user = input.nextInt();
			
			// switches based on user input
			switch(user){
			case 0:
				user = 0;
				break;
			case 1:
				System.out.println("Now printing the elements stored in the hash table:\n");
				if(table.getCount() == 0)
					System.out.println("The table is currently empty. There are no elements to print out.\n\n");
				else{
				table.printTable();}
				break;
			case 2:
				System.out.print("You have decided to insert an element.\n");
				key = verifyInput(0);
			    payLoad = verifyInput(1);
			    int loadFactor = table.getSize() - table.getCount();
			    
			    if(loadFactor == 0){									// table is currently full
					System.out.println("The hash table is currently full. Please select the option to rehash.\n\n");
				}
			    else{
			    MyHashTable.MyNode node = new MyHashTable.MyNode(key, payLoad, false);
			    table.insertNode(node);
			    }
				break;
			case 3:
				if(table.getCount() == 0)
					System.out.println("You cannot retrieve anything because the table is empty. \n");
				else{
					System.out.print("You have decided to retrieve the value of an element.\n");
					key = verifyInput(0);
					table.retrieveNode(key);
				}
				break;
			case 4:
				if(table.getCount() == 0)
					System.out.println("You cannot mark anything for deletion because the table is empty.\n");
				else{
					System.out.print("You have decided to remove an element.\n");
					key = verifyInput(0);
					table.markNode(key);
				}
				break;
			case 5:
				System.out.println("The table will now be cleared. \n");
				table.clearTable();
				break;
			case 6:
				if(table.getCount() == 0)
					System.out.println("You cannot search for an index because the table is empty. \n");
				else{
					System.out.println("Enter the key you wish to search for: ");
					key = verifyInput(0);
					table.findNode(key);
				}
				break;
			case 7:
				if(table.getCount() == 0)
					System.out.println("The table is empty so there is no need to rehash. \n");
				else{
					table.rehash();
					System.out.println("Table has been successfully re-hashed.\n\n");
				}
				break;
			default:	// takes care of when user input is an integer but not within the bounds (0 - 7)
				System.out.println("Invalid input. You must enter a value that corresponds to one of the options (0-7"
						+ " or 0 to exit the program)");
				user = 8;
			}
		
		}while(user != 0);
		
		System.out.println("This program has terminated.");
	}
	
	// menu function that prompts for user input
	public static void menu(){
		
		System.out.println(
				  "\n1. Print the elements in the collection:\n"
				+ "2. Insert:\n"
				+ "3. Retrieve:\n"
				+ "4. Remove:\n"
				+ "5. Empty the hash table:\n"
				+ "6. Find index of an element:\n"
				+ "7. Rehash the table:\n"
				+ "0. Exit\n");
		
		
	}
	
	/*
	 * This method verifies the input to see if the key/payLoad entered is an integer value
	 */
	public static int verifyInput(int i){
		// input for key
		if(i == 0){
			System.out.println("Please enter the key: ");
			Scanner input = new Scanner(System.in);

			while(!input.hasNextInt()){
				System.out.println("Invalid input. The value of the key must be an integer. "
						+ "Please try again: ");
				input = new Scanner(System.in);
			}
			
			int key = input.nextInt();
			
			return key;
		}
		
		// input for payLoad
		if(i == 1){
			System.out.println("Please enter the payload value: ");
			
			Scanner input = new Scanner(System.in);
			
			while(!input.hasNextInt()){
				System.out.println("Invalid input. The value of the payload must be an integer. "
						+ "Please try again: ");
				input = new Scanner(System.in);
			}
			
			int payLoad = input.nextInt();
			
			return payLoad;
		}
		
		return 0;
	}
	
}
