package com.company.project.util;



import java.lang.reflect.Field;

/**
 * Created by syuutou on 2018/8/31.
 */
public class CommonUtils {
    public static final int CURRENT_PAGE = 1;
    public static final int PAGE_SIZE = 10;

    /**
     * 判断任意属性值为null
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static boolean isAllFieldNull(Object obj) throws IllegalAccessException {
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性
            f.setAccessible(true); // 设置属性是可以访问的(私有的也可以访问)
            Object val = null;// 得到此属性的值
            //此处存在异常，直接进行处理
            val = f.get(obj);
            if (val != null) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                flag = false;
                break;
            }

        }
        return flag;
    }

    /**
     * 分页dto基本属性赋值
     *
     * @param obj
     */
    public static <T> void formatPagingInputDto(T obj) throws IllegalAccessException {

        Class<?> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        //用户接受当前页以及每页显示行数，计算起始索引
        int currentPage = 0;
        int pageSize = 0;

        //首先设置currentPage以及pageSIze的值
        for (Field e : declaredFields
        ) {
            e.setAccessible(true);
            switch (e.getName()) {
                case "currentPage": {
                    if (e.get(obj) == null || (e.get(obj)).equals(0)) {
                        //重新设置currentPage属性的默认值
                        e.set(obj, CURRENT_PAGE);

                        currentPage = CURRENT_PAGE;
                    } else {
                        currentPage = (int) e.get(obj);
                    }
                }
                break;
                case "pageSize": {
                    if (e.get(obj) == null || e.get(obj).equals(0)) {
                        //重新设置pageSize属性的默认值
                        e.set(obj, PAGE_SIZE);

                        pageSize = PAGE_SIZE;
                    } else {
                        pageSize = (int) e.get(obj);
                    }
                }
                break;
                default:
            }
        }

        for (Field e : declaredFields
        ) {
            e.setAccessible(true);
            switch (e.getName()) {
                case "start": {
                        //java.lang.IllegalArgumentException,需要强制转型
                    //重新设置start属性的默认值
                        e.set(obj, (long) (currentPage - 1) * pageSize);
                }
                break;
                default:
            }
        }
    }

    public static void main(String[] args) throws IllegalAccessException {
//        PagingInputDto<Object> out = new PagingInputDto<>();
//        out.setCurrentPage(3);
//        out.setPageSize(5);
//        System.err.println(out);
//
//        formatPagingInputDto(out);
//
//        System.err.println(out);

    }
}
