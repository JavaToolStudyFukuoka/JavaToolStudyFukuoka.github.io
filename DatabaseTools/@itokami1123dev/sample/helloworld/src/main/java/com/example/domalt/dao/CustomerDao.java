package com.example.domalt.dao;

import com.example.domalt.DomaConfig;
import com.example.domalt.entity.Customer;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import java.util.List;
import java.util.Optional;

@Dao(config= DomaConfig.class)
public interface CustomerDao {

    @Select
    List<Customer> selectAll();

    @Select
    Optional<Customer> selectById(Integer id);

    @Insert
    int insert(Customer customer);

    @Update
    int update(Customer employee);

}
