package com.wlsup.yygh.enums;

public enum AuthStatusEnum {

    NO_AUTH(0, "未认证"),
    AUTH_RUN(1, "认证中"),
    AUTH_SUCCESS(2, "认证成功"),
    AUTH_FAIL(-1, "认证失败"),
    ;

    private Integer status;
    private String name;

    AuthStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }
    //这个方法是将status码转为对应的名称
    public static String getStatusNameByStatus(Integer status) {
        //values()是枚举类的方法，可以将所有枚举对象放到一个对象数组
        AuthStatusEnum arrObj[] = AuthStatusEnum.values();
        //遍历每一个枚举对象，如果值对应上就返回名称
        //“intValue()方法用于返回此Integer对象表示的值,该值转换为int类型(通过强制转换)。
        //防止他们进行比较的是Integer引用的对象内存地址
        for (AuthStatusEnum obj : arrObj) {
            if (status.intValue() == obj.getStatus().intValue()) {
                return obj.getName();
            }
        }
        return "";
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
