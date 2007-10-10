Clay Tablet Producer Client
---------------------------

The Clay Tablet Technologies producer client project is a starting point for Java based producer implementations.

The project includes an event receiver that will receive events from the Clay Tablet platform and an event sender that will send events to the Clay Tablet platform. Methods for uploading and downloading asset, support asset, and asset task version files to and from remote storage are included.

There is also an event listener cron provided which will poll the producer queue every X interval.

In order to properly run the tests and use the client ode provided you will need to request a clay tablet account. Please send account requests to drapin@clay-tablet.com and include your name and your company name. Once your account has been created you will be sent an xml file which should be placed in /src/main/resources/accounts/.


Project Dependencies:

- claytablet-event-api
- claytablet-queue
- claytablet-storage


Library Dependencies:

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

If using S3:

- jets3t-0.5.1.jar


