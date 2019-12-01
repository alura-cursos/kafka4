package br.com.alura.ecommerce;

import com.google.gson.*;

import java.lang.reflect.Type;

public class MessageAdapter implements JsonSerializer<Message>, JsonDeserializer<Message> {
    @Override
    public JsonElement serialize(Message message, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", message.getPayload().getClass().getName());
        obj.add("payload", context.serialize(message.getPayload()));
        obj.add("correlationId", context.serialize(message.getId()));
        return obj;
    }

    @Override
    public Message deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        var obj = jsonElement.getAsJsonObject();
        var payloadType =  obj.get("type").getAsString();
        var correlationId = (CorrelationId) context.deserialize(obj.get("correlationId"), CorrelationId.class);
        try {
            // maybe you want to use a "accept list"
            var payload = context.deserialize(obj.get("payload"), Class.forName(payloadType));
            return new Message(correlationId, payload);
        } catch (ClassNotFoundException e) {
            // you might want to deal with this exception
            throw new JsonParseException(e);
        }
    }
}





