Clay Tablet Producer Client
---------------------------

The Clay Tablet Technologies producer client project is a starting point for Java based producer implementations.

The project includes an event receiver that will receive events from the Clay Tablet platform and an event sender that will send events to the Clay Tablet platform. Methods for uploading and downloading asset, support asset, and asset task version files to and from remote storage are included.

There is also an event listener cron provided which will poll the producer queue every X interval.

In order to properly run the tests and use the client code provided you will need to request a clay tablet account. Please send account requests to drapin@clay-tablet.com and include your name and your company name, and that you require a producer account. Once your account has been created you will be sent an xml file which should be placed in /src/main/resources/accounts/.

The classes of interest for third-party integrators are:
- /src/main/java/com/claytablet/app/ProducerEventCron.java
- /src/main/java/com/claytablet/service/event/impl/ProducerEventListenerImpl.java
- /src/main/java/com/claytablet/service/event/impl/ProducerReceiverImpl.java
- /src/main/java/com/claytablet/service/event/impl/ProducerSenderImpl.java

You can run the event reception cron from ant with the "event" task. I.e. "ant event". You will begin listening to the queue specified in your account XML.

We recommend using Java 1.5 for this project due to issues with Java 6 and JAXB versions. At least until Java 6 Update 5 is released. For more details see:
https://jaxb.dev.java.net/guide/Migrating_JAXB_2_0_applications_to_JavaSE_6.html
http://forums.java.net/jive/thread.jspa?messageID=239022


Library Dependencies:

- aopalliance.jar
- claytablet-event-api-1.0.jar
- claytablet-queue-1.0.jar
- claytablet-storage-1.0.jar
- commons-codec-1.3.jar
- commons-httpclient-3.0.jar
- commons-io.jar
- commons-lang-2.3.jar
- commons-logging-1.1.jar
- commons-net-1.4.1.jar
- guice-1.0.jar
- junit-4.3.1.jar
- log4j-1.2.14.jar
- persistence.jar
- xpp3_min-1.1.3.4.O.jar
- xstream-1.2.2.jar

If using JMS:

- activemq-core-4.1.0-incubator.jar
- apache-activemq-4.1.0-incubator.jar
- geronimo-jms_1.1_spec-1.0.jar
- jms-1.1.jar

If using SQS:

- typica-0.7.jar
- jaxb-xjc.jar

*********
WARNING FOR J2SE 6
Java SE 6 comes with JAXB 2.0. If you are using 6 then the following JAXB jars 
should be removed from the lib dir and placed into the jre/lib/endoresed dir.
*********
- activation.jar
- jaxb-api.jar
- jaxb-impl.jar
- jsr173_1.0_api.jar

If using S3:

- jets3t-0.5.1.jar


