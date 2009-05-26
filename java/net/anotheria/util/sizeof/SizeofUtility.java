package net.anotheria.util.sizeof;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.anotheria.util.StringUtils;
import test.sizeof.simple1.A;
import test.sizeof.simple1.C;

public class SizeofUtility {
	
	public static final int SIZEOF_INT = 4;
	public static final int SIZEOF_BYTE = 1;
	public static final int SIZEOF_LONG = 8;
	public static final int SIZEOF_FLOAT = 4;
	public static final int SIZEOF_DOUBLE = 8;
	public static final int SIZEOF_CHAR = 2;
	public static final int SIZEOF_SHORT = 2;
	public static final int SIZEOF_BOOLEAN = 4;
	
	public static final int VM = 64/8;
	//change that to SIZEOF_INT on 32 bit
	public static final int SIZEOF_REFERENCE = VM;
	
	public static final int SIZEOF_OBJECT_OVERHEAD = 2*VM;
	
	private static Map<Class<?>, SizeofHelper> helpers;
	
	static{
		helpers = new HashMap<Class<?>, SizeofHelper>();
		helpers.put(String.class, new StringHelper());
		helpers.put(Object.class, new ObjectHelper());
	}
	
	public static final int shortSizeof(Object o) throws Exception{
		return sizeof(o, false);
	}
	
	public static final int deepSizeof(Object o) throws Exception{
		return sizeof(o, true);
	}
	
	public static int sizeof(Object o, boolean deep) throws Exception{
		
		int ret = 0;
		Class<?> c = o.getClass();
		ret += sizeofClazz(c, o);
		
		return ret;
	}
	
	private static int sizeofClazz(Class<?> c, Object o) throws Exception{
		//System.out.println("enter Sizeof "+o+" of class "+c);
		
		int ret = 0;

		SizeofHelper helper = helpers.get(c);
		if (helper!=null){
			ret = helper.sizeof(o);
		}else{
		
			Field[] fields = c.getDeclaredFields();
			for (Field f : fields){
				int mod = f.getModifiers();
				if (Modifier.isStatic(mod))
					continue;
				//System.out.println(f);
				//System.out.println("\t"+f.getType()+", "+f.getType().getModifiers());
				//printMod(f.getType().getModifiers());
				boolean counted = false;
				if (f.getType().isPrimitive()){
					ret += sizeofPrimitive(f);
					//System.out.println("=== "+sizeofPrimitive(f)+" === "+f.getName());
					counted = true;
				}
				
				if (!counted && f.getType().isArray()){
					System.out.println("WARN! ARRAY! "+f.getName());
					//System.out.println(f.isAccessible());
					counted = true;
				}
				
				if (!counted){
					//not primitive
					ret += SIZEOF_REFERENCE;
					Object fieldValue = getObjectFromField(f, o);
					if (fieldValue!=null){
						ret += sizeofClazz(f.getType(), fieldValue);
					}
					/*if (Modifier.isPublic(f.getModifiers())){
						Object fieldValue = f.get(o);
						if (fieldValue!=null)
							ret += sizeofClazz(f.getType(), fieldValue);
						
					}else{
						System.out.println(f+" is not accessible");
					}*/
				}
			}
		}

		//System.out.println("\tsizeof "+c+" -> "+ret);
		if (!c.getName().equals("java.lang.Object"))
			ret += sizeofClazz(c.getSuperclass(), o);
		return ret;
	}
	
	
	private static Object getObjectFromField(Field f, Object owner) throws Exception{
		if (Modifier.isPublic(f.getModifiers())){
			return f.get(owner);
		}
		//trying to lookup an accessor method
		Class<?> c = owner.getClass();
		String methodName = "get"+StringUtils.capitalize(f.getName());
		//System.out.println("checking for method "+methodName);
		Method m = c.getDeclaredMethod(methodName);
		if (m==null){
			//System.out.println("No accessor "+methodName+" found!");
			return null;
		}
		
		if (!Modifier.isPublic(m.getModifiers())){
			System.out.println("can't access method: "+methodName);
			return null;
		}
			
		return m.invoke(owner);
	}
	
	private static int sizeofPrimitive(Field f){
		Class<?> c = f.getType();
		String name = c.getName();
		
		//System.out.println("Primitive: "+name);
		
		if ("int".equals(name))
			return SIZEOF_INT;
		if ("long".equals(name))
			return SIZEOF_LONG;
		if ("char".equals(name))
			return SIZEOF_CHAR;
		if ("byte".equals(name))
			return SIZEOF_BYTE;
		if ("float".equals(name))
			return SIZEOF_FLOAT;
		if ("double".equals(name))
			return SIZEOF_DOUBLE;
		if ("short".equals(name))
			return SIZEOF_SHORT;
		if ("boolean".equals(name))
			return SIZEOF_BOOLEAN;
				
		
		System.out.println("WARN - Unrecognized primitive type: "+f.getType());
		return 0;
	}
	
	
	public static void main(String a[]) throws Exception{
		//test(new A());
		//test(new B());
		test(new C());
		
		//testList();
		
		
	}
	
	private static void testList() throws Exception{
		ArrayList<A> list = new ArrayList(2);
		list.add(new A());
		list.add(new A());
		test(list); 
	}
	
	private static void test(Object o) throws Exception{
		int size = sizeof(o, true);
		System.out.println("Size of "+o+" is "+size);
		System.out.println("=======================");
	}
	
	private static void printMod(int mod){
		printMod("abstract", Modifier.isAbstract(mod));
		printMod("final", Modifier.isFinal(mod));
		printMod("interface", Modifier.isInterface(mod));
		printMod("native", Modifier.isNative(mod));
		printMod("private", Modifier.isPrivate(mod));
		printMod("protected", Modifier.isProtected(mod));
		printMod("public", Modifier.isPublic(mod));
		printMod("static", Modifier.isStatic(mod));
		printMod("strict", Modifier.isStrict(mod));
		printMod("synchronized", Modifier.isSynchronized(mod));
		printMod("transient", Modifier.isTransient(mod));
		printMod("volatile", Modifier.isVolatile(mod));
		
		
		
	}
	
	private static void printMod(String s, boolean v){
		if (v)
			System.out.println("\t"+s);
	}

}
