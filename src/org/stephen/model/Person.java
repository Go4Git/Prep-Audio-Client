package org.stephen.model;

import java.io.Serializable;

/**
 * A dummy class representing a person for
 * testing purposes.
 * @author Stephen Andrews
 */
public class Person implements Serializable {

	private String name;
	
	private int age;
	
	private double income;
	
	public Person(String name, int age, double income) {
		this.name = name;
		this.age = age;
		this.income = income;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public double getIncome() {
		return income;
	}
	
	public void setIncome(double income) {
		this.income = income;
	}
	
	@Override
	public String toString() {
		return "Person [" + name + ", " + age + ", " + income + "]";
	}
}
