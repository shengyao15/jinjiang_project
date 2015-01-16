package com.jje.membercenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.InitTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class DataPrepareFramework extends InitTestEnvironment{
    @Autowired
    DataSource dataSource;
    SimpleJdbcTemplate template;

    private Logger logger = LoggerFactory.getLogger(DataPrepareFramework.class);

    @Before
    public void setUp() throws IOException {
        template = new SimpleJdbcTemplate(dataSource);
        
        createSequence();
        String content = sqlForThisTest();
        if (content.equals("")) {
            return;
        }
        
        String[] sqlLines = content.split(";");
        for (int i = 0; i < sqlLines.length; i++) {
            String sql = sqlLines[i];

            if(0==sql.trim().length()){
            	continue;
            }
            template.update(sql);
            logger.debug(sql);
        }
    }

    void createSequence() {
        template = new SimpleJdbcTemplate(dataSource);
        dropSequence();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        try {
            template.update("CREATE SEQUENCE S_ABP_" + sd.format(new Date())
                    + " INCREMENT BY 1 START WITH 1",
                    new HashMap<String, String>());
            template.update("CREATE SEQUENCE S_HBP_" + sd.format(new Date())
                    + " INCREMENT BY 1 START WITH 1",
                    new HashMap<String, String>());
            template.update("CREATE SEQUENCE S_TBP_" + sd.format(new Date())
                    + " INCREMENT BY 1 START WITH 1",
                    new HashMap<String, String>());
        } catch (Exception e) {

        }
    }

    private String sqlForThisTest() throws IOException {
        String sqlName = getClass().getSimpleName() + ".sql";
        InputStream stream = getClass().getResourceAsStream(sqlName);
        if (stream == null) {
            return "";
        }
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream,"UTF-8"));
        StringBuilder buffer = new StringBuilder();
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } finally {
            reader.close();
        }
        return buffer.toString();
    }

    @After
    public void dropSequence() {
        try{
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
            template.update("DROP SEQUENCE S_ABP_" + sd.format(new Date()));
            template.update("DROP SEQUENCE S_HBP_" + sd.format(new Date()));
            template.update("DROP SEQUENCE S_TBP_" + sd.format(new Date()));
        }catch(Exception e){
            
        }
    }
}
