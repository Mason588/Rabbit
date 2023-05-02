package com.example.demo.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class ConnectionUtil {

	public static Connection getConnection() throws IOException, TimeoutException
	{
		//ConnectionFactory: a factory to create connections
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("newt");
		factory.setPassword("newt");
		//如果沒有virtualhost設定, defult 的virtual host是"/"
		factory.setVirtualHost("/");
		return factory.newConnection();
	}
}
