package ch.bitwave.platform.reflect;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.apache.commons.lang.WordUtils;

/**
 * Contains helper functions for manipulating beans via reflection.
 * 
 */
@SuppressWarnings("nls")
public class ReflectionUtility {
	private static void assertAttributeNameValidity(final String name) {
		if (name == null) {
			throw new IllegalArgumentException("Attribute name must not be null");
		} else if (name.isEmpty()) {
			throw new IllegalArgumentException("Attribute name must not be empty");
		}
	}

	protected static String deriveCamelCaseSetterFromAttributeName(final String name) {
		assertAttributeNameValidity(name);
		return "set" + WordUtils.capitalize(name); //$NON-NLS-1$
	}

	private static String deriveCamelCaseGetterFromAttributeName(final String name) {
		assertAttributeNameValidity(name);
		return "get" + WordUtils.capitalize(name); //$NON-NLS-1$
	}

	/**
	 * Sets the given attribute in the given bean of the given class
	 * 
	 * @param bean
	 *            the bean whose attribute to set.
	 * @param attributeName
	 *            the name of the attribute to set.
	 * @param attributeValue
	 *            the string representation of the value to set.
	 * @throws BeanModificationException
	 *             if the attribute could not be set for whatever reason.
	 */
	public static void setBeanAttribute(final Object bean, final String attributeName,
			final Object attributeValue) throws BeanModificationException {
		Class<?> beanClass = bean.getClass();
		Class<?> nClass = beanClass;
		while (!setOnClass(bean, attributeName, attributeValue, nClass)) {
			nClass = nClass.getSuperclass();
			if (nClass == null) {
				throw new BeanModificationException(String.format(
						"Member %s does not exist in class %s.", attributeName, bean.getClass()
								.getName()));
			}
		}
	}

	/**
	 * @param bean
	 * @param attributeName
	 * @param attributeValue
	 * @param beanClass
	 * @throws BeanModificationException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean setOnClass(final Object bean, final String attributeName,
			final Object attributeValue, final Class<?> beanClass) throws BeanModificationException {
		Method[] methods = beanClass.getDeclaredMethods();
		String setter = deriveCamelCaseSetterFromAttributeName(attributeName);
		for (Method method : methods) {
			if (setter.equals(method.getName())) {
				method.setAccessible(true);
				Class<?>[] parameters = method.getParameterTypes();
				if (parameters.length == 1) {
					try {
						Class<?> paramClass = parameters[0];
						if (paramClass == String.class) {
							method.invoke(bean, attributeValue);
							return true;
						} else if (paramClass == Double.class || paramClass == double.class) {
							method.invoke(bean, Double.valueOf(attributeValue.toString()));
							return true;
						} else if (paramClass == Long.class || paramClass == long.class) {
							method.invoke(bean, Long.valueOf(attributeValue.toString()));
							return true;
						} else if (paramClass == Integer.class || paramClass == int.class) {
							method.invoke(bean, Integer.valueOf(attributeValue.toString()));
							return true;
						} else if (paramClass == Boolean.class || paramClass == boolean.class) {
							method.invoke(bean, Boolean.valueOf(attributeValue.toString()));
							return true;
						} else if (paramClass == File.class) {
							method.invoke(bean, new File(attributeValue.toString()));
							return true;
						} else if (Enum.class.isAssignableFrom(paramClass)) {
							method.invoke(
									bean,
									Enum.valueOf((Class<Enum>) paramClass,
											attributeValue.toString()));
							return true;
						} else {
							throw new BeanModificationException(String.format(
									"Type %s is not supported.", paramClass.getSimpleName()));
						}
					} catch (InvocationTargetException e) {
						Throwable targetException = e.getTargetException();
						String reason;
						if (targetException != null) {
							reason = targetException.getMessage();
						} else {
							reason = e.getMessage();
						}
						throw new BeanModificationException(String.format(
								"Failed to invoke setter of attribute %s due to: %s",
								attributeName, reason), e);
					} catch (IllegalAccessException e) {
						throw new BeanModificationException(String.format(
								"No access to setter of attribute %s due to: %s", attributeName,
								e.getMessage()), e);
					}
				}
			}
		}
		return false;
	}

	/**
	 * Gets the value of the given attribute of the given bean.
	 * 
	 * @param bean
	 *            the bean to access.
	 * @param attributeName
	 *            the name of the attribute to read.
	 * @return the attribute value.
	 * @throws BeanModificationException
	 *             if there was a problem with the reflective call to the bean
	 *             getter of the attribute.
	 */
	public static Object getBeanAttribute(final Object bean, final String attributeName)
			throws BeanModificationException {
		Class<?> beanClass = bean.getClass();
		while (true) {
			try {
				return getOnClass(bean, attributeName, beanClass);
			} catch (MemberNotFoundException e) {
				beanClass = beanClass.getSuperclass();
				if (beanClass == null) {
					throw new BeanModificationException(String.format("Member %s does not exist",
							attributeName), e);
				}
			}
		}
	}

