package javaLambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;

/*
 * ���ٽ��� �߻�޼ҵ尡 1���� �������̽��� ��ü�� �����ϴ� ǥ����
 * => �� �� ����ϴ� �������̽��� �츮�� ���� ���� ���?
 * ==> �׷��� �ʴ�. ���ٽ��� ���ԵǴ� target type�� 
 * �Ϲ������� Java�� �����ϴ� API�̿�
 * ��ǥ���ΰ�...Runnable, Eventó�� interface�� ������ 
 * target type���� ���
 * 
 * Java������ ������ target type���� ���� �� �ִ� 
 * interface�� ������ ���� �츮���� package���·� ����
 * ( java.util.function package)
 * �����Ǵ� interface�� �� 5���� ������ �з��� �� ����
 * Consumer, SUpplier, FUnction, Operation, Predicate ��5���� �з��� ����
 * 
 * Consumer : �Լ��� �������̽�(���ٽ��� ���Ե� �� �ִ� target type���� ����� �� �ִ� interface�� ��Ī)
 * Consumer�� Java�� �츮���� �����ϴ� interface�̰� �߻� �޼ҵ带 �� 1���� ������ ����
 * accept()��� method�� ����
 * ���� �Һ��ϴ� ������ ���. accept()��� �Լ��� ���� Ÿ���� void -> return �� ���� �� ����
 */

public class Exam04_LambdaUsingConsumer {
	// method�� �ϳ� �����ϴµ� static���� ������. (���ϰ� ������)
	public static List<String> names = Arrays.asList("ȫ�浿",
			"��浿","�ֱ浿","�ڱ浿");
	
	// �Ϲ����� methodȣ���� ����ϴ� data�� ���ڷ� ���޵Ǵ� ����.
	// ���ٽ��� �̿��ϸ� method�� ȣ���� �� data�� �ƴ϶� ���� �ڵ带 �Ѱ��� �� ����(���� ���̴� ���¸�..)
	// �Ϲ������� ���α׷��� ���� �̷��� �Լ��� �ٸ� �Լ��� ���ڷ�
	// ����� �� �ִµ� �̷� �Լ��� first-classes function �̶�� ��
	// �ϱ��Լ���� ǥ���ֿ�(JavaScript�� ��ǥ��)
	// Java�� ���ٸ� �����ؼ� ��ġ 1���Լ��� ����ϴ� ��ó�� ��������
	
	public static void printName(Consumer<String> consumer) {
		for(String name : names) {
			consumer.accept(name);
		}
	}
	public static void main(String[] args) {
		
		Consumer<String> consumer = t ->{
			System.out.println(t); //���ڷ� ������ �Ұ���
		};
		consumer.accept("�Ҹ����� �ƿ켺!!");
		printName(consumer);
		
		BiConsumer<String, String> biConsumer = (a,b) -> {
			System.out.println(a+b);
		};
		biConsumer.accept("�Ҹ�����","�ƿ켺!!");
		
		IntConsumer intConsumer = i -> System.out.println(i);
		intConsumer.accept(100);
		
		ObjIntConsumer<String> objIntConsumer = (a,b) -> {
			System.out.println(a+b);
		};
		objIntConsumer.accept("Hello", 100);
		
	};
}
