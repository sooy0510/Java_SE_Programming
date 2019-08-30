package javaLambda;
/*
 * Operator는 Function과 하는일이 거의 비슷
 * 입력매개변수가 있고 리턴값이 잇음
 * Function은 매핑용도로 많이 사용됨(입력매개변수를 리턴타입으로 변화, 매핑의 용도)
 * Operator는 연산용도로 많이 사용됨(입력매개변수를 연산에 이용하여 같은 타입의 
 * 리턴값을 돌려주는 형태)
 * 
 * 최대값과 최소값을 구하는 static method를 작성
 */

import java.util.function.IntBinaryOperator;

public class Exam07_LambdaUsingOperator {

	private static int arr[] = {100,92,50,89,34,27,99,3};
	
	//getMaxMin()을 static method로 만들꺼에요
	//사용하는 Operator는 IntBinaryOperator을 이용
	// IntBinaryOperator => interface 타입
	private static int getMaxMin(IntBinaryOperator operator) {
		int result = arr[0];
		
		for(int k : arr) {
			result = operator.applyAsInt(result, k);
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println("최대값 : "+ getMaxMin((a,b) -> {
			//최대값
			return a>=b ? a : b;
		}));
		System.out.println("최소값 : "+ getMaxMin((a,b) -> {
			//최소값
			return a<b ? a : b;
		}));
	}

}
