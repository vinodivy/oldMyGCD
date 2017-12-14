package com.gcd.rest.dao;

import java.util.List;

import com.gcd.bean.InputNumber;

public interface NumberDao {
	
	public List<InputNumber> list();

	public int push(InputNumber number);
}