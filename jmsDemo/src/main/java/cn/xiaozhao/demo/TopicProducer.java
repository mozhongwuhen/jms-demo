package cn.xiaozhao.demo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicProducer {
	/**消息的生产者
	 * @param args
	 * @throws JMSException 
	 */
	public static void main(String[] args) throws JMSException {
		//1.创建连接工厂(10.1.7.200表示activemq安装所在服务器的ip地址,tcp表示之间的网络通信是tcp协议，61616是默认的端口号)
		//ConnectionFactory是jms的接口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.1.7.200:61616");
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        //3.启动连接
        connection.start();
        //4.创建会话对象(arg0:boolean类型,表示是否启动事务(false不启用事务,自动提交);arg1枚举值,消息的确认模式 (AUTO:自动确认,CLIENT:客户端手动确认,DUPS:自动批量确认,SESSION:事务提交并确认))
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.创建主题对象(xiaozhao1为自定义主题名称)
        Topic topic = session.createTopic("topic-test");
        //6.创建消息生产者对象
        MessageProducer producer =  session.createProducer(topic);
        //7.创建一个文本消息对象
        TextMessage textMessage = session.createTextMessage("欢迎来到activemq");
        //8.发送消息(由生产者发送)
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
	}

}
