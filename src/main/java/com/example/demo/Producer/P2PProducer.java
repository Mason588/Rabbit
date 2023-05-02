package com.example.demo.Producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.example.demo.util.ConnectionUtil;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
public class P2PProducer {
	//P2P消息模型
	//一個生產者對應一個消費者
	public static void main(String[] args) 
	{
		try {
				//用自動關閉自動關閉連線,信道跟其他資源
				Connection connection = ConnectionUtil.getConnection();
				//創建萬神信道
				Channel channel = connection.createChannel();
				//聲明隊列,如果隊列不存在,創造它
				channel.queueDeclare("firstQueue", true /*是否持久化*/, false /*是否獨享*/ , true /*是否自動刪除*/, null /*其他參數*/);
				//發送訊息
				for(int i=0;i<10; i++) 
				{
					String message = "Hello RabbitMQ!" +i;
					channel.basicPublish("", "firstQueue", null, message.getBytes());
					System.out.println("Sent" + message);
			
				}
				
				}
				catch(Exception e) 
				{
					e.printStackTrace();
				}
		}
	
	}
