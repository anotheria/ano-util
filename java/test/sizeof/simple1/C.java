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

	public void setS(String aS) {
		this.s = aS;
	}

	public Integer getBla() {
		return bla;
	}

	public void setBla(Integer aBla) {
		this.bla = aBla;
	}
}
