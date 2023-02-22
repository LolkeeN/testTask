How to build
1. cd $ProjectRoot
2. mvn clean install
3. docker build -f Docker/Dockerfile . -t vasyl-test-project
4. cd Docker
5. docker-compose up -d