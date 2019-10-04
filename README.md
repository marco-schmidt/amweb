# amweb [![Travis CI](https://travis-ci.org/marco-schmidt/amweb.svg?branch=master)](https://travis-ci.org/marco-schmidt/amweb)
Asset manager web application

* Clone amweb git repository.
* Run ``./gradlew war`` to create file build/libs/amweb-VERSION.war
* Download and unpack [Tomcat 9](https://tomcat.apache.org) and copy war file to Tomcat's directory ``webapps`
* Make sure Java 8 is in PATH: ``java -version``
* Run Tomcat's bin/startup.sh script.
* Access http://localhost:8080/amweb-VERSION in a browser.