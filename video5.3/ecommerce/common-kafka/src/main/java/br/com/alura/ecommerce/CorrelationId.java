package br.com.alura.ecommerce;

import java.util.UUID;

public class CorrelationId {

    private final String id;

    CorrelationId(String title) {
        id = title + "(" + UUID.randomUUID().toString() + ")";
    }

    @Override
    public String toString() {
        return "CorrelationId{" +
                "id='" + id + '\'' +
                '}';
    }

    public CorrelationId continueWith(String title) {
        return new CorrelationId(id + "-" + title);
    }
}
