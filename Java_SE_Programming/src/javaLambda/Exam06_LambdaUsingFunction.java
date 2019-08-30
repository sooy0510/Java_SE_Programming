package javaLambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/*
 * Function 함수적 인터페이스는 입력매개변수와 리턴값이 applyXXX() method가 제공됨
 * 일반적으로 입력매개변수를 리턴값으로 mapping 시킬 떄 일반적으로 사용됨
 * 
 * Function<T,R> func = t -> {return ~~~};
 * T : 입력 매개변수의 generic
 * R : 리턴값의 generic
 * 
 * Function은 입력은 객체로 들어오는데 return은 다른형태로 나갈떄(mapping)시키는데
 * 많이 사용
 * 
 *
 */

// Student VO class를 정의해요
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
	
	//static 사용 이유, 객체 안만들고 main에서 바로 access해서 사용하려고
	private static List<Exam06_Student> students = 
			Arrays.asList(new Exam06_Student("홍길동",10,20,30),
					new Exam06_Student("김길동", 50, 60, 70),
					new Exam06_Student("이순신", 90, 20, 30),
					new Exam06_Student("이수연", 10, 100, 70));
	
	private static void printName(Function<Exam06_Student, String> function) {
		// 처리하는 코드 자체가 넘어가는 형태
		for(Exam06_Student s : students) {
			System.out.println(function.apply(s));
		}
	}
	
	
	private static double getAvg(ToIntFunction<Exam06_Student> function) {
		// ToIntFunction -> return값이 int
		 int sum=0; 
		 for(Exam06_Student s : students) {
			 sum += function.applyAsInt(s); 
		 } 
		 //System.out.println(sum /= cnt);
		 return (double)sum / students.size();
		
	}
	
	
	
	public static void main(String[] args) {
		//학생이름을 출력!!
		printName(t -> {return t.getsName();});
		
		// getAvg()라는 static method를 만들어서 다음의 내용을 출력하세요
		// 학생들의 국어성적 평균 => getAvg()
		System.out.println("국어평균 : "+getAvg(t -> t.getsKor()));
		// 학생들의 영어성적 평균 => getAvg()
		System.out.println("영어평균 : "+getAvg(t -> t.getsEng()));
		// 학생들의 수학성적 평균 => getAvg()
		System.out.println("수학평균 : "+getAvg(t -> t.getsMath()));
	}
}
