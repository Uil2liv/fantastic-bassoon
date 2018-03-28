import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class QuerySerializer extends StdSerializer {
    QuerySerializer() {
        super(MyQuery.class);
    }

    QuerySerializer(Class t) {
        super(t);
    }

    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
/*
        jsonGenerator.writeStartObject();
        for (MyQuery.Keys key : ((MyQuery)o).fields.keySet()){
            jsonGenerator.writeObjectField(key.toString(), ((MyQuery)o).fields.get(key));
        }
        jsonGenerator.writeEndObject();
*/
        jsonGenerator.writeObject(((MyQuery)o));
    }
}
