package com.gcd.soap.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcd.bean.GCD;

@Repository
@Transactional
public class GCDDaoImpl implements GCDDao {

	private JdbcTemplate jdbcTemplate;

	private static final Logger log = Logger.getLogger(GCDDaoImpl.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int insertGCD(GCD gcd) {
		log.debug("Inserting calculated gcd into database..");
		int count = jdbcTemplate.update(" INSERT INTO gcd(id, gcd, `key`)VALUES(?, ?, ?)",
				new Object[] { gcd.getId(), gcd.getGcd(), gcd.getKey()});
		return count;

	}

	public List<Integer> fetchGCDList() {
		log.debug("Retrieving gcdList data from database..");
		List<Integer> output = new ArrayList<Integer>();
		List<GCD> gcdList = new ArrayList<GCD>();
		try {
			gcdList = jdbcTemplate.query("SELECT * FROM gcd", new BeanPropertyRowMapper<GCD>(GCD.class));

			if (!gcdList.isEmpty()) {
				for (GCD value : gcdList) {
				  output.add(value.getGcd());
				}
			}
			else
			{
				log.debug("There were no data found in GCD tables...");
				output.add(0);
			}

		} catch (DataAccessException e) {
			log.error("Data Exception occured while retrieving " + e);
		} catch (Exception e) {
			log.error("Some ether exception " + e);
		}
		return output;

	}

}
