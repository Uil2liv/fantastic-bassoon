package app.core.common;


import app.core.AssetType;
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
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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
            if (!Objects.equals(this.get(key), ad.get(key)))
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
        URL,
        Pictures
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
            EnumMap<AdField, Object> fields;
            fields = jsonParser.getCodec().readValue(jsonParser, new TypeReference<EnumMap<AdField, Object>>() {});

            fields.put(AdField.Provider, ProviderFactory.Providers.valueOf(fields.get(AdField.Provider).toString()));
            fields.put(AdField.Type, AssetType.valueOf(fields.get(AdField.Type).toString()));
            if (fields.get(AdField.Date) != null)
                fields.put(AdField.Date, new Date((Long)fields.get(AdField.Date)));

            ProviderFactory factory = ProviderFactory.createFactory((ProviderFactory.Providers) fields.get(AdField.Provider));
            Ad ad = factory.createAd(fields.get(AdField.URL).toString());

            ad.fields.putAll(fields);

            return ad;
        }
    }
}
