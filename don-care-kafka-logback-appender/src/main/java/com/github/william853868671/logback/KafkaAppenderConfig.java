package com.github.william853868671.logback;

import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.github.william853868671.logback.delivery.AsynchronousDeliveryStrategy;
import com.github.william853868671.logback.delivery.DeliveryStrategy;
import com.github.william853868671.logback.keying.KeyingStrategy;
import com.github.william853868671.logback.keying.NoKeyKeyingStrategy;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;

/**
 * @Author: william853868671
 * @Email: a853868671@gmail.com
 * @Date: 2024/6/2 下午6:13
 */
public abstract class KafkaAppenderConfig<E> extends UnsynchronizedAppenderBase<E> implements AppenderAttachable<E> {

    protected String topic = null;

    protected Encoder<E> encoder = null;
    protected KeyingStrategy<? super E> keyingStrategy = null;
    protected DeliveryStrategy deliveryStrategy;

    protected Integer partition = null;

    protected boolean appendTimestamp = true;

    protected Map<String,Object> producerConfig = new HashMap<String, Object>();

    protected boolean checkPrerequisites() {
        boolean errorFree = true;

        if (producerConfig.get(BOOTSTRAP_SERVERS_CONFIG) == null) {
            addError("No \"" + BOOTSTRAP_SERVERS_CONFIG + "\" set for the appender named [\""
                    + name + "\"].");
            errorFree = false;
        }

        if (topic == null) {
            addError("No topic set for the appender named [\"" + name + "\"].");
            errorFree = false;
        }

        if (encoder == null) {
            addError("No encoder set for the appender named [\"" + name + "\"].");
            errorFree = false;
        }

        if (keyingStrategy == null) {
            addInfo("No explicit keyingStrategy set for the appender named [\"" + name + "\"]. Using default NoKeyKeyingStrategy.");
            keyingStrategy = new NoKeyKeyingStrategy();
        }

        if (deliveryStrategy == null) {
            addInfo("No explicit deliveryStrategy set for the appender named [\""+name+"\"]. Using default asynchronous strategy.");
            deliveryStrategy = new AsynchronousDeliveryStrategy();
        }

        return errorFree;
    }

    public void setEncoder(Encoder<E> encoder) {
        this.encoder = encoder;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setKeyingStrategy(KeyingStrategy<? super E> keyingStrategy) {
        this.keyingStrategy = keyingStrategy;
    }

    public void addProducerConfig(String keyValue) {
        String[] split = keyValue.split("=", 2);
        if(split.length == 2) {
            addProducerConfigValue(split[0], split[1]);
        }
    }

    public void addProducerConfigValue(String key, Object value) {
        this.producerConfig.put(key,value);
    }

    public Map<String, Object> getProducerConfig() {
        return producerConfig;
    }

    public void setDeliveryStrategy(DeliveryStrategy deliveryStrategy) {
        this.deliveryStrategy = deliveryStrategy;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public boolean isAppendTimestamp() {
        return appendTimestamp;
    }

    public void setAppendTimestamp(boolean appendTimestamp) {
        this.appendTimestamp = appendTimestamp;
    }

}
