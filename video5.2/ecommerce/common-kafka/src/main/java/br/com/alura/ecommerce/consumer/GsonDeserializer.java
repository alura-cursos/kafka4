package br.com.alura.ecommerce.consumer;

import br.com.alura.ecommerce.Message;
import br.com.alura.ecommerce.MessageAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

public class GsonDeserializer implements Deserializer<Message> {

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new MessageAdapter()).create();

    @Override
    public Message deserialize(String s, byte[] bytes) {
        return gson.fromJson(new String(bytes), Message.class);
    }
}
