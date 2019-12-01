package br.com.alura.ecommerce;

import br.com.alura.ecommerce.consumer.KafkaService;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ServiceProvider {
    public <T> void run(ServiceFactory<T> factory) throws ExecutionException, InterruptedException {
        var emailService = factory.create();
        try (var service = new KafkaService(emailService.getConsumerGroup(),
                emailService.getTopic(),
                emailService::parse,
                Map.of())) {
            service.run();
        }
    }
}
