package pubsub;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPubSub;

@Slf4j
public class RedisPubsubListener extends JedisPubSub implements MessageListener {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Override
	public void onMessage(Message message, byte[] bytes) {
		RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
		log.info(message.toString());
		JSONObject json = JSONObject.parseObject(serializer.deserialize(message.getBody()).toString());
		String messageId = json.getString("messageId");
		String msg = json.getString("msg");
		log.info("get message messageId: {}，msg:{}", messageId, msg);
		//TODO 执行业务逻辑
	}
}
