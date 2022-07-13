package com.devsuperior.dsmeta.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Transactional
	public List<SaleDTO> findSales() {
		List<Sale> sales = repository.findAll();
		return sales.stream().map(x -> mapper.map(x, SaleDTO.class)).collect(Collectors.toList());
	}
}
