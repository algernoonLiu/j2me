package com.algernoon.enums;

public class Test {
    public static void main(String[] args) {
//	testColorEnum(ColorEnum.RED);
//	testOrderStateEnum();
	testGenderEnum();
//	testSeasonEnum();
    }
    
    public static void testColorEnum(ColorEnum color){
	//枚举是一种类型，用于定义变量，以限制变量的赋值；赋值时通过“枚举名.值”取得枚举中的值
        switch (color) {
            case RED:
                System.out.println("color is RED");
                break;
            case GREEN:
                System.out.println("color is GREEN");
                break;
            case YELLOW:
                System.out.println("color is YELLOW");
                break;
        }
        
        //遍历枚举
        System.out.println("遍历ColorEnum枚举中的值");
        for(ColorEnum col : ColorEnum.values()){
            System.out.println(col);
        }
        
        //获取枚举的个数
        ColorEnum[] values = ColorEnum.values(); // 枚举数组
        System.out.println("ColorEnum枚举中的值有:"+values.length+"个");
        
        //获取枚举的索引位置，默认从0开始
        System.out.println("索引位置："+ColorEnum.RED.ordinal());//0
        System.out.println("索引位置："+ColorEnum.GREEN.ordinal());//1
        System.out.println("索引位置："+ColorEnum.YELLOW.ordinal());//2
        
        //枚举默认实现了java.lang.Comparable接口
        /**
         * public final int compareTo(E o) {
         *    Enum other = (Enum)o;
         *    Enum self = this;
         *    if (self.getClass() != other.getClass() && // optimization
         *    	self.getDeclaringClass() != other.getDeclaringClass())
         *    	throw new ClassCastException();	
         *    return self.ordinal - other.ordinal;
         * }
         */
        System.out.println("类型比较："+ColorEnum.GREEN.compareTo(ColorEnum.RED));//1
        System.out.println("类型比较："+ColorEnum.YELLOW.compareTo(ColorEnum.RED));//2
        System.out.println("类型比较："+ColorEnum.RED.compareTo(ColorEnum.GREEN));//-1
        System.out.println("类型比较："+ColorEnum.RED.compareTo(ColorEnum.RED));//0
    }
    
    public static void testOrderStateEnum(){
	 for(OrderStateEnum order : OrderStateEnum.values()){
	     System.out.println(order);
	     System.out.println(order.getName());
	 }
    }
    
    public static void testGenderEnum(){
	 for(GenderEnum gender : GenderEnum.values()){
	     System.out.println("迭代得到的code值："+gender.code);
	     System.out.println("迭代得到的name值："+gender.name());
	 }

	 if(GenderEnum.MAN.getCode().equals("0")){
	     System.out.println("true");
	     System.out.println(GenderEnum.getByCode("0").getSex());
	 }
	        
	 System.out.println(GenderEnum.nameOf("man"));
    }
    
    public static void testSeasonEnum(){
	System.out.println("季节为：" + SeasonEnum.getSeason());
    }
}
