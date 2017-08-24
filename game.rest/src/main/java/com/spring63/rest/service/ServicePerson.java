package com.spring63.rest.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.spring63.rest.dao.IDaoPerson;
import com.spring63.rest.model.ModelPerson;

@Service("serviceperson")
public class ServicePerson implements IServicePerson {
    
    // SLF4J Logging
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    @Qualifier("daoperson")
    IDaoPerson dao;
    
    @Override
    public int login(ModelPerson person) {
        int result = -1;
        try {
            result = dao.login(person);
        } catch (Exception e) {
            logger.error("login" + e.getMessage() );
        }
        return result;
    }
    
    @Override
    public List<ModelPerson> getPersonList(ModelPerson person) {
        List<ModelPerson> result=null;
        try {
            result = dao.getPersonList(person);
        } catch (Exception e) {
            logger.error("getPersonList" + e.getMessage() );
        }
        return result;
    }
    
    @Override
    public int insertPerson(ModelPerson person) {
        int result = -1;
        try {
            result = dao.insertPerson(person);
        } catch (Exception e) {
            logger.error("insertPerson" + e.getMessage() );
        }
        return result;
    }

    @Override
    public int insertPersonList(List<ModelPerson> persons) {
        int result = -1;
        try {
            result = dao.insertPersonList(persons);
        } catch (Exception e) {
            logger.error("insertPersonList" + e.getMessage() );
        }
        return result;
    }
}
