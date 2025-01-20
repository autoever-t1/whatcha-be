# 베이스 이미지로 Amazon Corretto 17 사용
FROM amazoncorretto:17

# 컨테이너 내부에서 사용할 포트를 8080으로 공개
EXPOSE 8080

# 작업 디렉토리 생성
WORKDIR /app

# Firebase 설정 파일 복사
COPY src/main/resources/worryboxFirebaseKey.json /app/worryboxFirebaseKey.json

# JAR 파일 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 파일 권한 설정
RUN chmod 644 /app/worryboxFirebaseKey.json

# 컨테이너가 시작될 때 실행할 명령어를 지정
ENTRYPOINT ["java","-jar","app.jar"]