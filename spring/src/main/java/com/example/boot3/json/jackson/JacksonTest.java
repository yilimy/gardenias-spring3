package com.example.boot3.json.jackson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.boot3.json.pojo.Positions;
import com.example.boot3.json.pojo.PositionsWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author caimeng
 * @date 2024/6/6 10:57
 */
@Slf4j
public class JacksonTest {
    private List<Positions> positionsList;
    private String dataStr;
    private String wrapperStr;

    @Before
    public void init() {
        Positions positions_01 = Positions.builder()
                .name(UUID.randomUUID().toString())
                .pos(Collections.singletonList(0))
                .build();
        Positions positions_02 = Positions.builder()
                .name(UUID.randomUUID().toString())
                .pos(Collections.singletonList(0))
                .build();
        positionsList = Arrays.asList(positions_01, positions_02);
        dataStr = "[{\"name\":\"0ffb7aa4-5f95-464a-b254-c6e3b07a6516\",\"pos\":[0]},{\"name\":\"4495c9ab-a481-436f-84be-8b09b0704e35\",\"pos\":[0]}]";
        wrapperStr = "{\"one\":[{\"name\":\"0ffb7aa4-5f95-464a-b254-c6e3b07a6516\",\"pos\":[0]},{\"name\":\"4495c9ab-a481-436f-84be-8b09b0704e35\",\"pos\":[0]}],\"two\":[{\"name\":\"0ffb7aa4-5f95-464a-b254-c6e3b07a6516\",\"pos\":[0]},{\"name\":\"4495c9ab-a481-436f-84be-8b09b0704e35\",\"pos\":[0]}]}";
    }

    /**
     * 测试：序列化
     */
    @SneakyThrows
    @Test
    public void serializeTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(positionsList);
        /*
         * [{"name":"0ffb7aa4-5f95-464a-b254-c6e3b07a6516","pos":[0]},{"name":"4495c9ab-a481-436f-84be-8b09b0704e35","pos":[0]}]
         */
        System.out.println(jsonStr);
    }

    /**
     * 测试： 反序列化1
     */
    @SneakyThrows
    @Test
    public void unSerializeTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Positions>> typeReference = new TypeReference<>() {};
        List<Positions> result = objectMapper.readValue(dataStr, typeReference);
        log.info("result = {}", result);
    }

    /**
     * 测试： 反序列化2
     */
    @SneakyThrows
    @Test
    public void unSerializeObjectListTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        PositionsWrapper result = objectMapper.readValue(wrapperStr, PositionsWrapper.class);
        log.info("result = {}", result);
    }

    /**
     * 测试：混合测试，先fastjson，再jackson
     * 对照组 {@link this#mixForbidRefTest()}
     */
    @SneakyThrows
    @Test
    public void mixTest() {
        // fastjson 序列化操作
        PositionsWrapper positionsWrapper = JSONObject.parseObject(wrapperStr, PositionsWrapper.class);
        String fastJsonString = JSONObject.toJSONString(positionsWrapper);
        /*
         * fastJsonString = {"one":[{"name":"0ffb7aa4-5f95-464a-b254-c6e3b07a6516","pos":[0]},{"name":"4495c9ab-a481-436f-84be-8b09b0704e35","pos":[{"$ref":"$.one[0].pos[0]"}]}],"two":[{"name":"0ffb7aa4-5f95-464a-b254-c6e3b07a6516","pos":[{"$ref":"$.one[0].pos[0]"}]},{"name":"4495c9ab-a481-436f-84be-8b09b0704e35","pos":[{"$ref":"$.one[0].pos[0]"}]}]}
         * fastjson 在序列化后，带有了"$ref" | "$."之类的引用标记
         */
        System.out.println("fastJsonString = " + fastJsonString);
        // jackson 序列化操作
        ObjectMapper objectMapper = new ObjectMapper();
        /*
         * 无法解析fastjson的引用，导致了报错
         * com.fasterxml.jackson.databind.exc.MismatchedInputException:
         *          Cannot deserialize value of type `java.lang.Integer` from Object value (token `JsonToken.START_OBJECT`)
         */
        PositionsWrapper jacksonObject = objectMapper.readValue(fastJsonString, PositionsWrapper.class);
        log.info("jacksonObject = {}", jacksonObject);
    }

    /**
     * 测试：混合测试，禁用fastjson序列化的引用
     */
    @SneakyThrows
    @Test
    public void mixForbidRefTest() {
        // fastjson 序列化操作
        PositionsWrapper positionsWrapper = JSONObject.parseObject(wrapperStr, PositionsWrapper.class);
        String fastJsonString = JSONObject.toJSONString(positionsWrapper,
                // 禁用循环引用检测
                SerializerFeature.DisableCircularReferenceDetect);
        /*
         * fastJsonString = {"one":[{"name":"0ffb7aa4-5f95-464a-b254-c6e3b07a6516","pos":[0]},{"name":"4495c9ab-a481-436f-84be-8b09b0704e35","pos":[0]}],"two":[{"name":"0ffb7aa4-5f95-464a-b254-c6e3b07a6516","pos":[0]},{"name":"4495c9ab-a481-436f-84be-8b09b0704e35","pos":[0]}]}
         */
        System.out.println("fastJsonString = " + fastJsonString);
        // jackson 序列化操作
        ObjectMapper objectMapper = new ObjectMapper();
        PositionsWrapper jacksonObject = objectMapper.readValue(fastJsonString, PositionsWrapper.class);
        /*
         * jacksonObject = PositionsWrapper(one=[Positions(name=0ffb7aa4-5f95-464a-b254-c6e3b07a6516, pos=[0]), Positions(name=4495c9ab-a481-436f-84be-8b09b0704e35, pos=[0])], two=[Positions(name=0ffb7aa4-5f95-464a-b254-c6e3b07a6516, pos=[0]), Positions(name=4495c9ab-a481-436f-84be-8b09b0704e35, pos=[0])])
         */
        log.info("jacksonObject = {}", jacksonObject);
    }
}
