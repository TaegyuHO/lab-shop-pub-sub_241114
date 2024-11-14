package labshoppubsub.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import labshoppubsub.OrderApplication;
import labshoppubsub.domain.OrderPlaced;
import lombok.Data;

@Entity
@Table(name = "Order_table")
@Data
//<<< DDD / Aggregate Root
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productId;

    private Integer qty;

    private String customerId;

    private Double amount;

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        labshoppubsub.external.DecreaseStockCommand decreaseStockCommand = new labshoppubsub.external.DecreaseStockCommand();
        // mappings goes here
        OrderApplication.applicationContext
            .getBean(labshoppubsub.external.InventoryService.class)
            .decreaseStock(/* get???(), */decreaseStockCommand);

        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();
    }

    @PrePersist
    public void onPrePersist() {
        // Get request from Order
        //labshoppubsub.external.Order order =
        //    Application.applicationContext.getBean(labshoppubsub.external.OrderService.class)
        //    .getOrder(/** mapping value needed */);

    }

    public static OrderRepository repository() {
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(
            OrderRepository.class
        );
        return orderRepository;
    }
}
//>>> DDD / Aggregate Root
