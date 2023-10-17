package br.cin.ufpe.hybridschnorr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Key {
	
	private ArrayList<BigInteger> Entity;

    public Key(){
        Entity = new ArrayList<BigInteger>();
    }
    
    public Key(BigInteger[] keys){
        Entity = new ArrayList<BigInteger>();
        for (int i = 0; i < keys.length; i++){
            Entity.add(keys[i]);
        }
    }
    public Key(String path) throws FileNotFoundException {
        readFromFile(path);
    }

    public BigInteger get(int pos){
        if (pos >= Entity.size()) {
	    System.out.println("Size of key: " + Entity.size()); 	
            throw new IllegalArgumentException("pos more then size");
        }
        return Entity.get(pos);
    }

    public BigInteger set(int pos, BigInteger element){
        if (pos >= Entity.size()) {
            throw new IllegalArgumentException("pos more then size");
        }
        return Entity.set(pos, element);
    }	

    public void readFromFile(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(path));
        Entity = new ArrayList<BigInteger>();
        while (scanner.hasNextBigInteger()){
            Entity.add(scanner.nextBigInteger());
        }
        scanner.close();
    }

    public void writeToFile(String path) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new File(path));
        for (BigInteger element : Entity){
            printWriter.println(element);
        }
        printWriter.close();
    }

}
