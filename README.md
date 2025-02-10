# 無shinsa
## 🛠️ Tools : <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=github&logoColor=Green"> <img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/>  <img alt="Java" src ="https://img.shields.io/badge/intellijidea-000000.svg?&style=for-the-badge&logo=intellijidea&logoColor=white"/>, <img src="https://img.shields.io/badge/AmazonAWS-FF0000?style=flat-square&logo=Adobe&logoColor=white">
## 🚩 Period : 2024/01/02 ~ 2024/02/10
## 👨‍💻 ERD <a=href>https://www.erdcloud.com/d/vHWtykYujZaDJdLpc</a=href>
## 👨‍💻 API
<a=href>https://identity.getpostman.com/handover/multifactor?user=41769599&handover_token=3a869f52-d810-4426-b93a-9e4ae97357cd</a=href>
## 👨‍💻 Role 
이해욱 : Order, Toss  
권진석 : Category, Brand 
김민경 : User, 통계 
황상익 : Image, Report, Product, ProductOptions, CartItem, Coupon, Review, Cateogry Refactoring, Brand Refactoring, AWS, Order 일부 Refactoring, 각 기능 동적 쿼리 작성 
## 👨‍💻 About Project 
- 쿠팡, 무신사 이커머스를 모티브로 無신사라는 쇼핑몰 이커머스 입니다. 회원가입과 백 오피스 기능이 갖춰져 있고, 회원별 등급에 따라 상품 적립 포인트가 적립이 되어 그에 맞는 할인 및 포인트 사용이 가능하게 할 수 있으며, 관리자와 점주는 판매 현황 및 자신의 매장의 매출 현황을 분석해, 보다 영업을 효율적으로 할 수 있는 기능이 구성되어 있으며, 소비자는 브랜드별 카테고리에 상품들이 구성되어 구매가 가능합니다. 또한 쿠폰 지급을 통해 소비자는 상품을 구매할 때 보다 할인되어 있는 상품을 통해 구매가 가능합니다. 상품 구매 후 후기 작성이 가능 하며, 상품 구매 직전 다른 사람의 후기가 궁금할 경우, 별점에 따라 조회가 가능합니다.
악성 리뷰나, 잘못된 상품이 있을 경우에는 신고기능을 통해, 해당 개시물 혹은 상품을 신고 할 수 있으며, 신고가 접수되면, Admin 측에서 적절한 조치가 이뤄질 수 있도록 했습니다. 
## MVP 1 
- User  
  - UserService
    - CRUD : 회원 가입, 로그인, 회원 조회, 회원 수정, 로그이웃 기능
  - UserController
    - UserService에 회원 가입, 로그인, 회원 조회, 회원 수정, 로그이웃기능 호출
- Brand
  - BrandService
    -  CRUD : 브랜드 생성, 수정, 조회, 브랜드별 점주 조회, 모든 브랜드 조회
  - BrandController
    - BrandService에 브랜드 생성, 수정, 조회, 브랜드별 점주 조회, 모든 브랜드 조회 기능 호출
- Category
  - CategoryService
    - CRUD : 카테고리 생성, 조회 , 수정 기능
  - CateogryController
    - CategoryService에 카테고리 생성, 조회 , 수정 기능 호출
- CartItem
  - CartItemService
    - CRUD : 장바구니 생성, 장바구니 전체 조회 (날짜 선택 조회), 장바구니 수정, 장바구니에서 구매 전 쿠폰이 있다면 쿠폰 적용
  - CartItemController
    - 장바구니 생성, 장바구니 전체 조회 (날짜 선택 조회), 장바구니 수정, 장바구니에서 구매 전 쿠폰이 있다면 쿠폰 적용 기능 호출
- Product
  - ProductService
    - CRUD : 상품 생성, 수정, 단일 상품 조회, 이름 내림차순 상품 조회, 상품 삭제
  - ProductController
    - 상품 생성, 수정, 단일 상품 조회, 이름 내림차순 상품 조회, 상품 삭제 기능 호출 
- Review
  - ReviewService
    - CRUD : 리뷰 생성, 리뷰 조회, 리뷰 수정, 리뷰 별점에 따라 조회 기능, 리뷰 삭제
  - ReviewController
    - 리뷰 생성, 리뷰 조회, 리뷰 수정, 리뷰 별점에 따라 조회 기능, 리뷰 삭제 호출
