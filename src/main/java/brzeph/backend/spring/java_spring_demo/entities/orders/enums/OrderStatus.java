package brzeph.backend.spring.java_spring_demo.entities.orders.enums;

public enum OrderStatus {

    WAITING_PAYMENT(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELLED(5);

    private int code;

    OrderStatus(int code){
        this.code = code;
    }

    public static OrderStatus getValueOf(int code){
        for(OrderStatus orderStatus : OrderStatus.values()){
            if(orderStatus.getCode() == code){
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("No OrderStatus found with code " + code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
