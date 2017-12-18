package com.gcd.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SessionController {

	private static final long DURATION = TimeUnit.MINUTES.toMillis(3); 
	private static final Semaphore semaphore = new Semaphore(20);
	private static Map<String, Long> account = new HashMap<String, Long>();
	private static final Logger log = Logger.getLogger(SessionController.class);

	public void watchUserSession(String key) {
		
		log.debug("key is "+key);

		synchronized (account) {
			for (Iterator<Map.Entry<String, Long>> it = account.entrySet().iterator(); it.hasNext();) 
			{
				Entry<String, Long> entry = it.next();
				if (entry.getKey().equals(key)) {
					account.put(key, System.currentTimeMillis());
					return;
				}
			}
		}

		// hold on until you get the lock
		try {
			log.debug("Fetching lock...");
			semaphore.acquire();
			log.debug("Lock acquired...");
		} catch (InterruptedException e) {
			log.error("No of concurrent users has exceeded 20..");
		}
		synchronized (account) {
			account.put(key, System.currentTimeMillis());
		}
		
		Timer time = new Timer();
		ReleaseLockTask st = new ReleaseLockTask();
		time.schedule(st, 0, 5000);
	}

	static class ReleaseLockTask extends TimerTask {
		public void run() {
			synchronized (account) {
				for (Iterator<Map.Entry<String, Long>> it = account.entrySet().iterator(); it.hasNext();) {
					Entry<String, Long> entry = it.next();
					if ((System.currentTimeMillis() - entry.getValue()) > DURATION) {
						it.remove();
						semaphore.release();
					}
				}
			}

		}
	}

}
