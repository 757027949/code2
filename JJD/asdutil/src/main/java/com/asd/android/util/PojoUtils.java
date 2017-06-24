package com.asd.android.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import org.apache.commons.codec.binary.Base64;

public class PojoUtils {
	private static PojoUtils pojoUtils;

	public static synchronized PojoUtils getInstance() {
		if (null == pojoUtils) {
			pojoUtils = new PojoUtils();
		}
		return pojoUtils;
	}

	public String getOjbectBase64String(Object object) {
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(object);
			// 将字节流编码成base64的字符窜
			return new String(Base64.encodeBase64(baos.toByteArray()));
		} catch (IOException e) {
			// TODO Auto-generated
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取Object对象
	 *
	 * @param string
	 *            要转化object的数据
	 * @return
	 */
	public Object getObject(String string) {
		Object obj = null;
		// 读取字节
		byte[] base64 = Base64.decodeBase64(string.getBytes());

		// 封装到字节流
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		try {
			// 再次封装
			ObjectInputStream bis = new ObjectInputStream(bais);
			try {
				// 读取对象
				obj = bis.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}
