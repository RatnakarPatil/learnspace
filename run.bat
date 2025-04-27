@echo off

if "%1"=="build_start" (
    echo Starting all services...

    :: Start Discovery Service
    echo Starting Discovery Service...
    cd service-discovery
    start cmd /k "mvn clean install && mvn spring-boot:run"
    timeout /t 25
    cd ..

    :: Start API Gateway
    echo Starting API Gateway...
    cd api-gateway
    start cmd /k "mvn clean install && mvn spring-boot:run"
    timeout /t 20
    cd ..

    :: Start Mentor Service
    echo Starting Mentor Service...
    cd mentor-service
    start cmd /k "mvn clean install && mvn spring-boot:run"
    timeout /t 20
    cd ..

    :: Start Learner Service
    echo Starting Learner Service...
    cd learner-service
    start cmd /k "mvn clean install && mvn spring-boot:run"
    timeout /t 20
    cd ..

    echo All services started successfully!
    exit
)

if "%1"=="stop" (
    echo Stopping all services...

    taskkill /F /IM java.exe /T

    echo All services stopped!
    exit
)

:: If the argument is missing or incorrect
echo Invalid command! Use:
echo run.bat build_start  - to build & start all services
echo run.bat stop         - to stop all services
exit
