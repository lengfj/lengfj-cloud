package com.lengfj.cloud.gateway.grey;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.balancer.NacosBalancer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 灰度发布实现
 * @author lengfj
 * @version 1.0
 * @date 2023/9/7
 **/
@RequiredArgsConstructor
@Slf4j
public class GrayLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private static final String VERSION = "version";

    /**
     * 用于获取 serviceId 对应的服务实例的列表
     */
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    /**
     * 需要获取的服务实例名
     *
     * 暂时用于打印 logger 日志
     */
    private final String serviceId;

    private final AtomicInteger position = new AtomicInteger(new Random().nextInt(1000));

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        HttpHeaders headers = ((RequestDataContext) request.getContext()).getClientRequest().getHeaders();
        ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, headers));
    }


    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances, HttpHeaders headers) {
        if (serviceInstances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        } else {
            String reqVersion = headers.getFirst(VERSION);

            if (!StrUtil.isEmpty(reqVersion)) {
                List<ServiceInstance> serviceInstances2 = serviceInstances.stream()
                        .filter(instance -> reqVersion.equals(instance.getMetadata().get(VERSION)))
                        .collect(Collectors.toList());

                if (!serviceInstances2.isEmpty()) {
                    serviceInstances = serviceInstances2;
                }
            }

            Response<ServiceInstance> serviceInstanceResponse = getInstanceResponseNacosBalancer(serviceInstances);
            if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
                ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
            }
            return serviceInstanceResponse;
        }
    }

    /**
     * 根据nacos权重随机选择，仅支持nacos
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceResponseNacosBalancer(List<ServiceInstance> instances) {
        return new DefaultResponse(NacosBalancer.getHostByRandomWeight3(instances));
    }

    /**
     * 随机选择
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        int pos = Math.abs(this.position.incrementAndGet());
        ServiceInstance instance = instances.get(pos % instances.size());
        return new DefaultResponse(instance);
    }
}
