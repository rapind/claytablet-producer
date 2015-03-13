# Building A Custom Client #

In some cases using the open source client and libraries we provide to talk to the clay tablet system may not be the ideal solution. However, it is entirely feasible to roll a custom client in your language of choice (Ruby, PHP, Python, .NET, etc.).

There are two main components that you will need to interface with. The message queue for sending and receiving events and the remote storage service for sending and receiving files.

Both of these components use the Amazon Web Services (AWS) which are exposed via REST and SOAP. Many languages already have libraries specifically created to talk to AWS.

Please refer to the following resources:
  * http://aws.amazon.com/sqs
  * http://aws.amazon.com/s3

Ruby SQS & S3 libraries:
  * http://sqs.rubyforge.org/
  * https://rubyforge.org/projects/ruby-s3

PHP SQS & S3 libraries:
  * http://code.google.com/p/php-aws/

Python SQS & S3 library:
  * http://pypi.python.org/pypi/Python-Amazon/0.5

.NET:
  * http://www.devx.com/dotnet/Article/33392
  * http://sourceforge.net/projects/nets3client/


In order to send and receive events to/from the clay tablet platform you will need to write a client that can talk to SQS. All clay tablet platform events are XML documents which are documented here.

In order to send and receive files to / from the clay tablet platform you will need to write a client that can talk to S3. The clay tablet file naming conventions are documented here.