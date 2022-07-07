package edu.lanh.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.lanh.shop.domain.Account;

@Repository
public interface AccountReponsitory extends JpaRepository<Account,String>{

}
