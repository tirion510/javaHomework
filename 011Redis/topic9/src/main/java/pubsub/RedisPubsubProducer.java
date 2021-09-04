package pubsub;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RedisPubsubProducer {

	@Autowired
	private RedisTemplate redisTemplate;

	public void sendMsg(String sth) {
		JSONObject json = new JSONObject();
		json.put("messageId", UUID.randomUUID().toString());
		json.put("msg", sth);
		redisTemplate.convertAndSend("MY_TOPIC", json);
	}
}
