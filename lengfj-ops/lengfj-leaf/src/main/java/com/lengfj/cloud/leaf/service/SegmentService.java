package com.lengfj.cloud.leaf.service;

import com.lengfj.cloud.leaf.core.common.Result;
import com.lengfj.cloud.leaf.core.segment.SegmentIDGenImpl;
import com.lengfj.cloud.leaf.core.segment.dao.impl.IDAllocDaoImpl;
import com.lengfj.cloud.leaf.IDGen;
import com.lengfj.cloud.leaf.exception.InitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;

@Service("SegmentService")
public class SegmentService {
    private Logger logger = LoggerFactory.getLogger(SegmentService.class);

    private IDGen idGen;

    public SegmentService(DataSource dataSource) throws SQLException, InitException {
        //config dao
        IDAllocDaoImpl dao = new IDAllocDaoImpl(dataSource);

        //config ID Gen
        idGen = new SegmentIDGenImpl();
        ((SegmentIDGenImpl)idGen).setDao(dao);
        if (idGen.init()) {
            logger.info("Segment Service Init Successfully");
        } else {
            throw new InitException("Segment Service Init Fail");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }

    public SegmentIDGenImpl getIdGen() {
        if (idGen instanceof SegmentIDGenImpl) {
            return (SegmentIDGenImpl) idGen;
        }
        return null;
    }
}
