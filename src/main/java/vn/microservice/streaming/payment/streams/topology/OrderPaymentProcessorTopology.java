package vn.microservice.streaming.payment.streams.topology;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import vn.microservice.streaming.common.lib.dto.OrderStreamDTO;
import vn.microservice.streaming.common.lib.dto.VerifiedOrderStreamDTO;
import vn.microservice.streaming.common.lib.enumeration.Status;

import java.time.Instant;
import java.util.function.Function;

/**
 * @author Tuan.Truong Brian
 * @version 1.0
 * This class helps to process payment for order
 * @date 1/2/21 14:32
 */
@EnableBinding
public class OrderPaymentProcessorTopology {

    private final static Logger log = LoggerFactory.getLogger(OrderPaymentProcessorTopology.class);

    private final static String ORDER_PAYMENT_SERVICE = "Order Payment Service";

    @Bean
    public Function<KStream<Byte, OrderStreamDTO>, KStream<?, VerifiedOrderStreamDTO>> orderPaymentProcess() {
        return input -> input.peek((k, v) -> log.info("receive order to process payment {}", v))
               .map((k, v) -> new KeyValue<>(v.getOrderId(), new VerifiedOrderStreamDTO(v.getUserid(), v.getProdId(), v.getOrderId(), Status.VERIFIED, ORDER_PAYMENT_SERVICE, Instant.now(), Instant.now())));
    }
}
