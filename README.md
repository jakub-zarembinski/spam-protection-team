# Spam Protection Team

This project is a response to the [Full-Stack Challenge](https://github.com/morkro/coding-challenge). The solution applies the RESTful architectural style. The back-end is written in Java and it relies on the JAX-RS API implemented by [Eclipse Jersey](https://eclipse-ee4j.github.io/jersey/). The front-end is written in JavaScript and offers two alternative versions:

* [basic HTML / JavaScript client](https://github.com/jakub-zarembinski/spam-protection-team/blob/master/WebContent/index.html)
* [React application](https://github.com/jakub-zarembinski/spam-protection-team/tree/master/ReactClient)

The React-based variant offers a significantly better UX (as it eliminates the symptoms of page reloading whenever an update is needed) but it creates a problem of [Cross-Origin Resource Sharing](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS), as the React client needs to be hosted on its own NodeJS server. The CORS issue is solved on the back-end by utilizing a customized [Container Response Filter](https://github.com/jakub-zarembinski/spam-protection-team/blob/master/src/ai/sygnet/spt/CORSFilter.java).

#### Building from source

All dependencies are listed in the [POM file](https://github.com/jakub-zarembinski/spam-protection-team/blob/master/pom.xml).

#### Target server

The solution has been tested on [Apache TomEE](https://tomee.apache.org/) application server (the Plus edition). It's a good fit due to its built-in support for JAX-RS.

#### Deployment

The easiest option is deployment from the pre-compiled WAR binaries:

* Download [Apache TomEE Plus](https://tomee.apache.org/download-ng.html) and start the server
* Download the [WAR file](https://github.com/jakub-zarembinski/spam-protection-team/blob/master/bin/spam-protection-team.war) and copy it into TomEE's `webapps` folder
* Navigate to http://localhost:8080/spam-protection-team/

#### Running with the React client

First, make sure the TomEE server is running and the WAR file is successfully deployed (as described above). Then follow these steps:

* `git clone git@github.com:jakub-zarembinski/spam-protection-team.git`
* `cd spam-protection-team/ReactClient`
* `npm install`
* `npm start`
* Navigate to http://localhost:3000/

#### Unit testing

A basic unit test for the back-end is available in the [Tester.java](https://github.com/jakub-zarembinski/spam-protection-team/blob/master/test/ai/sygnet/spt/Tester.java) file. This test assumes that another instance of the application is already deployed and running on the server.