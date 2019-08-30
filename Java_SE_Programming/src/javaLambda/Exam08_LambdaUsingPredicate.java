package javaLambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/*
 * Predicate는 입력매개변수가 있다. boolean리던
 * 사용되는 method는 testXXX()가 잇다
 * 입력매개변수값을 조사하여 TRUE, FALSE값을 리턴해야 하는경우
 * 
 * 학생객체를 만들어서 List로 유지할것
 * static method를 만들어서 람다식으로 인자를 넘길것이다
 * 성별에 따른 특정 과목의 평균을 구할 수 있도록 method를 작성
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
					new Exam08_Student("홍길동",10,20,30,"남자"),
					new Exam08_Student("박길동",10,20,30,"남자"),
					new Exam08_Student("신사임당",10,20,30,"여자"),
					new Exam08_Student("유관순",10,20,30,"여자"),
					new Exam08_Student("이순신",10,20,30,"남자"));
	
	// static method를 하나 정의하는데 성별에 따른 특정 과목의 평균을 구하는
	// 작업을 할 거에요
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
		System.out.println("남자 수학 평균"+avg(t -> t.getGender().equals("남자"), t -> t.getMath()));
		System.out.println("여자 영어 평균"+avg(t -> t.getGender().equals("여자"), t -> t.getEng()));
		}
}
