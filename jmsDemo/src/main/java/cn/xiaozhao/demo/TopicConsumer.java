package cn.xiaozhao.demo;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicConsumer {
	/**消息的消费者
	 * @param args
	 * @throws JMSException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws JMSException, IOException {
		//1.创建连接工厂(10.1.7.200表示activemq安装所在服务器的ip地址,tcp表示之间的网络通信是tcp协议，61616是默认的端口号)
		//ConnectionFactory是jms的接口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.1.7.200:61616");
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        //3.启动连接
        connection.start();
        //4.创建会话对象(arg0:boolean类型,表示是否启动事务(false不启用事务,自动提交);arg1枚举值,消息的确认模式 (AUTO:自动确认,CLIENT:客户端手动确认,DUPS:自动批量确认,SESSION:事务提交并确认))
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.创建队列对象(xiaozhao1为自定义队列名称)
        Topic topic = session.createTopic("topic-test");
        //6.创建消费者对象
        MessageConsumer messageConsumer = session.createConsumer(topic);
        //7.设置监听
        messageConsumer.setMessageListener(new MessageListener() {			
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage)message;
				try {
					System.out.println("生产者对象发送的消息是:"+textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        //8.等待键盘输入(等待监听)
        System.in.read();
        //9.关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
	}

}
