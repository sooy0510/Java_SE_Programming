package javaStream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
 * reduction
 * 
 *  => �뷮�� �����͸� �����ؼ� ����ϴ� ����
 *  => sum, average, count, max, min
 *  
 *  ���� Collection�ȿ� reduction�ϱⰡ ���� ���� ���·� �����Ͱ� ��������
 *  �߰�ó�������� ���ļ� reduction�ϱ� ���� ���·� ��ȯ
 *  
 *  Stream�� pipeline�� ����(stream�� �����ؼ� ����� �� ����)
 *  
 *  ex)
 *  ������ü�� �����ؼ� ArrayList�ȿ� �������� ������ ����
 *  �� �����߿� it�� �����ϰ� ������ ������ �߷��� �ش� �������� ���� ����� ���
 */


public class Exam03_StreamPipeline {

	private static List<Exam03_Employee> employees = 
			Arrays.asList(
					new Exam03_Employee("ȫ�浿",20,"IT","����",2000),
					new Exam03_Employee("�ֱ浿",30,"Sales","����",3000),
					new Exam03_Employee("��浿",40,"IT","����",4000),
					new Exam03_Employee("���浿",28,"Sales","����",5000),
					new Exam03_Employee("�̼���",25,"IT","����",6000),
					new Exam03_Employee("����",35,"IT","����",3500),
					new Exam03_Employee("�ٲ�",50,"IT","����",2500),
					new Exam03_Employee("�ѻ�",40,"Sales","����",2800),
					new Exam03_Employee("ȫ�浿",20,"IT","����",2000));
	public static void main(String[] args) {
		//�μ��� IT�� ����� �� ���ڿ� ���� ���� ����� ���غ���!
		Stream<Exam03_Employee> stream = employees.stream();
//		//stream�� �߰�ó���� ����ó���� �̿��ؼ� ���ϴ� �۾��� ����
//		//filter method�� ������� ������ �ִ� stream�� ��������(it�μ��� ��ü�� ���մ� stream)
//		double avg = stream.filter(t->t.getDept().equals("IT")) //�߰�ó�� �ܰ�
//						  .filter(t->t.getGender().equals("����")) //�߰�ó�� �ܰ�
//						  .mapToInt(t->t.getSalary()) //stream�� �ش��ϴ� ������ ��� int�� ��ȯ // �߰�ó�� �ܰ�
//						  .average().getAsDouble(); //average�� ����ó��
//							//lazy ó��
						
		// stream�� reduction�ϴ� ����ó���� �ִ��� Ȯ���ϰ� ������ �߰�ó���� ��������
//		System.out.println("IT�μ��� �������� ���� ��� : "+avg);
		
		//�ѹ� streamó���� �ϸ� stream�� ������ ������ �ּ�ó��
		//sql��ó�� ���
		
		
		// stream�� ������ �մ� method
		// ���̰� 35�̻��� ���� �� ���� ������ �̸��� �������
//		stream.filter(t->t.getAge() >= 35)
//									 .filter(t->t.getGender().equals("����"))
//									 .forEach(t->System.out.println(t.getName()));
		
		// �ߺ����Ÿ� ���� �߰�ó��
//		int temp[] = {10,20,30,20,50};
//		IntStream s = Arrays.stream(temp);
//		s.distinct().forEach(t->System.out.println(t));
		
		// ��ü�� ���� �ߺ����Ÿ� �غ���
//		employees.stream()
//				 .distinct()
//				 .forEach(t->System.out.println(t.getName()));
		
		// mapToInt => mapXXX()
		// ����(�μ��� IT�� ����� ���������� ���)
//		employees.stream()
//				 .distinct()
//				 .filter(t->t.getDept().equals("IT"))
//				 .sorted(Comparator.reverseOrder() ) //�⺻�����δ� �������� ����, ���ڰ� ������ ��������
//				 .forEach(t->System.out.println(t.getName() + ", "+t.getSalary()));
		
		//�ݺ�
		//forEach()�� �̿��ϸ� ��Ʈ������ ��Ҹ� �ݺ��� �� �־��!
		// forEach()�� ���� ó�� �Լ�, �߰�ó���Լ��� ����Ҽ�����
		// -> �߰�ó���Լ��� �ݺ�ó���ϴ� �Լ��� �ϳ� �� ���� : peek
//		employees.stream()
//				 .peek(t->System.out.println(t.getName()))
//				 .mapToInt(t->t.getSalary())
//				 .forEach(t->System.out.println(t));
		
		//Ȯ�ο� ���� ó�� �Լ�
		//50���̻��� ����� �����ؼ� �̸� ���
//		boolean result = employees.stream()
//				 .filter(t->t.getAge() >= 50)
//				 .allMatch(t->(t.getAge() >55));
//		System.out.println(result);
		
		//���� Ȯ�ο� �Լ��� forEach�� ���� ����ߴµ�
		//forEach ���� collect()�� �̿��� ���ƿ�
		//���̰� 50�� �̻��� ������� ������ ���ؼ�
		//List<Integer>������ ArrayList�� ������ ���ƿ�
		List<Integer> tmp = employees.stream()
		//Set<Integer> tmp = employees.stream()
				 .filter(t->t.getAge() >= 50)
				 .map(t->t.getSalary())
				 .collect(Collectors.toList());
				 //.collect(Collectors.toCollection(HashSet :: new));
		
		System.out.println(tmp);
		//�翬�� set���ε� ������ �� �ְ�, map���ε� ���尡��
	}

}

class Exam03_Employee implements Comparable<Exam03_Employee>{ //Comparable�� �����ؾ� ��ü�鳢�� ���İ����ϴ�, ��ü�鳢�� �񱳰����� ���·� ������
	private String name;
	private int age;
	private String dept;
	private String gender;
	private int salary;
	
	
	public Exam03_Employee() {
		super();
	}


	public Exam03_Employee(String name, int age, String dept, String gender, int salary) {
		super();
		this.name = name;
		this.age = age;
		this.dept = dept;
		this.gender = gender;
		this.salary = salary;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public int getSalary() {
		return salary;
	}


	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + salary;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		// ���� overriding�� ���� ������ �޸� �ּҰ����� ��
		// ���� ���ϴ� ������� overriding�� �ؼ� Ư�� ������ �����ϸ�
		// ��ü�� ����!! ��� ������ �ۼ��غ���
		boolean result = false;
		Exam03_Employee target = (Exam03_Employee)obj;
		if(this.getName().equals(target.getName())) {
			result = true;
		}else {
			result = false;
		}
		return result;
	}



	@Override
	public int compareTo(Exam03_Employee o) { //Comparable interface ������ �� �ݵ�� overriding�ؾ���
		// �������� ����
		// ����� ���ϵǸ� ������ �ٲ�
		// 0�̳� ������ ���ϵǸ� ������ �ٲ��� ����
		int result = 0;
		if(this.getSalary() >= o.getSalary()) {
			result = 1;
		}else if(this.getSalary() == o.getSalary()) {
			result = 0; //��ġ��ȭ ���ְٴ�
		}else {
			result = -1;
		}
		
		return result;
	}
	
}
