:: 현재 docker process 종료 및 삭제
FOR /F "tokens=*" %%i IN ('docker ps -aq') DO docker rm -f %%i

:: 기존 docker image 삭제
docker rmi minseokoh/coin:latest

:: docker image 빌드 및 실행
docker compose -f ./docker-compose.yml -p coin-server up -d
