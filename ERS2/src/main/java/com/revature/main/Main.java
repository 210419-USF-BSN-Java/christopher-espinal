package com.revature.main;

public class Main {
	private String string;
	
	public static void main(String[] args) {
		Main main1 = new Main("hi");
		Main main2 = new Main("hello");
		
		System.out.println(main1 == main2);
		System.out.println(main1.equals(main2));

		main2 = main1;
		System.out.println(main1 == main2);
		System.out.println(main1.equals(main2));

		main2.setString("yo");
		System.out.println(main1 == main2);
		System.out.println(main1.equals(main2));

		main1.setString("yolo");
		System.out.println(main1 == main2);
		System.out.println(main1.equals(main2));
		
		Main main3 = new Main("yolo");
		System.out.println(main1 == main3);
		System.out.println(main1.equals(main3));

		Main main4 = new Main(main1.getString());
		System.out.println(main1 == main4);
		System.out.println(main1.equals(main4));
	
	}
	
	public Main(String string) {
		this.string = string;
	}

	
	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((string == null) ? 0 : string.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Main other = (Main) obj;
		if (string == null) {
			if (other.string != null) {
				return false;
			}
		} else if (!string.equals(other.string)) {
			return false;
		}
		return true;
	}
	
	
}
