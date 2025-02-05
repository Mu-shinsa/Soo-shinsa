package com.Soo_Shinsa.order;

import com.Soo_Shinsa.cartitem.CartItemRepository;
import com.Soo_Shinsa.cartitem.model.CartItem;
import com.Soo_Shinsa.constant.OrdersStatus;
import com.Soo_Shinsa.constant.ProductStatus;
import com.Soo_Shinsa.exception.ErrorCode;
import com.Soo_Shinsa.exception.InternalServerException;
import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.order.dto.OrderDateRequestDto;
import com.Soo_Shinsa.order.dto.OrdersResponseDto;
import com.Soo_Shinsa.order.model.OrderItem;
import com.Soo_Shinsa.order.model.Orders;
import com.Soo_Shinsa.product.ProductRepository;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.user.UserRepository;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.EntityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_CART;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;


    @Override
    public OrdersResponseDto getOrderById(Long orderId, User user) {
        User findUser = userRepository.findByIdOrElseThrow(user.getUserId());

        Orders findOrder = ordersRepository.findByIdOrElseThrow(orderId);

        EntityValidator.validateAndOrders(findOrder, findUser.getUserId());


        return OrdersResponseDto.toDto(findOrder);
    }

    @Override
    public Page<OrdersResponseDto> getAllByUserId(User user, OrderDateRequestDto dateRequestDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ordersRepository.getAllByUserId(user, dateRequestDto, pageable);
    }


    //    단일 상품 구매
//    상품을 찾아와서 주문번호를 생성 후 주문을 만들고 거기에 주문아이템에 물건을 담음
    @Transactional
    @Override
    public OrdersResponseDto createSingleProductOrder(User user, Long productId, Integer quantity) {

        Product product = productRepository.findByIdOrElseThrow(productId);
        if (product.getProductStatus().equals(ProductStatus.SOLD_OUT) || product.getProductStatus().equals(ProductStatus.UNAVAILABLE)) {
            throw new InternalServerException(ErrorCode.CAN_NOT_USE_PRODUCT);
        }

        Orders order = new Orders(product.getPrice().multiply(BigDecimal.valueOf(quantity)), OrdersStatus.BEFOREPAYMENT, user);


        OrderItem orderItem = new OrderItem(quantity, order, product);

        order.addOrderItem(orderItem);

        ordersRepository.save(order);

        return OrdersResponseDto.toDto(order);
    }

    @Transactional
    @Override
    public OrdersResponseDto createOrderFromCart(User user) {

        List<CartItem> cartItems = cartItemRepository.findByUserUserId(user.getUserId());
        if (cartItems.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_CART);
        }
        Orders order = new Orders(OrdersStatus.BEFOREPAYMENT, user);


        // CartItem 데이터를 기반으로 OrderItem 생성 및 추가
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProductOption().getProduct();
            Integer quantity = cartItem.getQuantity();


            OrderItem orderItem = new OrderItem(quantity, order, product);
            order.addOrderItem(orderItem);
        }

        ordersRepository.save(order);

        // 카트 비우기
        cartItemRepository.deleteAll(cartItems);

        return OrdersResponseDto.toDto(order);
    }

    @Transactional
    @Override
    public OrdersResponseDto createOrder(User user) {

        Orders order = new Orders(BigDecimal.ZERO, OrdersStatus.BEFOREPAYMENT, user);
        Orders savedOrder = ordersRepository.save(order);


        return OrdersResponseDto.toDto(savedOrder);
    }

    @Transactional
    @Override
    public OrdersResponseDto updateOrder(User user, Long orderId, OrdersStatus status) {

        User findUser = userRepository.findByIdOrElseThrow(user.getUserId());
        Orders findOrder = ordersRepository.findByIdOrElseThrow(orderId);

        EntityValidator.validateAndOrders(findOrder, findUser.getUserId());
        findOrder.updateStatus(status);
        Orders savedOrder = ordersRepository.save(findOrder);
        return OrdersResponseDto.toDto(savedOrder);
    }

}
