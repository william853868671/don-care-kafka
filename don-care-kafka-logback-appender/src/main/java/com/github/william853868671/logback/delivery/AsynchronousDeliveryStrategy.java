package com.github.william853868671.logback.delivery;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.errors.TimeoutException;

/**
 * @Author: william853868671
 * @Email: a853868671@gmail.com
 * @Date: 2024/6/2 下午6:29
 */
public class AsynchronousDeliveryStrategy implements com.github.william853868671.logback.delivery.DeliveryStrategy {

    @Override
    public <K, V, E> boolean send(Producer<K, V> producer, ProducerRecord<K, V> record, final E event,
                                  final com.github.william853868671.logback.delivery.FailedDeliveryCallback<E> failedDeliveryCallback) {
        try {
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        failedDeliveryCallback.onFailedDelivery(event, exception);
                    }
                }
            });
            return true;
        } catch (TimeoutException e) {
            failedDeliveryCallback.onFailedDelivery(event, e);
            return false;
        }
    }

}