- Report
  - ReportService
    - CRUD : 신고 생성, 신고 상태 변경, 신고 조회, 모든 신고 조회, 신고 삭제
  - ReviewController
    - 신고 생성, 신고 상태 변경, 신고 조회, 모든 신고 조회, 신고 삭제 호출
- Coupon
  - CouponService
    - 쿠폰 생성, 쿠폰 생성시 분산락을 활용해, 동시성 제어를 설정했습니다.
      비관적 락, 낙관적 락을 사용하면 동시성에 문제는 해결되지만 성능 저하 가능성, 특히 트랜잭션이 많이질수록 DB에 락이 증가하여 성능이 떨어지거나 데드락 발생 가능성 증가. 이를 해력하기 위해 Redisson과 같은 In-Memory 데이터 저장소를 활용한 분산 락을 적용하면, 빠른 속도로 동시성을 제어하면서 성능 저하 없이 쿠폰 발급을 처리가 가능 하고  In-Memory 데이터 저장소를 활용한 분산 락을 적용하면, 빠른 속도로 동시성을 제어하면서 성능 저하 없이 쿠폰 발급을 처리가 가능합니다. 
  - CouponContoller
    - 쿠폰을 생성하는데 호출
- Static
  - StaticService
    - 현재 매출 상태를 확인, 통계 합계
  - StaticController
    - StaticService 호출

## 🧨 TroubleShooting 
- QeuryDsl TroubleShooting
  - 동적 쿼리 작성시, 처음에 Service에 조회 기능에 동적쿼리를 작성했습니다. 하지만 이렇게 작성할 경우 Service에 Repository에 책임이 전가되어
    책임이 불명확해 지는 현상이 발생해, CustomRepository라는 클래스를 따로 만들어 동적 쿼리 부분만 따로 작성했습니다
  - Entity로 Parsing을 받아 작성했는데 Entity로 받아 오게 되면 불필요한 컬럼까지 조회하게 되는 현상이 있고, 레이어 구조 유지를 하기에 어려움이 있어 Projections.constructor(dto.class)라는 값으로 받도록 변경했습니다.
  - Dto 필드 순서에 맞게 작성해야 하는데 순서에 맞게, 혹은 dto 값에 없을 경우 
```
com.querydsl.core.types.ExpressionException: No constructor found for class com.Soo_Shinsa.order.dto.OrderItemResponseDto with parameters: [class java.lang.Long, class java.lang.Integer, class java.lang.Long, class java.lang.String, class java.math.BigDecimal, class java.lang.Long]
```       
라는 오류가 발생해, dto 순서에 맞게 필드값을 하나하나 다시 확인해 수정 했습니다. 

- Coupon
  - CouponTest 도중 DeadLock 발생
   - 테스트에서는 동시에 4000개의 요청이 쿠폰 발급을 시도, 쿠폰 발급 개수는 10개로 제한, 여러 스레드가 동시에 같은 쿠폰을 가져와 발급하려고 하면 경합 상태 발생, issuedCount(발급된 개수)를 체크하고 증가시키는 로직이 안전하게 처리되지 않으면, 여러 스레드가 동시에 같은 쿠폰을 발급하려고 경쟁하여 데이터 정합성이 깨질 수 있음. 그래서 비관적 락을 사용해 트랜잭션이 종료될 때 까지 특정 데이터 변경을 차단.
  - 쿠폰 제고 문제
   - 쿠폰을 사용하면 수량은 감소되었지만, 다른 사용자가 동일한 쿠폰을 사용하려고 하면 `NOT_FOUND_COUPON` 예외가 발생함
     기존 코드에서 `findUnusedCouponByCouponId(requestDto.getCouponId())`로 조회할 때, 특정 사용자의 `CouponUser`만 조회 했음
     즉, 다른 사용자가 접근할 경우 새로운 쿠폰을 찾지 못함. 쿠폰의 재고(`maxCount`)가 남아 있어도, 새로운 `CouponUser`가 생성되지 않아서 발생한 문제.
     재고(`maxCount`)가 남아 있다면 새로운 `CouponUser`를 생성하도록 수정, 기존 `findUnusedCouponByCouponId()`에서 쿠폰이 없을 경우, couponRepository.findById() 를 통해 쿠폰을 조회하고 새로운 `CouponUser`를 생성 후 저장하여 다른 사용자도 사용할 수 있도록 변경
