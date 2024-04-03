package com.ebit.batchProcessCode.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int year;
	private String make;
	private String model;
	private String trim;
	private String body;
	private String transmission;
	private String vin;
	private String state;
	private String c_condition;
	private String odometer;
	private String color;
	private String interior;
	private String seller;
	private String mmr;
	private String sellingprice;
	private String saledate;
}
