시작하기
Jersey와 Grizzly를 사용하여 간단한 JAX-RS 응용 프로그램을 만들어 가고 있습니다.

Glassfish 등의 JavaEE 애플리케이션 서버 에 포함 된 Jersey와 Grizzly이지만, 개별 라이브러리로도 쉽게 사용할 수 있습니다.

이 라이브러리를 사용하여 main 메소드 에서 시작하는 "무겁지 않은"응용 프로그램을 만들면서 JAX-RS 에 접해 가고 있습니다.

JAX-RS 는
JAX-RS 1.1 ( Java API for RESTful Web Services)로 JSR-311에 책정 된 사양에서 JavaEE6에 포함되었습니다.

JAX-RS 2.0은 JSR-339에 책정 된 사양으로, 이곳은 JavaEE7에 포함되어 있습니다. 2.0 사양에서는 Client API 와 필터 / 인터 셉터의 사양이 추가되어 있습니다.

JAX-RS 를 사용하여 POJO (Plain Old Java Object) 기반의 HTTP 관련 주석 을 사용하여 RESTful Web Services를 쉽게 구현할 수 있습니다. 그리고 사양 자체도 매우 간단하고 사용하기 쉬운 것으로되어 있습니다.

Jersey는
Jersey JAX-RX의 참조 구현입니다.

https://jersey.java.net/

Jersey 2.0에서 HK2 (JSR-330 DI 구현)가 내장되어 있고, Jersey MVC (JavaEE8에서 도입되는 MVC 1.0의 기반이되는)가 있고, 이것만으로도 간단한 응용 프로그램을 만들 수 있습니다.

Grizzly는
Grizzly는 Glassfish 의 HTTP 처리 엔진 (이전 Coyote 가 사용되었다)로 사용되고 있습니다.

https://grizzly.java.net/

NIO를 사용하여 C10K 문제에 대응 한 고성능 범용적인 네트워크 서버 엔진입니다.
