package com.ndi.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import com.ndi.model.Node;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ndi.utils.DepthBeanSerializer.DEPTH_KEY;
import static java.util.Collections.emptyList;

public class CollectionDepthSerializer extends JsonSerializer<Collection<?>> {

    private final CollectionSerializer serializer;

    public CollectionDepthSerializer(CollectionSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void serialize(Collection<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        AtomicInteger depth = (AtomicInteger) provider.getAttribute(DEPTH_KEY);
        if (isNotOfConfigurationItemType(value) || depth == null || depth.get() > 0) {
            serializer.serialize(value, gen, provider);
        } else {
            serializer.serialize(emptyList(), gen, provider);
        }
    }

    private boolean isNotOfConfigurationItemType(Collection<?> value) {
        Iterator it = value.iterator();
        if (it.hasNext()) {
            return !(it.next() instanceof Node);
        }
        return true;
    }
}
