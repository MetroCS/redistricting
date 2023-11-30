# Contributing to the Redistricting project

Please note that this project is released with a [Contributor Code of Conduct](CODE_OF_CONDUCT.md). By participating in this project you agree to abide by its terms.

## PROJECT DESCRIPTION
Please refer to [README.md](README.md)

## PROJECT WORKFLOW
Please refer to the [Project Kanban Board](https://github.com/MetroCS/redistricting/projects/1)

## PROJECT DISCUSSIONS
This project uses [Discussions](https://github.com/MetroCS/redistricting/discussions) for project-related announcements, questions and answers, ideas, and engaging in all discussions not related to a specific project [Issue](https://github.com/MetroCS/redistricting/issues) or [Pull Request](https://github.com/MetroCS/redistricting/pulls).

## TECHNOLOGIES
* Programming Language - Java
* Issue Tracking - GitHub
* Version Control - Git
* Build Process - Apache Ant 

## TOOLS
### Git
* [Git](https://git-scm.com)  
* [Git Clients](https://git-scm.com/downloads)

### GitHub
* [GitHub](https://github.com/)
* [GitHub Tutorial](https://guides.github.com/activities/hello-world/) - "Hello World" starter guide for those new to GitHub

### Apache Ant 
* [Apache Ant](https://ant.apache.org/bindownload.cgi)

#### Using Ant at the command line 
```
   $ cd <directory containing build.xml>
   $ ant all 
   $ cd build
```
#### Bulding on Linux 
Junit requires junit.test.launcher, which may not be in your calsspath by default.  
"junit.test.launcher not in classpath"
This error can happen because of some fo the following reasons:
* JUnit is not installed on your system.
* JUnit is installed, but it is not on the classpath for the project you are trying to run tests
 for.
* You are using a version of JUnit that is not compatible with the version of Java
 you are using.
Additionally, you can use a packae manager like snapd, to install ant, and avoid this classpath problem.
You can install here: [snapd](https://snapcraft.io/docs/installing-snap-on-ubuntu)