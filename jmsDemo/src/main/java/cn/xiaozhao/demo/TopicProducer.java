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
	/**��Ϣ��������
	 * @param args
	 * @throws JMSException 
	 */
	public static void main(String[] args) throws JMSException {
		//1.�������ӹ���(10.1.7.200��ʾactivemq��װ���ڷ�������ip��ַ,tcp��ʾ֮�������ͨ����tcpЭ�飬61616��Ĭ�ϵĶ˿ں�)
		//ConnectionFactory��jms�Ľӿ�
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.1.7.200:61616");
        //2.��������
        Connection connection = connectionFactory.createConnection();
        //3.��������
        connection.start();
        //4.�����Ự����(arg0:boolean����,��ʾ�Ƿ���������(false����������,�Զ��ύ);arg1ö��ֵ,��Ϣ��ȷ��ģʽ (AUTO:�Զ�ȷ��,CLIENT:�ͻ����ֶ�ȷ��,DUPS:�Զ�����ȷ��,SESSION:�����ύ��ȷ��))
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.�����������(xiaozhao1Ϊ�Զ�����������)
        Topic topic = session.createTopic("topic-test");
        //6.������Ϣ�����߶���
        MessageProducer producer =  session.createProducer(topic);
        //7.����һ���ı���Ϣ����
        TextMessage textMessage = session.createTextMessage("��ӭ����activemq");
        //8.������Ϣ(�������߷���)
        producer.send(textMessage);
        //9.�ر���Դ
        producer.close();
        session.close();
        connection.close();
	}

}