👍 최종 적용한 코드 
```
@Transactional
public ApplyCouponCartResponseDto applyCoupon(Long cartId, ApplyCouponCartRequestDto requestDto, User user) {
    CartItem cartItem = cartItemRepository.findByIdOrElseThrow(cartId);

    String lockKey = "coupon-lock:" + requestDto.getCouponId();
    RLock lock = redissonClient.getLock(lockKey);

    try {
        if (!lock.tryLock(5, 10, TimeUnit.SECONDS)) {
            throw new IllegalStateException("현재 쿠폰 사용 요청이 많아 잠시 후 다시 시도해주세요.");
        }

        Optional<CouponUser> optionalCouponUser = couponUserRepository.findUnusedCouponByCouponId(requestDto.getCouponId());

        Coupon coupon;
        CouponUser couponUser;

        if (optionalCouponUser.isPresent()) {
            couponUser = optionalCouponUser.get();
            coupon = couponUser.getCoupon();
        } else {
            coupon = couponRepository.findById(requestDto.getCouponId())
                    .orElseThrow(() -> new InvalidInputException(ErrorCode.NOT_FOUND_COUPON));

            if (coupon.getMaxCount() <= 0) {
                throw new InvalidInputException(ErrorCode.COUPON_OUT_OF_STOCK);
            }

            couponUser = CouponUser.builder()
                    .coupon(coupon)
                    .user(user)
                    .isUsed(false)
                    .usedAt(null)
                    .build();

            couponUserRepository.save(couponUser);
        }

        if (coupon.isExpired()) {
            throw new InvalidInputException(ErrorCode.EXPIRED_COUPON);
        }

        DiscountCouponCalculator discountCouponCalculator = new PercentageDiscountCalculator();
        BigDecimal discountPrice = discountCouponCalculator.calculateDiscountedPrice(cartItem.getProduct().getPrice(), coupon.getDiscountRate());

        cartItem.applyCoupon(coupon, discountPrice);
        cartItemRepository.save(cartItem);

        couponUser.markAsUsed();
        coupon.decreaseMaxCount(1);
        couponUserRepository.save(couponUser);

        List<ProductOption> productOptions = productOptionRepository.findProductOptionByProductId(cartItem.getProduct().getId());
        return ApplyCouponCartResponseDto.toDto(cartItem, productOptions);

    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IllegalStateException("쿠폰 적용 중 오류가 발생했습니다.", e);
    } finally {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
```

- Product
 - 상품 조회 시 파라미터 오류
```
2025-02-06T16:51:47.334+09:00 ERROR 17702 --- [Soo-Shinsa] [nio-8080-exec-5]
org.springframework.dao.InvalidDataAccessApiUsageException: At least 2 parameter(s) provided but only 1 parameter(s) present in query
java.lang.IllegalArgumentException: At least 2 parameter(s) provided but only 1 parameter(s) present in query
```
직접적인 원인은 찾지는 못했습니다만, 우회 방법으로 JPQL로 변경해 해당 오류를 해결했습니다. 

- Image
  - S3를 사용하기 때문에, S3만 구성한 Component로 만들어야 하나, 아니면, 이미지를 생성하고 저장할 수 있는 Image 서비스를 만들어야 하나라는 고민이 있었습니다. 하지만, S3를 직접 접근해서 이미지를 등록 하기 보다는 이를 따로 서비스화해, 유지 보수 하는데 적합하게 분리하는 것이 맞다고 판단이 들었습니다.
  - image entity에 Image 생성자에 originName.split 해서 배열 인덱스 바로 접근하는데 확장자 없으면 OutOfIndex 오류 발생
