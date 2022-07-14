package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.devsuperior.dsmeta.services.exceptions.DatabaseException;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Autowired
	private ModelMapper mapper;

	@Transactional
	public Page<SaleDTO> findSales(String minDate, String maxDate, Pageable pageable) {
		try {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

			LocalDate min = minDate.equals("") ? today.minusYears(1) : LocalDate.parse(minDate);
			LocalDate max = maxDate.equals("") ? today : LocalDate.parse(maxDate);

			Page<Sale> sales = repository.findSales(min, max, pageable);
			
			return sales.map(x -> mapper.map(x, SaleDTO.class));
		} catch (RuntimeException e) {
			throw new DatabaseException("Não foi possível realizar a operação. Por favor tente novamente");
		}
	}
}
