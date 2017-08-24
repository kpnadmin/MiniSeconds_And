package com.spring63.rest.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spring63.rest.model.ModelPerson;

public interface IDaoPerson {    
    int login( ModelPerson person);
    List<ModelPerson> getPersonList( ModelPerson person);
    int insertPerson( ModelPerson person);
    
    int insertPersonList(List<ModelPerson> persons);    
}
