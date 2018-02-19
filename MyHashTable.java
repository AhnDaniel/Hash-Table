/*
 * Hash table that implements insert, find, remove, empty, find index, and rehash table.
 * This hash table class has an inner class of MyNode and the hash table will utilize
 * an array of MyNode as its objects
 */
public class MyHashTable<Integer>{
	
	private int tableSize = 11;							// initialize variables
	private int probe = 7;
	private int counter = 0;
	MyNode[] hashTable;
	
	// mutators
	public int getSize(){
		return tableSize;
	}
	
	public void setSize(int val){
		this.tableSize = val;
	}
	
	public int getCount(){
		return counter;
	}
	
	public void setCount(int val){
		this.counter = val;
	}
	
	public int getProbe(){
		return probe;
	}
	
	public void setProbe(int val){
		this.probe = val;
	}
	
	// constructors
	// allocates the array to put array of objects
	public void allocateArray(int tableSize, int probe, int counter){
		hashTable = new MyNode[tableSize];
		this.setSize(tableSize);
		this.setCount(counter);
		this.setProbe(probe);
	}
	
	public void clearTable(){
		for(int i = 0; i < tableSize; i++)
			hashTable[i] = null;
		
		this.counter = 0;
		this.tableSize = 11;
		this.probe = 7;
	}
	
	
	public void printTable(){
		for(int i = 0; i < hashTable.length; i++){
			if(hashTable[i] != null){
				System.out.println(""
						+ "Index: " + i
						+ "\nKey: " + hashTable[i].getKey()
						+ "\nValue: " + hashTable[i].getPayLoad()
						+ "\nMarked for Delete: " + hashTable[i].getMarked()
						+ "\n\n");
			}
		}
	}
	
	// returns the index after hashing
	public int hash(MyNode n){						// basic hash function given a NODE
		return n.getKey() % tableSize;
	}

	public int hash(int key){						// basic hash function given a KEY
		return key % tableSize;
	}
	
	// inserts a node into the hash table
	public void insertNode(MyNode n){
		
		int location = hash(n);
		// after hashing, check for collisions and if the value already exists
		if(hashTable[location]  == null){
			hashTable[location] = n;
			counter++;
		}
		else
			if(hashTable[location].getKey() == n.getKey()){
				System.out.println("That specific key is already in the hash table. Try again.\n\n");
			}
			else
			{
				if(hashTable[location].getMarked() == true){
					hashTable[location] = n;
					counter++;
				}
				else{
					int i = 1;
					secondHash(n, location, i);
				}
			}
	}
	
	// second hash function that only applies if there is a collision when inserting
	public void secondHash(MyNode node, int location,int i){
		int probing = ((i * (probe - (node.getKey() % probe))) + location) % tableSize;
		
		if(probing == location){										// the program comes full circle to original hash index
			System.out.println("This node could not be entered into the hash table because of too many collisions.\n");
		}
		else{
			if(hashTable[probing] == null){
				hashTable[probing] = node;
				counter++;
			}
			else{
				if(hashTable[probing].getKey() == node.getKey() && hashTable[probing].getPayLoad() == node.getPayLoad()){			// element already exists in the table (moved along after collisions)
					System.out.println("This element is already in the table, it has just been moved"
							+ "due to collisions.\n\n");
				}
				else{														// not same element and another collision, increment 'i'
					secondHash(node, location, i + 1);
				}
			}
		}
	}
	
	
	// finds the Node given a key; prints both the key and value of the node
	public void retrieveNode(int key){
		int j = 1;						// if it gets through the array without finding the key
		for(int i = 0; i < hashTable.length; i++){
			if(hashTable[i] != null){
				if(hashTable[i].getKey() == key){
					System.out.println("Key: " + key
							+ "\nValue: " + hashTable[i].getPayLoad());
					break;
				}
			}
			j = i + 1;
		}
		

		if(j == hashTable.length)				
			System.out.println("The key you are searching for does not exist.\n\n");
	}
	
	// "delete a node" AKA mark for deletion
	public void markNode(int key){
		int j = 1;
		for(int i = 0; i < hashTable.length; i++){
			if(hashTable[i] != null){
				if(hashTable[i].getKey() == key){
					hashTable[i].setMarked(true);
					System.out.println("Node has been successfully marked for deletion:\n\n");
					break;
				}
			}
			j = i + 1;
		}
		
		if(j == hashTable.length)			// if the end of array is reached and key is not found
			System.out.println("The node you are trying to remove does not exist in the table.\n\n");
	}
	
	public void findNode(int key){
		int j = 1;
		for(int i = 0; i < hashTable.length; i++){
			if(hashTable[i] != null){
				if(hashTable[i].getKey() == key){
					System.out.println("The index of the element with the key of " + key + " is " + i);
					break;
				}
			}
			j = i + 1;
		}
		
		if(j == hashTable.length)			// if the end of array is reached and key is not found
			System.out.println("That key does not exist in the table.\n\n");
	}
	
	//rehashes the table only upon user command. It temporarily stores the data into a temp array,
	//re-declares an array of hashTable with the same name as the original, and then hashes all
	//the elements in the temp array back into the new hashTable array.
	public void rehash(){
		
		int newSize = tableSize * 2;
		int newProbe = probe * 2;
		int newCounter = 0;
		
		MyNode[] temp = new MyNode[tableSize];						// copy array into temp array
		for(int i = 0; i < hashTable.length; i++){
			if(hashTable[i] != null){
				MyNode n = new MyNode(hashTable[i].getKey(), hashTable[i].getPayLoad(), hashTable[i].getMarked());
				temp[i] = n;
			}
		}
		
		// readjusts values
		this.setSize(newSize);
		this.setCount(newCounter);
		this.setProbe(newProbe);
		this.allocateArray(this.getSize(), this.getProbe(), this.getCount());	// reallocates the hashTable array
		
		
		for(int j = 0; j < temp.length; j++){						// hash Nodes in temp into new hashTable array
			if(temp[j] != null){
				if(temp[j].markedForDelete == false){
					MyNode node = new MyNode(temp[j].getKey(), temp[j].getPayLoad(), temp[j].getMarked());
					insertNode(node);
				}
			}
		}		
	}
	

	
	/*
	 * This is a subclass of MyHashTable that will define the node. The node will contain the key, value (payload),
	 * and whether it is marked for deletion. 
	 */
	public static class MyNode<Integer>{
		private int key;
		private int payLoad;
		private boolean markedForDelete;
		
		public MyNode(int key, int payLoad, boolean marked){
			this.key = key;
			this.payLoad = payLoad;
			this.markedForDelete = marked;
		}
		
		public int getKey(){
			return this.key;
		}
		
		public void setKey(int key){
			this.key = key;
		}
		
		public int getPayLoad(){
			return this.payLoad;
		}
		
		public void setPayLoad(int payLoad){
			this.payLoad = payLoad;
		}
		
		public boolean getMarked(){
			return this.markedForDelete;
		}
		
		public boolean setMarked(boolean marked){
			return this.markedForDelete = marked;
		}
	}
}
