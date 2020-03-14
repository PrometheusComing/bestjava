package com.best.java.enumtest;

/**
 * 枚举测试
 *
 * 枚举的声明格式是：{ClassModifier} enum Identifier [Superinterfaces] EnumBody，ClassModifier是修饰符，Identifier是枚举的名称可以类比为类名，枚举类型可以实现接口。
 * 枚举类型不能使用abstract或者final修饰，否则会产生编译错误。
 * 枚举类型的直接超类是java.lang.Enum。
 * 枚举类型除了枚举常量定义之外没有其他实例，也就是枚举类型不能实例化。
 * 枚举类型禁用反射操作进行实例化(这个特性就是Effetive Java中推荐使用枚举实现单例的原因)。
 *
 * Enum实现了Serializable接口，但是readObject和readObjectNoData直接抛出了InvalidObjectException异常，注释说到是"防止默认的反序列化"，这一点有点不明不白，既然禁用反序列化为何要实现Serializable接口，这里可能考虑到是否实现Serializable接口应该交给开发者决定。
 * Enum禁用克隆。
 *
 * 枚举不会被反射且使用fastjson变string再序列化回对象后,依旧是那个对象.所以枚举作为单例很安全.
 */
public enum  Closes {
    SMALL(0,"小号"),
    MEDIUM(1,"中号"),
    LARGE(2,"大号"),
    MOSTLARGE(3,"超大号");
    private int size;

    private String desc;

    Closes(int size,String desc) {
        this.size = size;
        this.desc = desc;
    }
    public int getSize() {
        return size;
    }

    public String getDesc() {
        return desc;
    }

    public static void main(String[] args) {
        System.out.println(Closes.SMALL.getSize());
        System.out.println(Closes.SMALL.getDesc());
    }
}
