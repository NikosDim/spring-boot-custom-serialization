package com.ndi.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ndi.model.Node;
import com.ndi.utils.DepthBeanSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("serialize")
public class Controller {

    private final ObjectMapper objectMapper;

    public Controller(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> serviceStructureById(@RequestParam Integer depth) throws JsonProcessingException {

        ObjectWriter writer = objectMapper.writerFor(Node.class)
                .withAttribute(DepthBeanSerializer.DEPTH_KEY, new AtomicInteger(depth));

        Node node = getNode();

        return ResponseEntity.ok(writer.writeValueAsString(node));
    }

    private Node getNode() {
        Node root = new Node();
        root.setId("root");

        Node level1 = new Node();
        level1.setId("level 1");

        Node level2 = new Node();
        level2.setId("level 2");

        Node level3 = new Node();
        level3.setId("level 3");

        root.setChild(level1);
        level1.setChild(level2);
        level2.setChild(level3);

        return root;
    }
}
