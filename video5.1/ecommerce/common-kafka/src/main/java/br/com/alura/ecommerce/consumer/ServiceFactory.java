package br.com.alura.ecommerce.consumer;

public interface ServiceFactory<T> {
    ConsumerService<T> create() throws Exception;
}
