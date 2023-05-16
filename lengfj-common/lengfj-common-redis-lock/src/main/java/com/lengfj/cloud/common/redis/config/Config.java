package com.lengfj.cloud.common.redis.config;

import lombok.Data;

/**
 * @Author by lester
 * @Date 2020/6/12
 * @Desc redisson配置
 */
@Data
public class Config {

	private SentinelServersConfig sentinelServersConfig;

	private MasterSlaveServersConfig masterSlaveServersConfig;

	private SingleServerConfig singleServerConfig;

	private ClusterServersConfig clusterServersConfig;

	private ReplicatedServersConfig replicatedServersConfig;

	/**
	 * Threads amount shared between all redis node clients
	 */
//	private int threads = 0; // 0 = current_processors_amount * 2

//	private int nettyThreads = 0; // 0 = current_processors_amount * 2

	/**
	 * Redis key/value codec. JsonJacksonCodec used by default
	 */
//	private ClassProperty codec = new ClassProperty(JsonJacksonCodec.class.getName());

	/**
	 * For codec registry and look up. DefaultCodecProvider used by default
	 */
//	private ClassProperty codecProvider = new ClassProperty(DefaultCodecProvider.class.getName());

	/**
	 * For resolver registry and look up. DefaultResolverProvider used by default
	 */
//	private ClassProperty resolverProvider = new ClassProperty(DefaultResolverProvider.class.getName());

//	private Class<? extends ExecutorService> executor;

	/**
	 * Config option for enabling Redisson Reference feature. Default value is TRUE
	 */
//	private boolean redissonReferenceEnabled = true;

//	private boolean useLinuxNativeEpoll;

//	private Class<? extends EventLoopGroup> eventLoopGroup;

	private long lockWatchdogTimeout = 30 * 1000;

	private boolean keepPubSubOrder = true;
}
