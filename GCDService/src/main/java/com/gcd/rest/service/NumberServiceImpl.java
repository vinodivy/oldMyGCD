package com.gcd.rest.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcd.bean.InputNumber;
import com.gcd.rest.dao.NumberDao;
import com.gcd.rest.messaging.MessageSender;

@Service("NumberService")
public class NumberServiceImpl implements NumberService {
	
	@Autowired
	private NumberDao numberDao;
	
	@Autowired
	private MessageSender messageSender;
	
	private static final Logger log = Logger.getLogger(NumberServiceImpl.class);

	public List<InputNumber> list() {
		log.debug("Invoking the Number service list method..");
		List<InputNumber> numberList = numberDao.list();
		return numberList;
	}

	public void push(Integer i1, Integer i2, String key) {
		log.debug("Invoking the Number service push method..");				
		
		InputNumber number = new InputNumber();
		number.setNumber1(i1);
		number.setNumber2(i2);
		numberDao.push(number);	
		
		log.info("Pushing the numbers to Queue...");		
		messageSender.send(number);	
	}
}