# amweb [![Java CI](https://github.com/marco-schmidt/amweb/workflows/Java%20CI/badge.svg)](https://github.com/marco-schmidt/amweb/actions?query=workflow%3A%22Java+CI%22) [![CodeQL](https://github.com/marco-schmidt/amweb/workflows/CodeQL/badge.svg)](https://github.com/marco-schmidt/amweb/actions?query=workflow%3ACodeQL) [![CircleCI](https://circleci.com/gh/marco-schmidt/am.svg?style=svg)](https://app.circleci.com/pipelines/github/marco-schmidt/amweb) [![Docker Pulls](https://img.shields.io/docker/pulls/marcoschmidt/amweb.svg)](https://hub.docker.com/r/marcoschmidt/amweb/) [![Snyk.io](https://snyk.io/test/github/marco-schmidt/amweb/badge.svg)](https://snyk.io/test/github/marco-schmidt/amweb)

Asset manager web application

* Clone amweb git repository.
* Run ``./gradlew war`` to create file build/libs/amweb-VERSION.war
* Download and unpack [Tomcat 10](https://tomcat.apache.org) and copy war file to Tomcat's directory ``webapps``
* Make sure Java 11+ is in PATH: ``java -version``
* Run Tomcat's ``bin/startup.sh`` script.
* Access http://localhost:8080/amweb-VERSION in a browser.