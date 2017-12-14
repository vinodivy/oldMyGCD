package com.gcd.rest.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.gcd.bean.InputNumber;

@Component("producer")
public class MessageSender {

	@Autowired
	JmsTemplate jmsTemplate;
	
	private static final Logger logger = Logger.getLogger(MessageSender.class);

	public void send(final InputNumber number) {
		logger.debug("Sending the message from producer class..");

		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(number);
			}
		});

	}

}