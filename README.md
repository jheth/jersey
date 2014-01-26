
##  Java REST API based on Jersey

### Technologies

* [GitHub API](http://github-api.kohsuke.org/)
* [Jersey](https://jersey.java.net/index.html)
* [Apache Commons](http://commons.apache.org/) (IO, Lang, Codec)
* [Jackson](http://jackson.codehaus.org/)
* [FasterXML](http://wiki.fasterxml.com/JacksonHome)
* [Jetty](http://www.eclipse.org/jetty/)
* [Gradle](http://www.gradle.org/)


### Installation

Clone Repo

    git clone git@github.com:jheth/jersey.git

Create ~/.github file

This project uses [Kohsuke's GitHub API](http://github-api.kohsuke.org/) which relies on a local ~/.github properties file.

    login=kohsuke
    password=012345678

Alternatively, you can have just the OAuth token in this file:

    oauth=4d98173f7c075527cb64878561d1fe70

Run Gradle

    gradle build jettyRunWar

Open Browser and Visit:

    http://localhost:8088/jersey/rest/org/{orgName}
    http://localhost:8088/jersey/rest/org/{orgName}/repos
    http://localhost:8088/jersey/rest/org/{orgName}/top?limit=5

Or CURL away:

    curl -X GET http://localhost:8088/jersey/rest/org/{orgName}
    curl -X GET http://localhost:8088/jersey/rest/org/{orgName}/repos
    curl -X GET http://localhost:8088/jersey/rest/org/{orgName}/top?limit=5

### Eclipse

Build Eclipse project using gradle

    gradle eclipse

### Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
