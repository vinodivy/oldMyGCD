package com.gcd.soap.dao;

import java.util.List;

import com.gcd.bean.GCD;

public interface GCDDao {
	
	public int insertGCD(GCD gcd);
	public List<Integer> fetchGCDList();

}
