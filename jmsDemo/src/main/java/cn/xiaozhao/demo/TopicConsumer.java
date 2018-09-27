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
	/**��Ϣ��������
	 * @param args
	 * @throws JMSException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws JMSException, IOException {
		//1.�������ӹ���(10.1.7.200��ʾactivemq��װ���ڷ�������ip��ַ,tcp��ʾ֮�������ͨ����tcpЭ�飬61616��Ĭ�ϵĶ˿ں�)
		//ConnectionFactory��jms�Ľӿ�
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.1.7.200:61616");
        //2.��������
        Connection connection = connectionFactory.createConnection();
        //3.��������
        connection.start();
        //4.�����Ự����(arg0:boolean����,��ʾ�Ƿ���������(false����������,�Զ��ύ);arg1ö��ֵ,��Ϣ��ȷ��ģʽ (AUTO:�Զ�ȷ��,CLIENT:�ͻ����ֶ�ȷ��,DUPS:�Զ�����ȷ��,SESSION:�����ύ��ȷ��))
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.�������ж���(xiaozhao1Ϊ�Զ����������)
        Topic topic = session.createTopic("topic-test");
        //6.���������߶���
        MessageConsumer messageConsumer = session.createConsumer(topic);
        //7.���ü���
        messageConsumer.setMessageListener(new MessageListener() {			
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage)message;
				try {
					System.out.println("�����߶����͵���Ϣ��:"+textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        //8.�ȴ���������(�ȴ�����)
        System.in.read();
        //9.�ر���Դ
        messageConsumer.close();
        session.close();
        connection.close();
	}

}
