# amweb [![Travis CI](https://travis-ci.org/marco-schmidt/amweb.svg?branch=master)](https://travis-ci.org/marco-schmidt/amweb) [![Java CI](https://github.com/marco-schmidt/amweb/workflows/Java%20CI/badge.svg)](https://github.com/marco-schmidt/amweb/actions?query=workflow%3A%22Java+CI%22) [![CodeQL](https://github.com/marco-schmidt/amweb/workflows/CodeQL/badge.svg)](https://github.com/marco-schmidt/amweb/actions?query=workflow%3ACodeQL)

Asset manager web application

* Clone amweb git repository.
* Run ``./gradlew war`` to create file build/libs/amweb-VERSION.war
* Download and unpack [Tomcat 9](https://tomcat.apache.org) and copy war file to Tomcat's directory ``webapps``
* Make sure Java 8 is in PATH: ``java -version``
* Run Tomcat's ``bin/startup.sh`` script.
* Access http://localhost:8080/amweb-VERSION in a browser.