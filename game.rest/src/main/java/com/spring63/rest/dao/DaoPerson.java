package com.spring63.rest.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.spring63.rest.model.ModelPerson;

@Repository("daoperson")
public class DaoPerson implements IDaoPerson {
    // SLF4J Logging
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    @Qualifier("sqlSession")
    SqlSession session;
    
    @Override
    public int login(ModelPerson person) {
        return session.selectOne("mapper.mysql.mapperPerson.login", person);
    }
    
    @Override
    public List<ModelPerson> getPersonList(ModelPerson person) {
        return session.selectList("mapper.mysql.mapperPerson.getPersonList", person);
    }
    
    @Override
    public int insertPerson(ModelPerson person) {
        return session.insert("mapper.mysql.mapperPerson.insertPerson", person);
    }

    @Override
    public int insertPersonList(List<ModelPerson> persons) {
        return session.insert("mapper.mysql.mapperPerson.insertPersonList", persons);
    }
}
