package com.lomatech.telebetwebsocketclient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lomatech.telebetwebsocketclient.model.PushResult;

@Service
public class TelebetKafkaProducerServiceImpl implements TelebetKafkaProducerService {

	Logger logger = LoggerFactory.getLogger(TelebetKafkaProducerServiceImpl.class);
	
	private static final String TOPIC ="bet-result-topic"; 
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	
	@Override
	public String sendCardDetails(String message) {
		logger.info("Message received from websocket to producer service...."+message);
		
		//'{"code":0,"type":"TokenExpired","resp_msg":"连接已断开,清重连"}'
		
		//PushResult msgJson = new Gson().fromJson(message, PushResult.class);
		
		//JSONObject msgJson = JSONUtil.parseObj(message);
        //String type = msgJson.getStr("type");
        //JSONObject dataJson = msgJson.getJSONObject("data");
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			PushResult msgJson = mapper.readValue(message, PushResult.class);
			System.out.println("data: "+msgJson.getData());
			System.out.println("code: "+msgJson.getCode());
			System.out.println("type: "+msgJson.getType());
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
		kafkaTemplate.send(TOPIC, message);
		return "Message sent through kafka successfully....";
	}

}
