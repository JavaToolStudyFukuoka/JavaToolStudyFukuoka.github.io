package com.example.domalt;

import com.example.domalt.dao.AppDao;
import com.example.domalt.dao.AppDaoImpl;
import com.example.domalt.dao.CustomerDao;
import com.example.domalt.dao.CustomerDaoImpl;
import com.example.domalt.entity.Customer;
import org.seasar.doma.jdbc.tx.TransactionManager;

import java.sql.*;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        jdbcH2Sample();
        domaH2Sample();
    }

    private static void domaH2Sample() {

        AppDao appDao = new AppDaoImpl();

        TransactionManager tm = DomaConfig
                .singleton()
                .getTransactionManager();

        tm.required(()->{
            appDao.create();
        });

        CustomerDao customerDao = new CustomerDaoImpl();
        tm.required(() -> {
            List<Customer> customers =
                    customerDao.selectAll();

            customers
                    .stream()
                    .forEach(System.out::println);
        });

        tm.required(() -> {
                    customerDao
                            .selectById(1)
                            .ifPresent(System.out::println);

        });

        tm.required(() -> {
            Customer customer = new Customer();
            customer.name = "おれおれ";
            int resultCnt = customerDao.insert(customer);

            System.out.format("登録件数=%d \n", resultCnt);

            customerDao
                    .selectAll()
                    .stream()
                    .forEach(System.out::println);
        });

        tm.required(() -> {
            customerDao
                    .selectById(1)
                    .ifPresent((customer) -> {
                        customer.name = "hoge";
                        int resultCnt = customerDao.update(customer);
                        System.out.format("UPDATE cnt=%d \n", resultCnt);
                    });

            customerDao
                    .selectById(1)
                    .ifPresent(System.out::println);
        });

        tm.required(()->{
            appDao.drop();
        });
    }

    private static void jdbcH2Sample() {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select 'hello world h2 ooo' from dual");
            rs.first();
            System.out.println(rs.getString(1));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
