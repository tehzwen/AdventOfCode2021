FROM java:latest

COPY . /usr/app

WORKDIR /usr/app/src
RUN javac com/test/Program.java
ENTRYPOINT [ "java", "com.test.Program" ]
# ENTRYPOINT [ "sleep", "5000" ]
