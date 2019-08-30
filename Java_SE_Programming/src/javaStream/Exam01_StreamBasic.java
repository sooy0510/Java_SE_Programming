package javaStream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/*
 * Stream�� Java 8���� ������ �Ǿ����. �����ؾ� �Ұ� java.io�ȿ� �ִ� Stream�� �ٸ��� 
 * ��� �뵵 : �÷���ó��(List,Set, Map, �迭)�� ���ؼ� ����� �ſ�
 * 			�÷��Ǿ��� �����͸� �ݺ���Ű�� �ݺ����� ������ �ϴ°� stream
 * 			ex)ArrayList�ȿ� �л� ��ü�� 5�� ������ �� 5���� �ϳ���
 * 				�������� ������ ����. => �̷��� ������ �����͸� ���ٽ��� �̿��ؼ� ó������
 */

class Exam01_Student{
	private String name;
	private int kor;
	private int eng;
	
	public Exam01_Student() {
		super();
	}

	public Exam01_Student(String name, int kor, int eng) {
		super();
		this.name = name;
		this.kor = kor;
		this.eng = eng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}
	
	
}

public class Exam01_StreamBasic {

	private static List<String> myBuddy = 
			Arrays.asList("ȫ�浿","��浿","�ֱ浿","�ڱ浿");
	
	private static List<Exam01_Student> students = 
			Arrays.asList(
					new Exam01_Student("ȫ�浿",10,20),
					new Exam01_Student("�ڱ浿",10,20),
					new Exam01_Student("�Ż��Ӵ�",10,20),
					new Exam01_Student("������",10,20),
					new Exam01_Student("�̼���",10,20));
	
	public static void main(String[] args) {
		//����̸� ���
		//��� 1. �Ϲ� for��(÷�ڸ� ���)�� �̿��ؼ� ó��
		//		=> ��ȿ����(÷�ڿ� �ݺ���)
		for(int i=0; i<myBuddy.size(); i++) {
			System.out.println(myBuddy.get(i));
		}
		//��� 2. ÷�ڸ� �̿��� �ݺ��� ���ϱ� ���� Iterator�� ���
		//		=> ÷�ڰ� ����
		Iterator<String> iter = myBuddy.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		//��� 3. Stream
		//		=> �ݺ��ڰ� �ʿ����. ���� �ݺ��� �̿�
		// 			����ó�� ����, �������� ���� , �� �� ��ü������
		Consumer<String> consumer = t -> {
			System.out.println(t + ", "
					+ Thread.currentThread().getName());
		};
		
		Stream<String> stream1 = myBuddy.stream();//stream�� �÷��� ���� �����͸� �ݺ���Ŵ
		stream1.forEach(consumer);
		
		Stream<String> stream2 = myBuddy.parallelStream();//stream�� �÷��� ���� �����͸� �ݺ���Ŵ
		//stream.forEach(t -> System.out.println(t));
		stream2.forEach(consumer);  
		
		// arrayList�� stream�̶�� �޼ҵ尡 ����
		Stream<Exam01_Student> studentStream = students.stream();
		// function lambda���� �̿��Ͽ� ��ü��  mapping
		// t��� ��ü�� ���� getkor()�� �̿�
		double avg = 
				studentStream.mapToInt(t->t.getKor()).average().getAsDouble();
		System.out.println("������� ��� : "+avg);
	}

}
