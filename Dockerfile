FROM openjdk:21-jdk-slim

# 빌드 컨텍스트에서 JAR 파일을 컨테이너로 복사
# ARG는 docker build 시 --build-arg 옵션으로 값을 전달받을 수 있습니다.
# 여기서는 Gradle 빌드 후 생성되는 JAR 파일의 일반적인 경로를 사용합니다.
ARG JAR_FILE=board/build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 애플리케이션 실행 포트 노출
EXPOSE 8080
EXPOSE 8081

# prod 프로필 사용하도록 설정
ENV SPRING_PROFILES_ACTIVE=prod

# 컨테이너 실행 시 실행될 명령어
# java -jar app.jar 뒤에 Spring Boot 옵션을 추가할 수 있습니다.
ENTRYPOINT ["java", "-jar", "app.jar"]