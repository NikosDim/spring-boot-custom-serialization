package com.ndi.config;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.ndi.model.Node;
import com.ndi.utils.CollectionDepthSerializer;
import com.ndi.utils.DepthBeanSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .addModule(createNodeModule())
                .build();
    }

    private static SimpleModule createNodeModule() {
        SimpleModule nodeModule = new SimpleModule("DepthModule");
        nodeModule.setSerializerModifier(new BeanSerializerModifier() {
            @Override
            public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
                if (beanDesc.getBeanClass() == Node.class) {
                    return new DepthBeanSerializer((BeanSerializerBase) serializer);
                }
                return super.modifySerializer(config, beanDesc, serializer);
            }

            @Override
            public JsonSerializer<?> modifyCollectionSerializer(SerializationConfig config, CollectionType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {

                if (serializer instanceof CollectionSerializer) {
                    return new CollectionDepthSerializer((CollectionSerializer) serializer);
                }
                return super.modifyCollectionSerializer(config, valueType, beanDesc, serializer);
            }
        });
        return nodeModule;
    }
}
