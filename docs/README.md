# PRODUCT Service
> Spring Cloud를 활용한 Product API를 담당하는 서버입니다.<br> 
> 위 서버는 상품의 경매 상태 변경, 관심 상품 등록 시 회원의 유효성 검증을 위해 Open Feign 통신을 합니다.<br>
> 위 서버는 이미지 업로드를 위해 스토리지 서비스인 AWS S3를 활용합니다.
<br>

## 목차
- [Dependency](#-dependency) <br>
- [Product Service 기능](#-product-service-기능) <br>
- [Service Architecture](#-Service-Architecture) <br>
- [Product Service 흐름](#-product-service-흐름) <br>
<br>

## 🛠️ Dependency
|       기능       | 기술 스택                                                                       |
|:--------------:|:----------------------------------------------------------------------------|
|  Spring Boot   | - Spring Framework 2.7.15<br> - Java 17 <br> - Gradle 8.0 <br> - Spring Web |
|  Spring Cloud  | - Eureka <br> - Config <br> - Gateway <br> - OpenFeign                      |
|    Database    | - Mysql 8.33 <br>                                                           |
|      ORM       | - JPA <br>- Native Query|                                                   
|  Monitoring   | - Actuator| <br> 
|    Storage     | - AWS S3                                                                    |

<br>


## 📝 Product Service 기능

|   기능   | 내용                                                                                                 |
|:------:|:---------------------------------------------------------------------------------------------------|
|  상품 등록   | 사용자로부터 정보를 입력받아 신원을 확인 후, 관리자인 경우 **상품 정보를 등록**한다.<br> 이 때, Image는 S3를 활용하여 업로드하며 상품에 대한 이미지는 최대 10장 업로드가 가능하다. 상품의 이미지는 URL값으로 DB에 저장된다.      |
| 상품 조회 | 전체 상품 조회, 상품ID로 상품 조회, 상품명으로 상품 조회, 아티스트명으로 **상품을 조회**한다.  |
|  상품 정보 수정  | 사용자로부터 신원을 확인 후 관리자인 경우 **상품의 정보를 수정**한다. 이 때, Image는 S3를 활용하여 수정하여 업로드한다.|
|  카테고리별 상품조회  | QueryDSL을 사용하여 매체별 상품조회, 매체와 키워드를 통해 **상품을 조회**한다.|
|  상품 삭제  | 사용자로부터 신원을 확인 후 관리자인 경우 상품ID로 **상품을 삭제**한다.이 때, Image는 S3를 활용하여 해당 상품의 이미지를 삭제한다.|
|  관심상품 등록  | 사용자로부터 정보를 입력받아 신원과 상품을 확인 후, **관심상품을 등록**한다. 이 때, 관심상품은 한 사용자 당 10개까지만 담길 수 있으며 하나의 상품은 하나의 관심만 가질 수 있다.|
|  관심상품 조회  | 사용자의 ID를 통해 해당 사용자의 **관심상품을 조회**한다.|
|  관심상품 삭제  | 사용자의 신원과 상품에 대한 유효성 검증 후 **관심상품을 삭제**한다. |
|    Feign 통신  | 내부 기능을 수행하기 위해 사용자의 정보를 받아오고, 상품의 경매상태 정보를 다른 서비스에 제공하기 위해서 **OpenFeign 통신을 사용**한다.        |
<br>

## 🚀 Service Architecture
<img width="7252" alt="서비스아키텍처" src="https://github.com/wooriFisa-Final-Project-F4/product-service/assets/75323120/014c300a-c57f-4a94-9a07-08153ea4ce1e">

## 🚀 Product Service 흐름
1. Front(Client)에서 받은 요청을 Spring Cloud Gateway에서 수신하여 Auth-Service & User-Service를 통해 인증/인가를 확인합니다. 
2. 인증된 사용자에 한해서 유레카 서비스에 등록된 Product-Service 정보를 가져와 API를 Product-Service에 라우팅시킵니다. 
3. Product-Service 내부에서는 각 API를 활용해 사용자에게 필요한 요청을 처리하고 응답합니다.
4. 다른 서비스와는 Spring Cloud의 OpenFeign 기술을 활용하여 통신합니다.

<br>
