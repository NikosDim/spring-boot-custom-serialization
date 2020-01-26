package com.ndi.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class DepthBeanSerializer extends BeanSerializer {

    public static final String DEPTH_KEY = "maxDepth";

    public DepthBeanSerializer(BeanSerializerBase serializer) {
        super(serializer);
    }

    @Override
    protected void serializeFields(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException {
        AtomicInteger depth = (AtomicInteger) provider.getAttribute(DEPTH_KEY);
        if (depth.get() > 0) {
            depth.decrementAndGet();
            super.serializeFields(bean, gen, provider);
            depth.incrementAndGet();
        }
    }
}
