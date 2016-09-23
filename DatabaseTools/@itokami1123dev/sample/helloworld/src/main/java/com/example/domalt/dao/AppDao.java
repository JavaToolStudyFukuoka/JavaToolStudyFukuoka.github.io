package com.example.domalt.dao;

import com.example.domalt.DomaConfig;
import org.seasar.doma.Dao;
import org.seasar.doma.Script;

@Dao(config = DomaConfig.class)
public interface AppDao {
    @Script
    void create();

    @Script
    void drop();
}
