package github.tanishqtrivedi27.authService.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import github.tanishqtrivedi27.authService.models.UserInfoDTO;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoDTO> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, UserInfoDTO userInfoDTO) {
        byte[] val = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LowerCamelCaseStrategy.INSTANCE);
        try {
            val = objectMapper.writeValueAsString(userInfoDTO).getBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    @Override
    public byte[] serialize(String topic, Headers headers, UserInfoDTO data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
