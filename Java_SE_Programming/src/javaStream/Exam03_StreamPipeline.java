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
 *  => 대량의 데이터를 가공해서 축소하는 개념
 *  => sum, average, count, max, min
 *  
 *  만약 Collection안에 reduction하기가 쉽지 않은 형태로 데이터가 들어가있으면
 *  중간처리과정을 거쳐서 reduction하기 좋은 형태로 변환
 *  
 *  Stream은 pipeline을 지원(stream을 연결해서 사용할 수 있음)
 *  
 *  ex)
 *  직원객체를 생성해서 ArrayList안에 여러명의 직원을 저장
 *  이 직원중에 it에 종사하고 남자인 직원을 추려서 해당 직원들의 연봉 평균을 출력
 */


public class Exam03_StreamPipeline {

	private static List<Exam03_Employee> employees = 
			Arrays.asList(
					new Exam03_Employee("홍길동",20,"IT","남자",2000),
					new Exam03_Employee("최길동",30,"Sales","여자",3000),
					new Exam03_Employee("고길동",40,"IT","남자",4000),
					new Exam03_Employee("윤길동",28,"Sales","남자",5000),
					new Exam03_Employee("이수연",25,"IT","여자",6000),
					new Exam03_Employee("뇸뇸",35,"IT","여자",3500),
					new Exam03_Employee("꾸꾸",50,"IT","남자",2500),
					new Exam03_Employee("뿌뿌",40,"Sales","남자",2800),
					new Exam03_Employee("홍길동",20,"IT","남자",2000));
	public static void main(String[] args) {
		//부서가 IT인 사람들 중 남자에 대한 연봉 평균을 구해보자!
		Stream<Exam03_Employee> stream = employees.stream();
//		//stream의 중간처리와 최종처리를 이용해서 원하는 작업을 하자
//		//filter method는 결과값을 가지고 있는 stream을 리턴해줌(it부서인 객체가 들어가잇는 stream)
//		double avg = stream.filter(t->t.getDept().equals("IT")) //중간처리 단계
//						  .filter(t->t.getGender().equals("남자")) //중간처리 단계
//						  .mapToInt(t->t.getSalary()) //stream에 해당하는 정보를 모두 int로 변환 // 중간처리 단계
//						  .average().getAsDouble(); //average는 최종처리
//							//lazy 처리
						
		// stream은 reduction하는 최종처리가 있는지 확인하고 없으면 중간처리를 하지않음
//		System.out.println("IT부서의 남자직원 연봉 평균 : "+avg);
		
		//한번 stream처리를 하면 stream이 닫히기 때문에 주석처리
		//sql문처럼 사용
		
		
		// stream이 가지고 잇는 method
		// 나이가 35이상인 직원 중 남자 직원의 이름을 출력하자
//		stream.filter(t->t.getAge() >= 35)
//									 .filter(t->t.getGender().equals("남자"))
//									 .forEach(t->System.out.println(t.getName()));
		
		// 중복제거를 위한 중간처리
//		int temp[] = {10,20,30,20,50};
//		IntStream s = Arrays.stream(temp);
//		s.distinct().forEach(t->System.out.println(t));
		
		// 객체에 대한 중복제거를 해보자
//		employees.stream()
//				 .distinct()
//				 .forEach(t->System.out.println(t.getName()));
		
		// mapToInt => mapXXX()
		// 정렬(부서가 IT인 사람을 연봉순으로 출력)
//		employees.stream()
//				 .distinct()
//				 .filter(t->t.getDept().equals("IT"))
//				 .sorted(Comparator.reverseOrder() ) //기본적으로는 오름차순 정렬, 인자가 있으면 내림차순
//				 .forEach(t->System.out.println(t.getName() + ", "+t.getSalary()));
		
		//반복
		//forEach()를 이용하면 스트림안의 요소를 반복할 수 있어요!
		// forEach()는 최종 처리 함수, 중간처리함수로 사용할수없음
		// -> 중간처리함수로 반복처리하는 함수가 하나 더 제공 : peek
//		employees.stream()
//				 .peek(t->System.out.println(t.getName()))
//				 .mapToInt(t->t.getSalary())
//				 .forEach(t->System.out.println(t));
		
		//확인용 최종 처리 함수
		//50살이상인 사람만 추출해서 이름 출력
//		boolean result = employees.stream()
//				 .filter(t->t.getAge() >= 50)
//				 .allMatch(t->(t.getAge() >55));
//		System.out.println(result);
		
		//최종 확인용 함수로 forEach를 많이 사용했는데
		//forEach 말고 collect()를 이용해 보아요
		//나이가 50살 이상인 사람들의 연봉을 구해서
		//List<Integer>형태의 ArrayList에 저장해 보아요
		List<Integer> tmp = employees.stream()
		//Set<Integer> tmp = employees.stream()
				 .filter(t->t.getAge() >= 50)
				 .map(t->t.getSalary())
				 .collect(Collectors.toList());
				 //.collect(Collectors.toCollection(HashSet :: new));
		
		System.out.println(tmp);
		//당연히 set으로도 저장할 수 있고, map으로도 저장가능
	}

}

class Exam03_Employee implements Comparable<Exam03_Employee>{ //Comparable을 구현해야 객체들끼리 정렬가능하다, 객체들끼리 비교가능한 형태로 만들자
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
		// 만약 overriding을 하지 않으면 메모리 주소가지고 비교
		// 내가 원하는 방식으로 overriding을 해서 특정 조건을 만족하면
		// 객체가 같아!! 라는 식으로 작성해보자
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
	public int compareTo(Exam03_Employee o) { //Comparable interface 구현할 때 반드시 overriding해야함
		// 정수값을 리턴
		// 양수가 리턴되면 순서를 바꿈
		// 0이나 음수가 리턴되면 순서를 바꾸지 않음
		int result = 0;
		if(this.getSalary() >= o.getSalary()) {
			result = 1;
		}else if(this.getSalary() == o.getSalary()) {
			result = 0; //위치변화 안주겟다
		}else {
			result = -1;
		}
		
		return result;
	}
	
}
