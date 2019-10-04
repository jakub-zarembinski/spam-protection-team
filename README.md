# Spam Protection Team

This project is a response to the [Full-Stack Challenge](https://github.com/morkro/coding-challenge).

The solution applies the RESTful architectural style.

The back-end is written in Java and it relies on the JAX-RS API implemented by [Eclipse Jersey](https://eclipse-ee4j.github.io/jersey/).

Regarding the front-end there are two versions available:
* [a basic HTML / JavaScript client](https://github.com/jakub-zarembinski/spam-protection-team/blob/master/WebContent/index.html)
* [a web app utilizing the React framework](https://github.com/jakub-zarembinski/spam-protection-team/tree/master/ReactClient)

The React-based variant offers a significantly better UX (as it eliminates the symptoms of page reloading whenever an update is needed) but it creates a problem of [Cross-Origin Resource Sharing](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS) (because a React client needs to be hosted on its own server) and this is solved on the back-end by implementing a customized [Container Response Filter](https://github.com/jakub-zarembinski/spam-protection-team/blob/master/src/ai/sygnet/spt/CORSFilter.java).

#### Testing and building

The solution has been tested on [Apache TomEE](https://tomee.apache.org/) application server (the Plus edition) due to its built-in support for JAX-RS.

A basic unit test is available in the [Tester.java](https://github.com/jakub-zarembinski/spam-protection-team/blob/master/test/ai/sygnet/spt/Tester.java) file.

If you want to build the project from the source code - all dependencies are listed in the [POM file](https://github.com/jakub-zarembinski/spam-protection-team/blob/master/pom.xml).

#### Deployment

Deployment from the pre-compiled binaries:

* Download [Apache TomEE Plus](https://tomee.apache.org/download-ng.html) and start the server
* Download the [WAR file](https://github.com/jakub-zarembinski/spam-protection-team/blob/master/bin/spam-protection-team.war) and copy it into TomEE's `webapps` folder
* Navigate to http://localhost:8080/spam-protection-team/

Deployment of the React client:
* Make sure the TomEE server is running and the WAR file is deployed (as described above)
* `git clone git@github.com:jakub-zarembinski/spam-protection-team.git`
* `cd spam-protection-team/ReactClient`
* `npm install`
* `npm start`
* Navigate to http://localhost:3000/