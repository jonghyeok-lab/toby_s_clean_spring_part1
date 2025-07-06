#### 개발환경 구성
- JDK = JRE + javac + tool(디버깅, 보안..)
- LTS = Long Term Support
- JDK 버전을 관리하는 도구를 이용해서 JDK 설치
- SDKMAN: Java와 다양한 도구의 버전 관리
    - 터미널 사용 초기화: source "$HOME/.sdkman/bin/sdkman-init.sh"
    - 설치 가능한 JDK 목록 확인 sdk list java
    - 설치된 JDK 목록 확인 sdk list java | grep installed
    - 설치된 JDK 선택 : sdk use java 21.0.6-libraca
    - 현재 선택된 JDK 버전 정보 저장: sdk env init
        - 선택한 JDK 버전 정보는 .sdkmanrc 파일에 저장된다. 해당 파일을 Git이 추적할 수 있게 하면 개발팀과 버전을 공유 가능하다.
    - 기존에 저장된 버전의 JDK 활성화 : sdk env
    - 저장된 JDK 버전이 설치되어 있지 않을 때, 설치하기: sdk env install
- Docker Desktop 설치 시, 기업 인원이 1000명?이 넘어가면 라이선스 비용이 발생하니, 회사에 물어보고 설치해야 한다.
    - 대체 도구 Rancher Desktop / Podman Desktop / OrbStack

#### DDD(Domain Driven Design): 도메인 주도 설계(not 개발)
- 도메인의 복잡성이 주는 문제를 해결하기 위한 접근 방법
- 도메인 모델을 개발 과정의 중심에 두는 방법(not 패턴)
- 현업 전문가 + 이해 관계자 + 개발자 모두 참여
- **도메인 모델이 설계와 코드**까지 이어져야 한다.(모델 주도 설계)

#### 엔티티(Entity)
- 도메인 모델을 만들 때 사용하는 패턴
- 도메인에 있는 대상이나 개념
- 고유한 식별자를 가지고 개별적으로 구분
- 생명주기를 가지며, 시간 흐름에 따라 상태가 변경된다.

#### 도메인 모델 패턴
- 도메인/비즈니스 로직을 구성하는 아키텍처 패턴의 한 종류