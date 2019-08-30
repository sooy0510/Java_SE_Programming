package javaStream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/*
 * Stream은 Java 8에서 도입이 되었어요. 주의해야 할건 java.io안에 있는 Stream과 다르다 
 * 사용 용도 : 컬렉션처리(List,Set, Map, 배열)를 위해서 사용이 돼요
 * 			컬렉션안의 데이터를 반복시키는 반복자의 역할을 하는게 stream
 * 			ex)ArrayList안에 학생 객체가 5개 있으면 그 5개를 하나씩
 * 				가져오는 역할을 수행. => 이렇게 가져온 데이터를 람다식을 이용해서 처리가능
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
			Arrays.asList("홍길동","김길동","최길동","박길동");
	
	private static List<Exam01_Student> students = 
			Arrays.asList(
					new Exam01_Student("홍길동",10,20),
					new Exam01_Student("박길동",10,20),
					new Exam01_Student("신사임당",10,20),
					new Exam01_Student("유관순",10,20),
					new Exam01_Student("이순신",10,20));
	
	public static void main(String[] args) {
		//사람이름 출력
		//방법 1. 일반 for문(첨자를 사용)을 이용해서 처리
		//		=> 비효율적(첨자와 반복자)
		for(int i=0; i<myBuddy.size(); i++) {
			System.out.println(myBuddy.get(i));
		}
		//방법 2. 첨자를 이요한 반복을 피하기 위해 Iterator을 사용
		//		=> 첨자가 없음
		Iterator<String> iter = myBuddy.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		//방법 3. Stream
		//		=> 반복자가 필요없음. 내부 반복자 이용
		// 			병렬처리 가능, 로직에만 집중 , 좀 더 객체지향적
		Consumer<String> consumer = t -> {
			System.out.println(t + ", "
					+ Thread.currentThread().getName());
		};
		
		Stream<String> stream1 = myBuddy.stream();//stream은 컬렉션 안의 데이터를 반복시킴
		stream1.forEach(consumer);
		
		Stream<String> stream2 = myBuddy.parallelStream();//stream은 컬렉션 안의 데이터를 반복시킴
		//stream.forEach(t -> System.out.println(t));
		stream2.forEach(consumer);  
		
		// arrayList는 stream이라는 메소드가 잇음
		Stream<Exam01_Student> studentStream = students.stream();
		// function lambda식을 이용하여 객체를  mapping
		// t라는 객체가 들어가서 getkor()를 이용
		double avg = 
				studentStream.mapToInt(t->t.getKor()).average().getAsDouble();
		System.out.println("국어성적의 평균 : "+avg);
	}

}
