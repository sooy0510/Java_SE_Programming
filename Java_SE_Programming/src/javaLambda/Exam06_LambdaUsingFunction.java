package javaLambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/*
 * Function �Լ��� �������̽��� �Է¸Ű������� ���ϰ��� applyXXX() method�� ������
 * �Ϲ������� �Է¸Ű������� ���ϰ����� mapping ��ų �� �Ϲ������� ����
 * 
 * Function<T,R> func = t -> {return ~~~};
 * T : �Է� �Ű������� generic
 * R : ���ϰ��� generic
 * 
 * Function�� �Է��� ��ü�� �����µ� return�� �ٸ����·� ������(mapping)��Ű�µ�
 * ���� ���
 * 
 *
 */

// Student VO class�� �����ؿ�
class Exam06_Student{
	private String sName;
	private int sKor;
	private int sEng;
	private int sMath;
	
	
	public Exam06_Student() {
		super();
	}

	public Exam06_Student(String sName, int sKor, int sEng, int sMath) {
		super();
		this.sName = sName;
		this.sKor = sKor;
		this.sEng = sEng;
		this.sMath = sMath;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public int getsKor() {
		return sKor;
	}

	public void setsKor(int sKor) {
		this.sKor = sKor;
	}

	public int getsEng() {
		return sEng;
	}

	public void setsEng(int sEng) {
		this.sEng = sEng;
	}

	public int getsMath() {
		return sMath;
	}

	public void setsMath(int sMath) {
		this.sMath = sMath;
	}
	
}

public class Exam06_LambdaUsingFunction {
	
	//static ��� ����, ��ü �ȸ���� main���� �ٷ� access�ؼ� ����Ϸ���
	private static List<Exam06_Student> students = 
			Arrays.asList(new Exam06_Student("ȫ�浿",10,20,30),
					new Exam06_Student("��浿", 50, 60, 70),
					new Exam06_Student("�̼���", 90, 20, 30),
					new Exam06_Student("�̼���", 10, 100, 70));
	
	private static void printName(Function<Exam06_Student, String> function) {
		// ó���ϴ� �ڵ� ��ü�� �Ѿ�� ����
		for(Exam06_Student s : students) {
			System.out.println(function.apply(s));
		}
	}
	
	
	private static double getAvg(ToIntFunction<Exam06_Student> function) {
		// ToIntFunction -> return���� int
		 int sum=0; 
		 for(Exam06_Student s : students) {
			 sum += function.applyAsInt(s); 
		 } 
		 //System.out.println(sum /= cnt);
		 return (double)sum / students.size();
		
	}
	
	
	
	public static void main(String[] args) {
		//�л��̸��� ���!!
		printName(t -> {return t.getsName();});
		
		// getAvg()��� static method�� ���� ������ ������ ����ϼ���
		// �л����� ����� ��� => getAvg()
		System.out.println("������� : "+getAvg(t -> t.getsKor()));
		// �л����� ����� ��� => getAvg()
		System.out.println("������� : "+getAvg(t -> t.getsEng()));
		// �л����� ���м��� ��� => getAvg()
		System.out.println("������� : "+getAvg(t -> t.getsMath()));
	}
}
