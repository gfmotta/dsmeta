package com.devsuperior.dsmeta.services;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.devsuperior.dsmeta.services.exceptions.DatabaseException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

	@Value("${twilio.sid}")
	private String twilioSid;

	@Value("${twilio.key}")
	private String twilioKey;

	@Value("${twilio.phone.from}")
	private String twilioPhoneFrom;

	@Value("${twilio.phone.to}")
	private String twilioPhoneTo;

	@Autowired
	private SaleRepository saleRepository;

	public void sendSms(Long id) {
		Sale sale = saleRepository.findById(id).orElseThrow(
				() -> new DatabaseException("Não foi possível realizar a operação. Registro não encontrado!"));

		Twilio.init(twilioSid, twilioKey);

		PhoneNumber to = new PhoneNumber(twilioPhoneTo);
		PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

		Message message = Message.creator(to, from,
				String.format("O vendedor %s foi destaque em %s com um total de R$ %.2f em vendas! ",
						sale.getSellerName(), sale.getDate().format(DateTimeFormatter.ofPattern("MM/yyyy")),
						sale.getAmount()))
				.create();

		System.out.println(message.getSid());
	}
}
