//package com.jy.jz.zbx.dms;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.StreamCorruptedException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Map;
//
//public class SPUtils {
//
//  public SPUtils() {
//        /* cannot be instantiated */
//    throw new UnsupportedOperationException("cannot be instantiated");
//  }
//
//  /**
//   * 保存在手机里面的文件名
//   */
//  public static final String FILE_NAME = "share_data";
//
//  /**
//   * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
//   */
//  public static void put(Context context, String key, Object object) {
//    if (context == null) {
//      return;
//    }
//    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//    if (sp == null) {
//      return;
//    }
//    Editor editor = sp.edit();
//
//    if (object instanceof String) {
//      editor.putString(key, (String) object);
//    } else if (object instanceof Integer) {
//      editor.putInt(key, (Integer) object);
//    } else if (object instanceof Boolean) {
//      editor.putBoolean(key, (Boolean) object);
//    } else if (object instanceof Float) {
//      editor.putFloat(key, (Float) object);
//    } else if (object instanceof Long) {
//      editor.putLong(key, (Long) object);
//    } else {
//      editor.putString(key, object.toString());
//    }
//
//    SharedPreferencesCompat.apply(editor);
//  }
//
//  /**
//   * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
//   */
//  public static Object get(Context context, String key, Object defaultObject) {
//    if (context == null) {
//      return null;
//    }
//    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//
//    if (defaultObject instanceof String) {
//      return sp.getString(key, (String) defaultObject);
//    } else if (defaultObject instanceof Integer) {
//      return sp.getInt(key, (Integer) defaultObject);
//    } else if (defaultObject instanceof Boolean) {
//      return sp.getBoolean(key, (Boolean) defaultObject);
//    } else if (defaultObject instanceof Float) {
//      return sp.getFloat(key, (Float) defaultObject);
//    } else if (defaultObject instanceof Long) {
//      return sp.getLong(key, (Long) defaultObject);
//    }
//
//    return null;
//  }
//
//  /**
//   * 移除某个key值已经对应的值
//   */
//  public static void remove(Context context, String key) {
//    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//    Editor editor = sp.edit();
//    editor.remove(key);
//    SharedPreferencesCompat.apply(editor);
//  }
//
//  /**
//   * 清除所有数据
//   */
//  public static void clear(Context context) {
//    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//    Editor editor = sp.edit();
//    editor.clear();
//    SharedPreferencesCompat.apply(editor);
//  }
//
//  /**
//   * 查询某个key是否已经存在
//   */
//  public static boolean contains(Context context, String key) {
//    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//    return sp.contains(key);
//  }
//
//  /**
//   * 返回所有的键值对
//   */
//  public static Map<String, ?> getAll(Context context) {
//    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//    return sp.getAll();
//  }
//
//  /**
//   * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
//   *
//   * @author zhy
//   */
//  private static class SharedPreferencesCompat {
//
//    private static final Method sApplyMethod = findApplyMethod();
//
//    /**
//     * 反射查找apply的方法
//     */
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    private static Method findApplyMethod() {
//      try {
//        Class clz = Editor.class;
//        return clz.getMethod("apply");
//      } catch (NoSuchMethodException e) {
//      }
//
//      return null;
//    }
//
//    /**
//     * 如果找到则使用apply执行，否则使用commit
//     */
//    public static void apply(Editor editor) {
//      try {
//        if (sApplyMethod != null) {
//          sApplyMethod.invoke(editor);
//          return;
//        }
//      } catch (IllegalArgumentException e) {
//      } catch (IllegalAccessException e) {
//      } catch (InvocationTargetException e) {
//      }
//      editor.commit();
//    }
//  }
//
//  public static void saveOAuth(Context context, String key, Object oAuth_1) {
//    SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
//    // 创建字节输出流
//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    try {
//      // 创建对象输出流，并封装字节流
//      ObjectOutputStream oos = new ObjectOutputStream(baos);
//      // 将对象写入字节流
//      oos.writeObject(oAuth_1);
//      // 将字节流编码成base64的字符窜
//      String oAuth_Base64 = new String(Base64.encodeBase64(baos.toByteArray()));
//      Editor editor = preferences.edit();
//      editor.putString(key, oAuth_Base64);
//
//      editor.apply();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  public static Object getObject(Context context, String key) {
//    Object oAuth_1 = null;
//    SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
//    String productBase64 = preferences.getString(key, "");
//
//    // 读取字节
//    byte[] base64 = Base64.decodeBase64(productBase64.getBytes());
//
//    // 封装到字节流
//    ByteArrayInputStream bais = new ByteArrayInputStream(base64);
//    try {
//      // 再次封装
//      ObjectInputStream bis = new ObjectInputStream(bais);
//      try {
//        // 读取对象
//        oAuth_1 = (Object) bis.readObject();
//      } catch (ClassNotFoundException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//    } catch (StreamCorruptedException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    return oAuth_1;
//  }
//}