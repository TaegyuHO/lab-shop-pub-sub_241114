package labshoppubsub.external;

import java.util.Date;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//<<< Resilency / Circuit Breaker
//<<< Resilency / Fallback
@FeignClient(
    name = "order",
    url = "${api.url.order}",
    fallback = OrderServiceImpl.class
)
//>>> Resilency / Fallback
public interface OrderService {
    @RequestMapping(method = RequestMethod.POST, path = "/orders")
    public void order(@RequestBody Order order);
}
//>>> Resilency / Circuit Breaker
