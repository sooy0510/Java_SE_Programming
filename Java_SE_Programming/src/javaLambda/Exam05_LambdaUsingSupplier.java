package javaLambda;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/*
 * Supplier��� �Ҹ��� �Լ��� �������̽� �������� �츮���� �����Ǵµ�
 * �� �������̽��� Ư¡�� �Ű������� ����. ��� ���ϰ��� ����
 * getXXX()��� method�� �߻� �޼ҵ� ���·� �������̽��ȿ� ����Ǿ� ����
 * 
 * ģ������� List<String>���·� ������
 */
public class Exam05_LambdaUsingSupplier {
	// �ζ� ��ȣ (1~46) �� �ڵ����� �����ϰ� ����ϴ� ������ Method�� �ۼ�
	public static void generateLotto(IntSupplier supplier, Consumer<Integer> consumer) {

		Set<Integer> lotto = new HashSet<Integer>();
		while (lotto.size() != 6) {
			lotto.add(supplier.getAsInt());
		}

		for (Integer i : lotto) {
			consumer.accept(i);
		}
	}

	public static void main(String[] args) {
		final List<String> myBuddy = Arrays.asList("ȫ�浿", "��浿", "�̼���", "������");

		// Supplier�� �̿��ؼ� �������� 1���� ģ���� ����� ���ƿ�
		Supplier<String> supplier = () -> {
			return myBuddy.get((int) (Math.random() * 4)); // get : List���� Ư�� index�� ���� �̴� method
		};

		System.out.println(supplier.get());

		// ------------------------------------------------
		// IntSupplier : �������� 1�� �����ϴ� supplier
		// �ζ� ��ȣ�� �ڵ����� �����ϰ� ����ϴ� ������ method�� �ۼ�
		// generateLotto(���ö��̾�, ������);
		generateLotto(() -> {
			return ((int) (Math.random() * 45)) + 1;
		}, t -> {
			System.out.println(t + " ");
		});
	}
}
