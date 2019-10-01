package practice;

import java.util.logging.Logger;


public class ThreadTest {

	public ThreadTest() {
	}
	
	public static void main(String arg[]) {

		Logger logger = Logger.getLogger("test");
		
		Resource resource = new Resource();
				
		Producer producer = new Producer("Producer1", resource);
		producer.start();
		
		Consumer[] consumers = new Consumer[10];
		for(int i=0 ; i<10 ; i++) {
			consumers[i] = new Consumer("Consumer"+(i+1), resource);
			consumers[i].start();
		}
		
	}
}

class Resource {
	int money = 10;
	
	public synchronized void earn(Producer producer) {
		money ++;
		System.out.println(producer.name + "earn 1 coin entire is " + money);
		notifyAll();
	}
	
	public synchronized void consum(Consumer consumer) {
		try {
			while(money <= 0)
				wait();
			money -= 1;
			System.out.println(consumer.name + "sonsume 1 coin entire is " + money);
			
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Producer extends Thread {
	
	Resource resource;
	String name;
	
	public Producer (String name, Resource resource) {
		this.name = name;
		this.resource = resource;
	}
	
	@Override
	public void run() {
		for(int i=0 ; i<100 ; i++) {
			try {
				Thread.sleep(100);
				resource.earn(this);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}

class Consumer extends Thread{
	
	Resource resource;
	String name;
	
	public Consumer (String name, Resource resource) {
		this.name = name;
		this.resource = resource;
	}
	
	@Override
	public void run() {
		for(int i=0 ; i<10 ; i++) {
			try {
				Thread.sleep(100);
				resource.consum(this);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
