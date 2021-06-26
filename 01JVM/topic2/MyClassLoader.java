import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class MyClassLoader extends ClassLoader {

	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
		MyClassLoader classLoader = new MyClassLoader();
		Class<?> helloClass = classLoader.loadClass("Hello");
		Object hello = helloClass.newInstance();
		helloClass.getDeclaredMethods()[0].invoke(hello);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] bytes = new byte[1024];
		// get file
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("./Hello.xlass");
			int size = fis.available();
			bytes = new byte[size];
			fis.read(bytes);
			// 255-x
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = (byte) (255 - bytes[i]);
			}
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defineClass(name, bytes, 0, bytes.length);
	}

}