package jpabook.jpashop.repository.order.query;

import lombok.Data;

/**
 * packageName : jpabook.jpashop.repository.order.query
 * fileName : OrderItemQueryDto
 * author : joguk
 * date : 2021/12/01
 * description :
 * <pre></pre>
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2021/12/01 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Data
public class OrderItemQueryDto {
    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
