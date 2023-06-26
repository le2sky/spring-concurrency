## memo

### Optimistic vs Pessimistic

- 동시에 수정을 하는일이 빈번하게 일어나는 지를 기준으로 결정(충돌이 빈번하게 일어나는가?)
  - 동시 수정하는 상황이 빈번하다면 비관적 락을 사용
  - 적다면, 낙관적 락을 사용(재시도 비용 때문)

### Lettuce vs Redisson
- `Lettuce` :
  - 구현이 간단함
  - spring-data-redis를 이용하면 lettuce가 기본
  - spin-lock 방식
- `Redisson`
  - 락 획득 재시도를 기본 제공
  - pub-sub 방식
  - 별도의 라이브러리를 추가해야 한다는 부담감
  - lock을 라이브러리 차원에서 제공하기 때문에 사용법 공부 필요
- 실무에서는?
  - 재시도가 필요하지 않으면 Lettuce,
  - 재시도가 필요하다면 Redisson 사용

### MySQL vs Redis

- `MySQL` :
  - 이미 사용하고 있다면, 별도 비용 없음
  - 어느정도의 트래픽까지는 문제없이 활용 가능
  - Redis 보다는 성능이 잘안나옴
- `Redis` :
  - 별도의 비용이 발생
  - 성능 굳