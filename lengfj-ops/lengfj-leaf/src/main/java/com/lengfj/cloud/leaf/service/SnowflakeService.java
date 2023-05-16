package com.lengfj.cloud.leaf.service;

import com.lengfj.cloud.common.core.util.SpringContextUtil;
import com.lengfj.cloud.leaf.core.common.Constants;
import com.lengfj.cloud.leaf.core.common.Result;
import com.lengfj.cloud.leaf.core.common.ZeroIDGen;
import com.lengfj.cloud.leaf.core.snowflake.SnowflakeIDGenImpl;
import com.lengfj.cloud.leaf.IDGen;
import com.lengfj.cloud.leaf.exception.InitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("SnowflakeService")
public class SnowflakeService {
    private Logger logger = LoggerFactory.getLogger(SnowflakeService.class);

    private IDGen idGen;

    public SnowflakeService() throws InitException {
        boolean flag = Boolean.parseBoolean(SpringContextUtil.getProperty(Constants.LEAF_SNOWFLAKE_ENABLE));
        if (flag) {
            String zkAddress = SpringContextUtil.getProperty(Constants.LEAF_SNOWFLAKE_ZK_ADDRESS);
            int port = Integer.parseInt(SpringContextUtil.getProperty(Constants.LEAF_SNOWFLAKE_PORT));
            idGen = new SnowflakeIDGenImpl(zkAddress, port);
            if(idGen.init()) {
                logger.info("Snowflake Service Init Successfully");
            } else {
                throw new InitException("Snowflake Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            logger.info("Zero ID Gen Service Init Successfully");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }
}
