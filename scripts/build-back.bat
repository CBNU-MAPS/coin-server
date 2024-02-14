:: 현재 docker process 종료 및 삭제
FOR /F "tokens=*" %%i IN ('docker ps -aq') DO docker rm -f %%i

:: gradlew 권한 설정 및 빌드
chmod +x ./gradlew
./gradlew build -x test

:: docker image 빌드 및 실행
docker build --no-cache -t coin-app .
docker compose -f ./docker-compose.yml -p coin-server up -d