package test.sizeof.simple1;

public class C extends B{
	private String s;
	private Integer bla;
	
	public C(){
		s = "Test";
		bla = new Integer(1);
	}
	
	public String toString(){
		return "Object "+s;
	}
	

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public Integer getBla() {
		return bla;
	}

	public void setBla(Integer bla) {
		this.bla = bla;
	}
}
