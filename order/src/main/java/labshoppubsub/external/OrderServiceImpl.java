package labshoppubsub.external;

import org.springframework.stereotype.Service;

//<<< Resilency / Fallback
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Fallback
     */
    public Order getOrder(Long id) {
        Order order = new Order();
        return order;
    }
}
//>>> Resilency / Fallback
