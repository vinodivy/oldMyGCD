package com.gcd.rest.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gcd.bean.InputNumber;



@Repository("NumberDao")
public class NumberDaoImpl implements NumberDao {

	private JdbcTemplate jdbcTemplate;
	
	private static final Logger log = Logger.getLogger(NumberDaoImpl.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<InputNumber> list() {
		log.debug("Retrieving all numbers from database..");
		List<InputNumber> numbers = new ArrayList<InputNumber>();
		try {
			numbers = jdbcTemplate.query("SELECT * FROM InputNumber",
					new BeanPropertyRowMapper<InputNumber>(InputNumber.class));
		} catch (DataAccessException e) {
			log.error("Data Exception occured while retrieving "+e);
		}
		catch(Exception e){
			log.error("Some ether exception "+e);
		}
		return numbers;
	}

	public int push(InputNumber inputNumber) {
		log.debug("Pushing the numbers "+inputNumber.getNumber1()+ " "+inputNumber.getNumber2()+
				" "+inputNumber.getKey()+" to the database.");
		int count = jdbcTemplate.update(
				"INSERT INTO inputnumber (`key`, number1, number2) VALUES (?, ?, ?)", new Object[] {
						inputNumber.getKey(), inputNumber.getNumber1(), inputNumber.getNumber2()});
		return count;
	}
}
