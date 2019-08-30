package javaLambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/*
 * Predicate�� �Է¸Ű������� �ִ�. boolean����
 * ���Ǵ� method�� testXXX()�� �մ�
 * �Է¸Ű��������� �����Ͽ� TRUE, FALSE���� �����ؾ� �ϴ°��
 * 
 * �л���ü�� ���� List�� �����Ұ�
 * static method�� ���� ���ٽ����� ���ڸ� �ѱ���̴�
 * ������ ���� Ư�� ������ ����� ���� �� �ֵ��� method�� �ۼ�
 */

class Exam08_Student{
	private String name;
	private int kor;
	private int eng;
	private int math;
	private String gender;
	
	public Exam08_Student() {
		super();
	}

	public Exam08_Student(String name, int kor, int eng, int math, String gender) {
		super();
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.gender = gender;
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

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}

public class Exam08_LambdaUsingPredicate {
	private static List<Exam08_Student> students =
			Arrays.asList(
					new Exam08_Student("ȫ�浿",10,20,30,"����"),
					new Exam08_Student("�ڱ浿",10,20,30,"����"),
					new Exam08_Student("�Ż��Ӵ�",10,20,30,"����"),
					new Exam08_Student("������",10,20,30,"����"),
					new Exam08_Student("�̼���",10,20,30,"����"));
	
	// static method�� �ϳ� �����ϴµ� ������ ���� Ư�� ������ ����� ���ϴ�
	// �۾��� �� �ſ���
	private static double avg(Predicate<Exam08_Student> predicate, 
			ToIntFunction<Exam08_Student> function) {
		int sum = 0;
		int count = 0;
		
		for(Exam08_Student s : students) {
			if(predicate.test(s)) {
				count++;
				sum += function.applyAsInt(s);
			}
		}
		
		return (double)sum/count;
	}
	
	public static void main(String[] args) {
		System.out.println("���� ���� ���"+avg(t -> t.getGender().equals("����"), t -> t.getMath()));
		System.out.println("���� ���� ���"+avg(t -> t.getGender().equals("����"), t -> t.getEng()));
		}
}
