package br.com.alura.ecommerce;

public interface ServiceFactory<T> {
    ConsumerService<T> create();
}
