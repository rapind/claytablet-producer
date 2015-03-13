This is the base / example client SDK for producers to the Clay Tablet Technologies Translation Platform.

Typical producer systems are content management systems.

Use this as a starting point when building a new producer system client.

[See Architecture Overview](http://code.google.com/p/claytablet-producer/wiki/ArchitectureOverview)

In order to properly test and use the client you first need to request an account. Please send account requests to [mailto:rcoleman@clay-tablet.com](mailto:rcoleman@clay-tablet.com). Please include your name, your company name, and that you require a producer account in the request.

The Client SDK API Documentation can be found in the downloads section.

Once you have downloaded the source and are beginning integration you will want to first drop the source.xml account file we send you into:
/src/main/resources/account

The source code is organized similar to a typical Apache project. ./src/main/java/ contains the source for the main application, and ./src/test/java constain the source for the various test classes. Ant is used to compile, test, and can be use to launch the receiver and send some mock test events & files to the platform for processing.

You can retrieve the source code using subversion. For details, please click on the "Source" tab above.

The following are the main points of interest for integration work:

1) [Mock Cron](http://claytablet-producer.googlecode.com/svn/trunk/src/main/java/com/claytablet/app/MockCron.java) - This runs a never ending loop that will call the receiver to check for new platform events and the poller to check for state changes in the connected system (CMS).

2) [Mock Receiver](http://claytablet-producer.googlecode.com/svn/trunk/src/main/java/com/claytablet/service/event/impl/MockReceiver.java) - This checks the incoming message queue for new events from the platform and performs whatever work is necessary, like downloading attachments and calling the connection stub (interface to the CMS).


3) [Mock State Poller](http://claytablet-producer.googlecode.com/svn/trunk/src/main/java/com/claytablet/service/event/impl/MockStatePoller.java) - This checks the connected system via the connection stub for state changes in the CMS, and sends important update events and files to the platform queue.


4) [Mock Stub](http://claytablet-producer.googlecode.com/svn/trunk/src/main/java/com/claytablet/service/event/stubs/MockStub.java) - This is the interface to the connected system (CMS).

5)
[Mock Module](http://claytablet-producer.googlecode.com/svn/trunk/src/main/java/com/claytablet/module/MockModule.java) - Guice DI configuration module. Same idea as a Spring applicationContext.xml.


**If you need to build a custom client in a language other than Java please see:
[Building a Custom Client](http://code.google.com/p/claytablet-producer/wiki/BuildingCustomClient)**