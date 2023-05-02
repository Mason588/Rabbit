package com.example.demo.Producer;
import com.example.demo.Consumer.Consumer01;
import com.example.demo.Consumer.Consumer02;
import com.example.demo.Consumer.Consumer1;
import com.example.demo.util.ConnectionUtil;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class MyProducer {
	public final static String EXCHANGE_NAME = "ffjava.fanout";
	public final static String ROUTING_KEY = "test1";
	
	public static void main(String [] args) 
	{
		//use autiCloseable to close the connection and channel automatically
		try( 
		
				//create a connection between the producer and the  rabbitMQ server
				Connection connection = ConnectionUtil.getConnection();
				//create a channel
				Channel channel = connection.createChannel()
			) {
				//declare an exchange, if the exchange doesn't exist,create it ,set the  set the exchange type to fanout
				channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT, true /*是否持久層?*/, false/*是否自動刪除*/, null /*其他參數 */);
				//聲明隊列
				channel.queueDeclare(Consumer01.Queue_Name, true /*是否持久化*/, false /*是否獨享*/,true/*是否自動刪除*/, null/*其他參數*/);
				//queueBind: bind a queue to an exchange
				channel.queueBind(Consumer01.Queue_Name,EXCHANGE_NAME, ROUTING_KEY, null);
				
				channel.queueDeclare(Consumer02.Queue_Name, true /*是否持久化*/, false /*是否獨享*/,true/*是否自動刪除*/, null/*其他參數*/);
				//queueBind: bind a queue to an exchange
				channel.queueBind(Consumer02.Queue_Name,EXCHANGE_NAME, ROUTING_KEY, null);
				//send a message
				for(int i=0 ; i<10 ;i++) 
				{
					String message = "Hello RabbitMQ! " + i;
					// basicPublish: publish a message to the specify exchange 向指定的交換器發送消息
	                // routingKey: the routing key of the message
					channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
					System.out.println("sent " + message);
				}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
	