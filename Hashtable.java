//Hashtable class
//Contains various public and private method to complete the required tasks for
//assignment four

//Liam Barclay
//ID : 1268723


public class Hashtable
{
	//Declare class variables
	private int size;
	private String strategy;
	private hashNode[] table;
	
	private float load;
	private int items;
	
	private float hashCount;
	private float putGetCount;
	
	//Constructor for the hashtable, takes two arguments
	//Arg1 -> size of the hashtable
	//Arg2 -> collision resolution strategy to follow.
	public Hashtable(int s, String strat)
	{
		size = s;
		strategy = strat.toUpperCase();
		table = new hashNode[size];
		load = 0;
		items = 0;
		hashCount = 0;
		putGetCount = 0;
	}
	
	//Method to put a value with key k and data s into the hashtable
	public void put(int k, String s)
	{
		//Checks if the load is over 80%
		if(load > 0.80)
		{
			System.err.println("LOAD OVER 80");
		}
		//increases items and put/get count by 1
		items++;
		putGetCount++;
		//gets the initial hashcode for the key
		int hc = hash(k);
		//Makes a new hash node to store in the array.
		hashNode node = new hashNode(k, s);
		
		//if the position at the hashcode is not null
		if(table[hc] != null)
		{
			//follow one of two collision resolution strategies
			if(strategy.equals("K"))
			{
				hc = rehashKey(k, hc);
			}
			else if(strategy.equals("L"))
			{
				hc = rehashLinear(k, hc);
			}
			
		}
		//Insert the node at the position of the hashcode
		table[hc] = node;
	}

	//Method to get a data value from the hashtable with the key k.
	public String get(int k)
	{
		putGetCount++;
		int hc = hash(k);
		
		//If the key at the initial hashcode is not k	
		if(table[hc].getKey() != k)
		{
			//Follow one of two collision resolution strategies
			if(strategy.equals("K"))
			{
				hc = rehashKey(k, hc);
			}
			else if(strategy.equals("L"))
			{
				hc = rehashLinear(k, hc);
			}
		}
		//return the data at the hashcode position in the hashtable
		return table[hc].getData();
	}
	

	//Method to rehash using a linear probing technique
	//Takes a key k and the initial hashcode
	private int rehashLinear(int k, int hc)
	{
		int hash = hc;
		//While the position in the hashtable at hash is not null nor the value we want
		while(table[hash] != null && table[hash].getKey() != k)
		{
			//Add one to the hash count and hash
			hashCount++;
			hash++;

			//If the hash value exceeds the size of the hashtable, reset hash to start of array
			if(hash >= size)
			{
				hash = 0;
			}
		}
		//Return this hash code
		return hash;
	}
	

	//Method to rehash using a key offset collision resolution method
	//method take the key value and the initial hashcode
	private int rehashKey(int k, int hc)
	{
		int hash = hc;
		//While the position is not null and the value is not want we want
		while(table[hash] != null && table[hash].getKey() != k)
		{
			hashCount++;
			//get the new hashcode by adding the quotient of k/size
			hash += (k / size);
			//While the hash code exceeds the size, take away the size of the table
			//This ensures the hash code is inside the size of the table
			while(hash >= size)
			{
				hash -= size;
			}
		}
		//Return the hash code
		return hash;
	}
	

	//Method to return the current performance of the hashtable
	//Returns a float which represents the ratio of hashes to inserts and gets
	public float performance()
	{
		//to ensure we don't divide by zero
		if(putGetCount != 0)
		{	
			//return the ration of hash count to put/get count.
			return hashCount/putGetCount;
		}
		else
		{
			return 0;
		}
	}
	
	//Method to return the load value of the table
	public float load()
	{	
		//calculates load to be items/size and stores this in the hashtable
		load = items/size;
		//also returns this value
		return load;
	}
	
	//Method to calculate the initial hash code for a given key, k.
	public int hash(int k)
	{
		//Increase hashcount by 1
		hashCount++;
		//return the hashcode which is the key modulo the size of the table
		return (k % size);
	}
	

	//The class hashNode which can store both a interger value, key, and a string data.
	private class hashNode
	{	
		//The class variables for a hash node
		private int key;
		private String data;
		
		//Constructor for the hashnode
		hashNode(int k, String d)
		{
			key = k;
			data = d;
		}
		
		//Public getter method for the data and the key of a hash node
		public int getKey()
		{
			return key;
		}
		
		public String getData()
		{
			return data;
		}	
		
	}
	
	
	
}
