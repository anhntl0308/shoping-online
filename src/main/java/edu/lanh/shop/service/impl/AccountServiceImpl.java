package edu.lanh.shop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.lanh.shop.domain.Account;
import edu.lanh.shop.repository.AccountReponsitory;
import edu.lanh.shop.service.AccountService;


@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountReponsitory accountReponsitory;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public <S extends Account> S save(S entity) {
		entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
		return accountReponsitory.save(entity);
	}

	@Override
	public <S extends Account> Optional<S> findOne(Example<S> example) {
		return accountReponsitory.findOne(example);
	}

	@Override
	public List<Account> findAll() {
		return accountReponsitory.findAll();
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return accountReponsitory.findAll(pageable);
	}

	@Override
	public List<Account> findAll(Sort sort) {
		return accountReponsitory.findAll(sort);
	}

	@Override
	public List<Account> findAllById(Iterable<String> ids) {
		return accountReponsitory.findAllById(ids);
	}

	@Override
	public Optional<Account> findById(String id) {
		return accountReponsitory.findById(id);
	}

	@Override
	public <S extends Account> List<S> saveAll(Iterable<S> entities) {
		return accountReponsitory.saveAll(entities);
	}

	@Override
	public void flush() {
		accountReponsitory.flush();
	}

	@Override
	public boolean existsById(String id) {
		return accountReponsitory.existsById(id);
	}

	@Override
	public <S extends Account> S saveAndFlush(S entity) {
		return accountReponsitory.saveAndFlush(entity);
	}

	@Override
	public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
		return accountReponsitory.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
		return accountReponsitory.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Account> entities) {
		accountReponsitory.deleteInBatch(entities);
	}

	@Override
	public <S extends Account> long count(Example<S> example) {
		return accountReponsitory.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Account> entities) {
		accountReponsitory.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return accountReponsitory.count();
	}

	@Override
	public <S extends Account> boolean exists(Example<S> example) {
		return accountReponsitory.exists(example);
	}

	@Override
	public void deleteById(String id) {
		accountReponsitory.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<String> ids) {
		accountReponsitory.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Account entity) {
		accountReponsitory.delete(entity);
	}

	@Override
	public <S extends Account, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return accountReponsitory.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		accountReponsitory.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		accountReponsitory.deleteAllInBatch();
	}

	@Override
	public Account getOne(String id) {
		return accountReponsitory.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Account> entities) {
		accountReponsitory.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		accountReponsitory.deleteAll();
	}

	@Override
	public Account getById(String id) {
		return accountReponsitory.getById(id);
	}

	@Override
	public Account getReferenceById(String id) {
		return accountReponsitory.getReferenceById(id);
	}

	@Override
	public <S extends Account> List<S> findAll(Example<S> example) {
		return accountReponsitory.findAll(example);
	}

	@Override
	public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
		return accountReponsitory.findAll(example, sort);
	}
	
	
}
