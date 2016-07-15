package com.example.domalt;

import org.seasar.doma.SingletonConfig;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import org.seasar.doma.jdbc.tx.TransactionManager;

import javax.sql.DataSource;

@SingletonConfig // しんぐるとん
public class DomaConfig implements Config{
    private static final DomaConfig CONFIG = new DomaConfig();

    // SQLの方言を設定
    private final Dialect dialect;

    // ローカルトランザクションを設定するデータソース
    private final LocalTransactionDataSource dataSource;

    // ローカルトランザクションを管理する
    private final TransactionManager transactionManager;

    // シングルトンなのでprivateなコンストラクタ
    private DomaConfig(){
        dialect = new H2Dialect(); // H2DataBaseの方言を設定
        dataSource = new LocalTransactionDataSource(
                "jdbc:h2:~/test", null, null);
        transactionManager = new LocalTransactionManager(
                dataSource.getLocalTransaction(getJdbcLogger()));
    }

    @Override
    public Dialect getDialect(){
        return dialect;
    }

    @Override
    public DataSource getDataSource(){
        return dataSource;
    }

    @Override
    public TransactionManager getTransactionManager(){
        return transactionManager;
    }

    public static DomaConfig singleton(){
        return CONFIG;
    }
}
