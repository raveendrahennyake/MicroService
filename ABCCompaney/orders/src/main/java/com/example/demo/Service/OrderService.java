package com.example.demo.Service;
import com.example.demo.D.T.O.InventoryDTO;
import com.example.demo.D.T.O.OrderDTO;
import com.example.demo.D.T.O.ProductDTO;
import com.example.demo.Model.Orders;
import com.example.demo.Repository.OrderRepo;
import com.example.demo.common.ErrorResponce;
import com.example.demo.common.OrderResponce;
import com.example.demo.common.SucessOrderResponce;
import com.example.demo.config.WebClientConfig;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
@Service
public class OrderService {
    @Autowired
    private final OrderRepo orderRepo;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    @Qualifier("inventoryWebClient")
    private final WebClient inventoryWebClient;
    @Autowired
    @Qualifier("ProductWebClient")
    private final WebClient ProductWebClient;

    public OrderService(WebClient inventoryWebClient,WebClient ProductWebClient, OrderRepo orderRepo,ModelMapper modelMapper) {
        this.ProductWebClient=ProductWebClient;
        this.inventoryWebClient=inventoryWebClient;
        this.orderRepo=orderRepo;
        this.modelMapper=modelMapper;
    }
    public List<OrderDTO> getAllOrders() {
        List<Orders>orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }
    public OrderResponce saveOrder(OrderDTO OrderDTO) {
        Integer itemId=OrderDTO.getItemId();
        try {
            InventoryDTO inventoryResponce=inventoryWebClient.get()
                    .uri(uriBuilder ->uriBuilder.path("/item/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();
            assert inventoryResponce != null;

            Integer productId=inventoryResponce.getProductId();
            ProductDTO productResponce=ProductWebClient.get()
                    .uri(uriBuilder ->uriBuilder.path("/product/{productId}").build(productId))
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();
            assert productResponce!=null;

            if (inventoryResponce.getQuantity()>0){
                if(productResponce.getForSale()==1){
                    orderRepo.save(modelMapper.map(OrderDTO, Orders.class));
                    return new SucessOrderResponce(OrderDTO) ;
                }else {
                    return new ErrorResponce("this item not for sale");
                }

            }else{
                return new ErrorResponce("this item not availabel ");
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    public OrderDTO updateOrder(OrderDTO OrderDTO) {
        orderRepo.save(modelMapper.map(OrderDTO, Orders.class));
        return OrderDTO;
    }
    public String deleteOrder(Integer orderId) {
        orderRepo.deleteById(orderId);
        return "Order deleted";
    }
    public OrderDTO getOrderById(Integer orderId) {
        Orders order = orderRepo.getOrderById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }
}
