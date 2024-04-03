package com.ebit.batchProcessCode.config;

import org.springframework.batch.item.ItemProcessor;

import com.ebit.batchProcessCode.entity.Car;

import lombok.Data;

@Data
public class CarProcessor implements ItemProcessor<Car, Car> {

	@Override
	public Car process(Car item) throws Exception {
		return item;
	}

}
