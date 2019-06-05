FROM java:8
ENV ARTIFACTID service-user-center-oauth
ENV ARTIFACTVERSION 0.0.1-SNAPSHOT
ENV HOME_PATH /home
WORKDIR $HOME_PATH
ADD /$ARTIFACTID-$ARTIFACTVERSION.jar $HOME_PATH/$ARTIFACTID.jar
ENTRYPOINT ["java", "-jar", "service-user-center-oauth.jar"]
#ENTRYPOINT ["java", "-jar", "service-user-center-oauth.jar", "--server.port=7001", "--spring.profiles.active=prepub"]