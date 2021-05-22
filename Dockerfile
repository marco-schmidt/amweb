FROM tomcat:10-jdk11

ARG BUILD_DATE
ARG BUILD_REVISION

LABEL org.opencontainers.image.authors="mschmidtgit@protonmail.com"
LABEL org.opencontainers.image.vendor="Marco Schmidt"
LABEL org.opencontainers.image.title="marcoschmidt/amweb"
LABEL org.opencontainers.image.description="amweb: Asset Manager web application"
LABEL org.opencontainers.image.url="https://hub.docker.com/r/marcoschmidt/amweb"
LABEL org.opencontainers.image.source="https://github.com/marco-schmidt/amweb/blob/master/Dockerfile"
LABEL org.opencontainers.image.licenses="Apache-2.0"

# Requirement: must have run
#   ./gradlew war
# so that a single file build/libs/amweb-*.war exists.

# labels using arguments, changing with every build
LABEL org.opencontainers.image.created=$BUILD_DATE
LABEL org.opencontainers.image.revision=$BUILD_REVISION

RUN rm -rf /usr/local/tomcat/webapps/ROOT \
  rm -rf /usr/local/tomcat/webapps/documentation \
  rm -rf /usr/local/tomcat/webapps/examples
# copy war file into image
COPY ./build/libs/amweb-*.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]