	/**
	 * Gets the value of the given attribute of the given bean. The attribute
	 * name may contain dots to traverse the object graph.
	 * 
	 * @param bean
	 *            the bean to access.
	 * @param attributeName
	 *            the name of the attribute to read.
	 * @return the attribute value.
	 * @throws BeanModificationException
	 *             if there was a problem with the reflective call to the bean
	 *             getter of the attribute.
	 */
	public static Object getIndirectBeanAttribute(final Object bean, final String attributeName)
			throws BeanModificationException {
		String[] components = attributeName.split("\\.");
		Object curBean = bean;
		for (int i = 0; i < components.length; i++) {
			String component = components[i];
			curBean = getBeanAttribute(curBean, component);
			if (curBean == null && i < components.length) {
				throw new BeanModificationException(String.format(
						"Indirection \"%s\" in attribute \"%s\" returned null.", component,
						attributeName));
			}
		}
		return curBean;
	}

	/**
	 * @param bean
	 * @param attributeName
	 * @param beanClass
	 * @return
	 * @throws BeanModificationException
	 */
	private static Object getOnClass(final Object bean, final String attributeName,
			final Class<?> beanClass) throws BeanModificationException {
		Method[] methods = beanClass.getDeclaredMethods();
		String getter = deriveCamelCaseGetterFromAttributeName(attributeName);
		for (Method method : methods) {
			if (getter.equals(method.getName())) {
				Class<?>[] parameters = method.getParameterTypes();
				if (parameters.length == 0) {
					try {
						method.setAccessible(true);
						return method.invoke(bean);
					} catch (InvocationTargetException e) {
						throw new BeanModificationException(String.format(
								"Failed to invoke getter of attribute %s", attributeName), e);
					} catch (IllegalAccessException e) {
						throw new BeanModificationException(String.format(
								"No access to getter of attribute %s", attributeName), e);
					}
				}
			}
		}
		throw new MemberNotFoundException(String.format("Member %s does not exist", attributeName));
	}

	/**
	 * Returns all bean setters for the given bean. Setters begin with "set" and
	 * have only a single parameter.
	 * 
	 * @param bean
	 * @return the list of methods.
	 */
	public static List<Method> getBeanSetters(final Object bean) {
		List<Method> result = new ArrayList<Method>();
		Class<? extends Object> curClass = bean.getClass();
		while (curClass != null) {
			Method[] methods = curClass.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("set")) {
					Class<?>[] parameters = method.getParameterTypes();
					if (parameters.length == 1) {
						result.add(method);
					}
				}
			}
			curClass = curClass.getSuperclass();
		}
		return result;
	}

	/**
	 * Returns all public bean setters for the given bean. Getters begin with
	 * "get" or "is" and have no parameters.
	 * 
	 * @param bean
	 * @return the list of methods.
	 */
	public static List<Method> getBeanGetters(final Object bean) {
		List<Method> result = new ArrayList<Method>();
		Class<? extends Object> curClass = bean.getClass();
		while (curClass != null) {
			Method[] methods = curClass.getDeclaredMethods();
			for (Method method : methods) {
				String name = method.getName();
				if (name.startsWith("get") || name.startsWith("is")) {
					Class<?>[] parameters = method.getParameterTypes();
					if (parameters.length == 0) {
						method.setAccessible(true);
						result.add(method);
					}
				}
			}
			curClass = curClass.getSuperclass();
		}
		return result;
	}

	/**
	 * Derives an attribute name from the given getter.
	 * 
	 * @param getterName
	 * @return the attribute name.
	 */
	public static String deriveAttributeFromGetter(@Nonnull final String getterName) {
		if (getterName.startsWith("get")) {
			return decapitalize(getterName.substring(3));
		}
		if (getterName.startsWith("is")) {
			return decapitalize(getterName.substring(2));
		}
		throw new RuntimeException(String.format("\"%s\" is not a bean setter.", getterName));
	}

	/**
	 * Converts the first character of the text to lower case.
	 * 
	 * @param text
	 * @return
	 */
	protected static String decapitalize(final String text) {
		if (text.length() == 0) {
			return text;
		}
		return text.substring(0, 1).toLowerCase() + text.substring(1);
	}

	/**
	 * Locates a method with the given name and signature in the given type and
	 * all its super types.
	 * 
	 * @param type
	 *            the type on which to search the method.
	 * @param name
	 *            the name of the method to retrieve.
	 * @param parameterTypes
	 *            the signature of the method to retrieve.
	 * @return a reference to the method, or null if it does not exists.
	 */
	@CheckForNull
	public static Method findMethod(@Nonnull final Class<?> type, final String name,
			final Class<?>... parameterTypes) {
		Class<?> curType = type;
		while (curType != null) {
			try {
				Method method = curType.getDeclaredMethod(name, parameterTypes);
				return method;
			} catch (SecurityException e) {
				throw new RuntimeException(String.format(
						"Failed to find method due to a security exception: %s", e.getMessage()), e);
			} catch (NoSuchMethodException e) {
				curType = curType.getSuperclass();
			}
		}
		return null;
	}

	public static Object getField(final Object bean, final String name) {
		Field field = findField(bean.getClass(), name);
		field.setAccessible(true);
		try {
			return field.get(bean);
		} catch (Exception e) {
			throw new RuntimeException(String.format(
					"Failed to get value of field \"%s\" from bean %s due to: %s", name, bean), e);
		}
	}

	public static Field findField(@Nonnull final Class<?> type, final String name) {
		Class<?> curType = type;
		while (curType != null) {
			try {
				Field field = curType.getDeclaredField(name);
				return field;
			} catch (SecurityException e) {
				throw new RuntimeException(String.format(
						"Failed to find field due to a security exception: %s", e.getMessage()), e);
			} catch (NoSuchFieldException e) {
				curType = curType.getSuperclass();
			}
		}
		return null;
	}

}
