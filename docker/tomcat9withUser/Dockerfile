FROM tomcat:9.0

ADD tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
ADD context.xml /tmp/context.xml

RUN mv /usr/local/tomcat/webapps /usr/local/tomcat/webapps2
RUN mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps
RUN cp /tmp/context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml

EXPOSE 8080

CMD ["catalina.sh", "run"]