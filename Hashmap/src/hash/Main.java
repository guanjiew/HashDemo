package hash;

public class Main {

	public static void main(String[] args) {
		MyChainHashLinkedListTable<Integer, Object> htable = new MyChainHashLinkedListTable<Integer, Object>(1000000);
		System.out.println(htable);
		for (int i = 0; i < 2000000; i++) {
			int id  = i;
			String str = "elements" + i;
			htable.add(id, str);
		}
		System.out.println(htable.getValue(9990));
	}

}
