FROM openjdk:8-jdk-alpine

# Metadata
LABEL module.name="spring-boot-demo"	


COPY *.jar app.jar

# logs folder
RUN mkdir -p /var/log && \
	mkdir ./target


# create user
RUN addgroup -S user -g 433 && adduser -u 431 -S -g user -h /usr/local -s /sbin/nologin user

RUN  chown -R user:user /var/log && \
	 chmod -R 777 /var/log && \
	 chown user:user app.jar && \
	 chown -R user:user ./target && \    
     chmod -R 777 ./target
	 
	 
VOLUME ["/var/log"]


USER user

EXPOSE 8080


ENV JAVA_OPTS="$JAVA_OPTS -Xms1G -Xmx3G" \
	ONESAIT_SERVERNAME=development.onesaitplatform.com \
	PORT=8080 \
	CONTEXT_PATH=/spring-boot-demo \
	MONGO_SERVER=mongodb-demo \
	MONGO_DB=spring_boot_demo
	
	

ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker -jar /app.jar

