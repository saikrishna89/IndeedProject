package com.polymorphism;

public class App {
	
	public static void main(String args[]) {
		Plant plant = new Plant();
		plant.grow();
		
		Tree tree = new Tree();
		tree.grow();
		
		Plant plant1 = new Tree();
		plant1.grow();
		plant1.doGrow();
		
	}
	
}