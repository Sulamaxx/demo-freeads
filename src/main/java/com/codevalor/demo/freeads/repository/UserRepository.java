package com.codevalor.demo.freeads.repository;

import com.codevalor.demo.freeads.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

}
