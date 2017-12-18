package com.gcd.rest.service;

import java.util.List;

import com.gcd.bean.InputNumber;

public interface NumberService {
	public List<InputNumber> list();

	public void push(Integer i1, Integer i2, String key);
}
