package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import pubsub.RedisPubsubListener;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedisPubsubConfig {

	@Bean
	public RedisPubsubListener redisPubsubListener() {
		return new RedisPubsubListener();
	}

	@Bean
	public MessageListenerAdapter messageListenerAdapter(MessageListener messageListener) {
		return new MessageListenerAdapter(messageListener);
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
	                                                                   MessageListenerAdapter messageListenerAdapter) {

		List<Topic> collection = new ArrayList<Topic>();
		ChannelTopic channelTopic = new ChannelTopic("MY_TOPIC");
		collection.add(channelTopic);
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
		redisMessageListenerContainer.addMessageListener(messageListenerAdapter, collection);
		return redisMessageListenerContainer;
	}
}
