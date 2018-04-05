package app.core.common;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

@JsonSerialize(using = Ad.AdSerializer.class)
@JsonDeserialize(using = Ad.AdDeserializer.class)
public abstract class Ad extends Page {
    public EnumMap<AdField, Object> fields = new EnumMap<>(AdField.class);

    public Ad() {
        this(null);
    }

    public Ad(String url) {
        super(url);
        this.fields.put(AdField.URL, url);
    }

    public Object get(AdField key) { return this.fields.get(key); }

    public Boolean isEquals(Ad ad){
        for (AdField key : AdField.values()) {
            if (this.get(key) != ad.get(key))
                return false;
        }
        return true;
    }

    public abstract void getFields();

    public enum AdField {
        Id,
        Title,
        Price,
        Date,
        Type,
        NbRooms,
        Area,
        ProviderId,
        SubmitterId,
        Description,
        GHG,
        Energy,
        Submitter,
        Provider,
        URL
    }

    // Jackson custom serializer/deserializer
    public static class AdSerializer extends StdSerializer<Ad> {
        public AdSerializer() {
            this(null);
        }

        public AdSerializer(Class<Ad> t) {
            super(t);
        }

        @Override
        public void serialize(Ad ad, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeObject(ad.fields);
        }
    }

    public static class AdDeserializer extends StdDeserializer<Ad> {
        public AdDeserializer() {
            this(null);
        }

        public AdDeserializer(Class<?> t) {
            super(t);
        }

        @Override
        public Ad deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            EnumMap<AdField, Object> fields = jsonParser.getCodec().readValues(jsonParser, new TypeReference<EnumMap<AdField, Object>>() {});
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);

            ProviderFactory.Providers provider = ProviderFactory.Providers.valueOf(node.get("Provider").asText());
            String url = node.get("URL").asText();

            ProviderFactory factory = ProviderFactory.createFactory(provider);
            Ad ad = factory.createAd(url);


            ad.fields.putAll((Map<AdField, Object>)jsonParser.readValuesAs(new TypeReference<EnumMap<AdField, Object>>(){}));

            return ad;
        }
    }
}
