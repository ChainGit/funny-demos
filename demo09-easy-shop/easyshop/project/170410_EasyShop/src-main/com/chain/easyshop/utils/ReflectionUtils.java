package com.chain.easyshop.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.chain.easyshop.exception.EasyShopRuntimeException;

/**
 * 反射工具类
 * 
 * @author CHAIN
 *
 */
@SuppressWarnings("rawtypes")
public class ReflectionUtils {

	/**
	 * 获取Class定义时父类的所有泛型参数的类型<br/>
	 * 如获取： public EmployeeDao extends BaseDao<com.test.Employee, String>
	 * 的泛型参数类型为com.test.Employee和java.lang.String
	 * 
	 * @param clz
	 *            传入的Class
	 * @return 所有的泛型参数的类型组成的数组
	 */
	public static Class[] getAllGenericSuperType(Class clz) {
		if (clz == null)
			return new Class[] { Object.class };

		Type genericType = clz.getGenericSuperclass();

		if (!(genericType instanceof ParameterizedType))
			return new Class[] { Object.class };

		Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();

		Class[] clzs = new Class[params.length];
		for (int i = 0; i < params.length; i++)
			if (params[i] instanceof Class)
				clzs[i] = (Class) params[i];
			else
				clzs[i] = Object.class;

		return clzs;
	}

	/**
	 * 获取Class定义时父类的所有泛型参数的类型组成的数组中的<b>第index+1个</b><br/>
	 * 如获取： public EmployeeDao extends BaseDao<com.test.Employee, String>
	 * 的泛型参数类型为com.test.Employee和java.lang.String
	 * 
	 * @param clz
	 * @param index
	 * @return
	 */
	public static Class getGenericSuperType(Class clz, int index) {
		Class[] clzs = getAllGenericSuperType(clz);
		if (index < 0 || index > clzs.length - 1)
			return Object.class;
		return clzs[index];
	}

	/**
	 * 获取Class定义时父类的所有泛型参数的类型组成的数组中的<b>第1个</b><br/>
	 * 如获取： public EmployeeDao extends BaseDao<com.test.Employee, String>
	 * 的泛型参数类型为com.test.Employee
	 * 
	 * @param clz
	 * @return
	 */
	public static Class getFirstGenericSuperType(Class clz) {
		return getAllGenericSuperType(clz)[0];
	}

	/**
	 * 获取Class定义时父类的所有泛型参数的类型组成的数组中的<b>最后一个</b><br/>
	 * 如获取： public EmployeeDao extends BaseDao<com.test.Employee, String>
	 * 的泛型参数类型为java.lang.String
	 * 
	 * @param clz
	 * @return
	 */
	public static Class getLastGenericSuperType(Class clz) {
		Class[] clzs = getAllGenericSuperType(clz);
		return clzs[clzs.length - 1];
	}

	/**
	 * 忽略private/protected修饰符，使得能访问字段
	 * 
	 * @param f
	 */
	public static void makeFieldAccessible(Field f) {
		if (f != null && !(Modifier.isPublic(f.getModifiers())))
			f.setAccessible(true);
	}

	/**
	 * 忽略private/protected修饰符，使得能调用方法
	 * 
	 * @param f
	 */
	public static void makeMethodAccessible(Method m) {
		if (m != null && !(Modifier.isPublic(m.getModifiers())))
			m.setAccessible(true);
	}

	/**
	 * 忽略修饰符返回方法
	 * 
	 * @param obj
	 * @param methodName
	 * @return
	 */
	public static Method getDeclaredMethod(Object obj, String methodName, Class<?>... parameterTypes) {
		if (obj == null)
			return null;

		// 向上转型
		for (Class<?> clz = obj.getClass(); clz != Object.class; clz = clz.getSuperclass()) {
			try {
				Method m = clz.getDeclaredMethod(methodName, parameterTypes);
				if (m != null) {
					makeMethodAccessible(m);
					return m;
				}
			} catch (SecurityException | NoSuchMethodException e) {
				continue;
			}
		}

		return null;
	}

	/**
	 * 查找并执行方法，返回执行结果
	 * 
	 * @param obj
	 * @param methodName
	 * @param parameterTypes
	 * @param params
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Object invokeDeclaredMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object[] params)
			throws NoSuchMethodException {
		Method m = getDeclaredMethod(obj, methodName, parameterTypes);
		if (m == null)
			throw new NoSuchMethodException(methodName);

		try {
			return m.invoke(obj, params);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}

	}

	/**
	 * 忽略修饰符返回字段
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getDeclaredField(Object obj, String fieldName) {
		if (obj == null)
			return null;

		for (Class<?> clz = obj.getClass(); clz != Object.class; clz = clz.getSuperclass()) {
			try {
				Field f = clz.getDeclaredField(fieldName);
				if (f != null) {
					makeFieldAccessible(f);
					return f;
				}
			} catch (NoSuchFieldException | SecurityException e) {
				continue;
			}
		}

		return null;
	}

	/**
	 * 忽略修饰符设置字段的值
	 * 
	 * @param obj
	 * @param fieldName
	 * @param val
	 * @throws NoSuchFieldException
	 */
	public static void setDeclaredFieldValue(Object obj, String fieldName, Object val) throws NoSuchFieldException {
		Field f = getDeclaredField(obj, fieldName);
		if (f == null)
			throw new NoSuchFieldException(fieldName);

		try {
			f.set(obj, val);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}
	}

	/**
	 * 忽略修饰符获得字段的值
	 * 
	 * @param obj
	 * @param fieldName
	 * @param val
	 * @throws NoSuchFieldException
	 */
	public static Object getDeclaredFieldValue(Object obj, String fieldName) throws NoSuchFieldException {
		Field f = getDeclaredField(obj, fieldName);
		if (f == null)
			throw new NoSuchFieldException(fieldName);

		try {
			return f.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}

	}

}