```
public enum FileType {
    JPG("jpg", "image/jpeg"),
    PNG("png", "image/png"),
    GIF("gif", "image/gif"),
    JPEG("jpeg", "image/jpeg");

    private final String extension;
    private final String mimeType;

    FileType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }


    /**
     * 확장자와 MIME 타입의 유효성을 검증
     * @param extension 파일 확장자
     * @param mimeType MIME 타입
     * @return 유효한 경우 true
     */
    public static boolean isValid(String extension, String mimeType) {
        return Arrays.stream(values())
                .anyMatch(fileType -> fileType.extension.equals(extension) && fileType.mimeType.equals(mimeType));
    }
}
```
확장자 명을 생성했고, 

```
public class FileUtils {

    /**
     * 파일 이름에서 확장자를 추출
     *
     * @param originName 원본 파일명
     * @return 확장자 (소문자)
     * @throws IllegalArgumentException
     */
    public static String extractFileExtension(String originName) {
        int dotIndex = originName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == originName.length() - 1) {
            throw new InvalidInputException(NO_EXTENSION);
        }
        return originName.substring(dotIndex + 1).toLowerCase();
    }

    /**
     * 파일 이름에서 확장자를 제외한 이름만 추출
     *
     * @param originName 원본 파일명
     * @return 확장자를 제외한 파일명
     */
    public static String extractFileName(String originName) {
        int dotIndex = originName.lastIndexOf(".");
        if (dotIndex == -1) {
            return originName; // 확장자가 없는 경우 전체 파일명 반환
        }
        return originName.substring(0, dotIndex); // 확장자 이전의 파일명 반환
    }
}
public class FileUtils {

    /**
     * 파일 이름에서 확장자를 추출
     *
     * @param originName 원본 파일명
     * @return 확장자 (소문자)
     * @throws IllegalArgumentException
     */
    public static String extractFileExtension(String originName) {
        int dotIndex = originName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == originName.length() - 1) {
            throw new InvalidInputException(NO_EXTENSION);
        }
        return originName.substring(dotIndex + 1).toLowerCase();
    }

    /**
     * 파일 이름에서 확장자를 제외한 이름만 추출
     *
     * @param originName 원본 파일명
     * @return 확장자를 제외한 파일명
     */
    public static String extractFileName(String originName) {
        int dotIndex = originName.lastIndexOf(".");
        if (dotIndex == -1) {
            return originName; // 확장자가 없는 경우 전체 파일명 반환
        }
        return originName.substring(0, dotIndex); // 확장자 이전의 파일명 반환
    }
}

```
파일 유틸을 통해 해당 확장자값이 아니면 오류 처리를 따로 했습니다. 
그후 
```
    public Image(String originName, TargetType targetType) {
        // 안전한 확장자 추출
        this.originName = FileUtils.extractFileName(Objects.requireNonNull(originName, "파일명은 필수입니다."));
        this.extension = FileUtils.extractFileExtension(originName);

        // 저장 파일명은 UUID로 생성
        this.name = UUID.randomUUID().toString();
        this.path = determinePath(targetType);
    }

```
엔티티 생성자 부분에 해당 FileUtil을 추가해 정해진 확장자가 아니면 이미지 생성시 오류가 발생하도록 진행했습니다. 

- Category
  - 계층형 데이터 생성 문제
  - 원인 : 자식 카테고리 생성시 부모의 id가 null로 생성 되는 문제 발생
  - 방안 : 부모의 정보를 가져오기 위해 responseDto를 다시 한번 호출해야 한다는 것을 알게 되었습니다
  - 해결 방법
    ```
    this.parent = (category.getParent() != null) ? new CategoryResponseDto(category.getParent()) : null;
     public static CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(category);
        }
    }
    ```
    자식 카테고리의 경우 재귀 함수를 사용해서 부모의 정보를 가져오게 함
