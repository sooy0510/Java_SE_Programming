package javaStream;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
 * java.util.stream package안에 우리가 사용할 수 잇는 stream이 존재
 * BaseStream으로부터 상속받아서 몇몇개의 Stream이 존재
 * Stream => 해당 Stream안에 객체 들어가 있는 경우
 * IntStream => 해당 Stream안에 int값이 들어가 있는 경우 사용
 * LongStream
 * DoubleStream도 존재 
 */

public class Exam02_StreamSource {

	private static List<String> names =
			Arrays.asList("홍길동","김길동","최길동");
	private static int myArr[] = {10,20,30,40,50};
	
	public static void main(String[] args) {
		// List로부터 Stream을 생성할 수 있음
		Stream<String> stream1 = names.stream();
		//Stream<String> stream1 = names.parallelStream();
		stream1.forEach(t->System.out.println(t));
		IntStream stream2 = Arrays.stream(myArr);
		System.out.println(stream2.sum());
		IntStream stream3 = IntStream.rangeClosed(1, 10); //1과 10포함
		System.out.println(stream3.sum());
		
		//파일로부터 stream을 생성 할 수 있어요
		Path path = Paths.get("asset/readme.txt");
		//File객체(java.io)와 유사한 java.nio에 포함된 class가 바로 Path
		try {
			Stream<String> stream4 = Files.lines(path,
					Charset.forName("UTF-8"));
			stream4.forEach(t -> System.out.println(t));
			stream4.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
