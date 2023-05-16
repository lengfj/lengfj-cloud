package com.lengfj.cloud.leaf;

import com.lengfj.cloud.leaf.core.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