- Docker
  - 지금 배포는 AWS에서 demon을 형성해 직접 배포하는 방식입니다.
    그래서 이를 Container화 하고, gitActions을 통해 자동화 배포를 구성하려고 했지만,
    ```
    Error starting ApplicationContext. To display the condition evaluation report re-run your application with       'debug' enabled.
    2025-02-07T22:49:41.810+09:00 ERROR 35517 --- [Soo-Shinsa] [           main] o.s.boot.SpringApplication               : Application run failed
    org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'cartItemController' defined in file [/Users/hwangsang-ik/IdeaProjects/sparta/soo-shinsa/build/classes/java/main/com/Soo_Shinsa/cartitem/controller/CartItemController.class]: Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'cartItemServiceImpl' defined in file [/Users/hwangsang-ik/IdeaProjects/sparta/soo-shinsa/build/classes/java/main/com/Soo_Shinsa/cartitem/service/CartItemServiceImpl.class]: Unsatisfied dependency expressed through constructor parameter 6: Error creating bean with name 'redissonClient' defined in class path resource [com/Soo_Shinsa/config/RedissonConfig.class]: Failed to instantiate [org.redisson.api.RedissonClient]: Factory method 'redissonClient' threw exception with message:
    ```
    이라는 오류가 발생했습니다. Redis port에서 문제가 생겼다는 것인데, 아직 Redis가 익숙하지 않아 이를 직접적으로 해결하지는 못했고, 추후 docker와 redis를 공부 한 후 적용해야 할 것 같습니다.

- Order
    - 일단 secretKey를 Base64.getEncoder() 인코딩해서 restTemplate로 orderId와 amount를https://api.tosspayments.com/v1/payments/confirm 에 전달해줘야 했는데 HTTP 요청을 보내고 응답을 받는 걸 해본적이 없었고 또한 토스페이먼츠 외부 api의 결제 흐름을 파악해야했다. 일단 HTTP요청을 보내고 응답을 어떻게 해야할지 몰라 많이 어려웠음. 구글링 끝에 HTTP요청과 토스페이먼츠의 개발자센터에서 api흐름을 공부하고 에서 api를 다운받고 결제를 완료하면 paymentKey 값이 나오는데 이것을 결제가 끝날 때 db에 저장하고 취소기능을 만들 때 paymentKey값을 이용해서 값을 찾은 후 취소 결제 기능까지 다행히 구현. 
```
@Transactional
public void approvePayment(String paymentKey, String orderId, Long amount, Model model) throws JsonProcessingException {
HttpHeaders headers = new HttpHeaders();
headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes()));
headers.setContentType(MediaType.APPLICATION_JSON);

Payment findPayment = paymentRepository.findByOrderId(orderId);
findPayment.update(TossPayStatus.DONE, paymentKey);
paymentRepository.save(findPayment);

PayloadRequestDto payload = new PayloadRequestDto(orderId, String.valueOf(amount));
HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payload), headers);
ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
        "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);
Orders findOrder = ordersRepository.findByOrderId(orderId);
findOrder.updateStatus(PAYMENTCOMPLETED);
ordersRepository.save(findOrder);
```
  - 주문시 쿠폰 적용후 할인 금액이 totalAmount에 들어가지 않는 상황
    ```
        // 총 결제 금액 계산
    public void calculateTotalPrice() {
        this.totalPrice = orderItems.stream()
                .map(item -> {
                    BigDecimal effectivePrice = (item.getDiscountPrice() != null && item.getDiscountPrice().compareTo(BigDecimal.ZERO) > 0)
                            ? item.getDiscountPrice()
                            : item.getProduct().getPrice();
                    return effectivePrice.multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    ```
    계산 로직에 DiscountPrice가 적용되지 않았었고, OrderItems에 discountPrice 필드가 적용되지 않았었다.
    그래서 카트 아이템에서 쿠폰을 적용하더라도, 총 금액에 할인 율이 적용이 되지 않았음. 그래서 해당 코드를 위와 같이 변경 후
    할인 금액이 적용 되었음. 

## 🫠 MVP2에서 해야 할 것들 
1. Token 형성시 RefreshToken과 BlakcList에 대한 해당 기능 들이 없다. 로그아웃 시 해당 토큰을 삭제하는 것이 아닌 따로 관리 해줄 필요가 있다. 
그래서 이를 추후에 추가해 관리 할 예정
2. 쿠폰을 보면 현재는 브랜드만 적용 할 수 있도록 되어 있다. 상품에도 적용 할 수 있도록 상품과 쿠폰을 정규화를 진행하고, 적용할 예정이다.
3. 카테고리에 대한 depth가 너무 얕다. 그래서 depth를 추가적으로 적용할 예정
4. 브랜드에 대한 카테고리 추가 생성필요
5. docker 생성과 git actions을 통한 자동화 배포 진행 예정
